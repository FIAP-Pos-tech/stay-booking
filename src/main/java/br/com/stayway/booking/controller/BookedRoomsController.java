package br.com.stayway.booking.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.stayway.booking.model.BookedRoomCalendar;
import br.com.stayway.booking.model.dto.BookedRoomDTO;
import br.com.stayway.booking.model.entries.DateEntry;
import br.com.stayway.booking.service.BookedRoomsService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class BookedRoomsController {

    private final BookedRoomsService bookedRoomsService;

    public BookedRoomsController(BookedRoomsService bookedRoomsService) {
        this.bookedRoomsService = bookedRoomsService;
    }

    // Returns a list of bookings for hotel rooms given a period
    @GetMapping("/admin/hotel-bookings")
    public ResponseEntity<List<BookedRoomDTO>> hotelBookings(@RequestParam String hotelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate) {

        return ResponseEntity.ok(bookedRoomsService.hotelBookings(hotelId, checkInDate, checkOutDate));
    }

    // Returns a list of bookings for the given rooms and period.
    // The rooms must belong to the same hotel.
    @GetMapping("/admin/rooms-bookings")
    public ResponseEntity<List<BookedRoomDTO>> roomsBookings(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam String roomIds) {

        return ResponseEntity
                .ok(bookedRoomsService.roomsBookings(checkInDate, checkOutDate, Arrays.asList(roomIds.split(","))));
    }

    @GetMapping("api/booking-calendar")
    public ResponseEntity<List<DateEntry>> bookingCalendar(@RequestParam String roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate) {
        return ResponseEntity.ok(bookedRoomsService.roomBookingCalendar(roomId, checkInDate, checkOutDate).getDates());
    }

    @GetMapping("api/booking-calendars")
    public ResponseEntity<List<BookedRoomCalendar>> bookingCalendars(@RequestParam String roomIds,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate) {
        return ResponseEntity.ok(bookedRoomsService.roomBookingCalendars(new HashSet<>(Arrays.asList(roomIds.split(","))), checkInDate, checkOutDate));
    }

}
