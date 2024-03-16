package br.com.stayway.booking.service;

import br.com.stayway.booking.controller.response.ReservationReceiptResponse;
import br.com.stayway.booking.exception.NoSuchRoomException;
import br.com.stayway.booking.exception.ReservationNotFoundException;
import br.com.stayway.booking.integration.HotelServiceAPI;
import br.com.stayway.booking.integration.request.ObtainAdditionalRequest;
import br.com.stayway.booking.integration.response.AdditionalResponse;
import br.com.stayway.booking.integration.response.RoomResponse;
import br.com.stayway.booking.model.AvailableRoomCalendar;
import br.com.stayway.booking.model.BookedRoomCalendar;
import br.com.stayway.booking.model.Reservation;
import br.com.stayway.booking.model.dto.BookedRoomDTO;
import br.com.stayway.booking.model.enums.ReservationStatus;
import br.com.stayway.booking.repository.ReservationRepository;
import br.com.stayway.booking.use_case.AssembleReceiptUseCase;
import br.com.stayway.booking.use_case.ReserveUseCase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BookedRoomService bookedRoomService;
    private final HotelServiceAPI hotelServiceAPI;

    public ReservationService(ReservationRepository reservationRepository, BookedRoomService bookedRoomService,
            HotelServiceAPI hotelServiceAPI) {
        this.reservationRepository = reservationRepository;
        this.bookedRoomService = bookedRoomService;
        this.hotelServiceAPI = hotelServiceAPI;
    }

    public void addReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public List<Reservation> searchAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation searchReservationById(String id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    public void updateReservation(String id, Reservation updatedReservation) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        existingReservation.setStatus(updatedReservation.getStatus());
        existingReservation.setNumberOfguests(existingReservation.getNumberOfguests());
        existingReservation.setCheckin(updatedReservation.getCheckin());
        existingReservation.setCheckout(updatedReservation.getCheckout());
        existingReservation.setHotelId(updatedReservation.getHotelId());
        existingReservation.setBookedRooms(updatedReservation.getBookedRooms());

        reservationRepository.save(existingReservation);
    }

    public void deleteReservation(String id) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        reservationRepository.delete(existingReservation);
    }

    @Transactional
    public void confirmReservation(String id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        // update status to block new updating attempts
        reservation.setStatus(ReservationStatus.PROCESSING);
        reservation = reservationRepository.save(reservation);

        var checkin = reservation.getCheckin();
        var checkout = reservation.getCheckout();
        var rooms = reservation.getBookedRooms().stream()
                .map(room -> new BookedRoomDTO(room.getRoomId(), checkin, checkout, room.getNumberOfRooms()))
                .collect(Collectors.toList());

        // Integrate with hotels service
        List<RoomResponse> hotelRooms = hotelServiceAPI.getHotelRooms(reservation.getHotelId());

        var additionalList = reservation.getAdditionals().stream()
                .map(a -> new ObtainAdditionalRequest(a.getId(), a.getQuantity()))
                .collect(Collectors.toList());
        List<AdditionalResponse> additionals = hotelServiceAPI.obtainAdditionals(additionalList);

        // checks room availability
        for (BookedRoomDTO room : rooms) {
            BookedRoomCalendar bCalendar = bookedRoomService.roomBookingCalendar(room.roomId(), checkin, checkout);
            int tRooms = hotelRooms.stream()
                    .filter(tRoom -> tRoom.id().equals(room.roomId()))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchRoomException())
                    .quantidade();
            AvailableRoomCalendar aCalendar = new AvailableRoomCalendar(bCalendar, tRooms);
            ReserveUseCase.checkRoomAvailability(room, aCalendar);
        }

        // update status to block new updating attempts
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setReceipt(AssembleReceiptUseCase.assembleReservation(reservation, hotelRooms, additionals));
        reservation = reservationRepository.save(reservation);
    }

    public ReservationReceiptResponse checkTotais(String id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
        
        List<RoomResponse> rooms = hotelServiceAPI.getHotelRooms(reservation.getHotelId());

        var additionalList = reservation.getAdditionals().stream()
                .map(a -> new ObtainAdditionalRequest(a.getId(), a.getQuantity()))
                .collect(Collectors.toList());
        List<AdditionalResponse> additionals = hotelServiceAPI.obtainAdditionals(additionalList);

        var receipt = AssembleReceiptUseCase.assembleReservation(reservation, rooms, additionals);
        return new ReservationReceiptResponse(reservation.getCheckin(), reservation.getCheckout(), receipt);
    }
}
