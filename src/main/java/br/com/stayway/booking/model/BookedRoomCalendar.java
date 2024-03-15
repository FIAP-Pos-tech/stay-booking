package br.com.stayway.booking.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.stayway.booking.exception.InvalidRoomCalendarException;
import br.com.stayway.booking.model.dto.BookedRoomDTO;
import br.com.stayway.booking.model.entries.DateEntry;
import br.com.stayway.booking.model.interfaces.Calendar;

public class BookedRoomCalendar implements Calendar {
    private String roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Map<LocalDate, Integer> bookedRoomsCalendar;

    public BookedRoomCalendar(String roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookedRoomsCalendar = new HashMap<>();
    }

    // -----
    // Interface
    // -----

    public String getRoomId() {
        return roomId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public List<DateEntry> getDates() {
        return bookedRoomsCalendar.entrySet().stream().map(entry -> new DateEntry(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    // -----
    // Methods
    // -----

    public boolean addBooking(BookedRoomDTO roomBooking) {
        if (!roomBooking.roomId().equals(this.roomId)) {
            throw new InvalidRoomCalendarException();
        }

        var checkin = roomBooking.checkInDate();
        var checkout = roomBooking.checkOutDate();

        if (checkin.compareTo(this.checkOutDate) > 0 || checkout.compareTo(this.checkInDate) < 0) {
            return false;
        }

        checkin.datesUntil(checkout.plusDays(1)).forEach(date -> {
            if (date.compareTo(this.checkInDate) >= 0 && date.compareTo(this.checkOutDate) <= 0) {
                int currentValue = bookedRoomsCalendar.getOrDefault(date, 0);
                int newValue = currentValue + roomBooking.numberOfRooms();
                this.bookedRoomsCalendar.put(date, newValue);
            }
        });

        return true;
    }
}
