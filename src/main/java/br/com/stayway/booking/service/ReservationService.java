package br.com.stayway.booking.service;

import br.com.stayway.booking.exception.ReservationNotFoundException;
import br.com.stayway.booking.model.AvailableRoomCalendar;
import br.com.stayway.booking.model.BookedRoomCalendar;
import br.com.stayway.booking.model.Reservation;
import br.com.stayway.booking.model.dto.BookedRoomDTO;
import br.com.stayway.booking.model.entries.RoomEntry;
import br.com.stayway.booking.model.enums.ReservationStatus;
import br.com.stayway.booking.repository.ReservationRepository;
import br.com.stayway.booking.use_case.ReserveUseCase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BookedRoomService bookedRoomService;

    public ReservationService(ReservationRepository reservationRepository, BookedRoomService bookedRoomService) {
        this.reservationRepository = reservationRepository;
        this.bookedRoomService = bookedRoomService;
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

        // TODO: getTotalRooms
        List<RoomEntry> totalRooms = new ArrayList<>();

        // checks room availability
        for (BookedRoomDTO room : rooms) {
            BookedRoomCalendar bCalendar = bookedRoomService.roomBookingCalendar(room.roomId(), checkin, checkout);
            int tRooms = totalRooms.stream()
                    .filter(tRoom -> tRoom.getRoomId().equals(room.roomId()))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException())
                    .getNumberOfRooms();
            AvailableRoomCalendar aCalendar = new AvailableRoomCalendar(bCalendar, tRooms);
            ReserveUseCase.checkRoomAvailability(room, aCalendar);
        }

        // update status to block new updating attempts
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation = reservationRepository.save(reservation);
    }
}
