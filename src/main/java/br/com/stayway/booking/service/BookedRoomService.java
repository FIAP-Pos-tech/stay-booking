package br.com.stayway.booking.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.stayway.booking.model.BookedRoomCalendar;
import br.com.stayway.booking.model.dto.BookedRoomDTO;
import br.com.stayway.booking.repository.ReservationRepository;

@Service
public class BookedRoomService {

    private final ReservationRepository reservationRepository;

    public BookedRoomService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // return a list of room bookings for the hotel in the given period.
    public List<BookedRoomDTO> hotelBookings(String hotelId, LocalDate checkInDate, LocalDate checkOutDate) {
        return reservationRepository.findHotelBookings(hotelId, checkInDate, checkOutDate);
    }

    // return a list of bookings for the given rooms and period. Rooms must belong
    // to the same hotel.
    public List<BookedRoomDTO> roomsBookings(LocalDate checkInDate, LocalDate checkOutDate,
            List<String> roomIds) {
        return reservationRepository.findRoomsBookings(checkInDate, checkOutDate, roomIds);
    }

    // return the calendar of reservations for the given room and period
    public BookedRoomCalendar roomBookingCalendar(String roomId, LocalDate checkInDate,
            LocalDate checkOutDate) {
        List<BookedRoomDTO> bookings = roomsBookings(checkInDate, checkOutDate, List.of(roomId));
        var calendar = new BookedRoomCalendar(roomId, checkInDate, checkOutDate);
        bookings.stream().filter(b -> b.roomId().equals(roomId)).forEach(b -> calendar.addBooking(b));
        return calendar;
    }

    // return a list of calendars for each room
    public List<BookedRoomCalendar> roomBookingCalendars(Set<String> roomIds, LocalDate checkInDate,
            LocalDate checkOutDate) {
        return roomIds.stream().map(id -> roomBookingCalendar(id, checkInDate, checkOutDate))
                .collect(Collectors.toList());
    }

}
