package br.com.stayway.booking.model;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import br.com.stayway.booking.exception.RoomUnavailableException;
import br.com.stayway.booking.model.entries.DateEntry;
import br.com.stayway.booking.model.interfaces.Calendar;

public class AvailableRoomCalendar implements Calendar {
    String roomId;
    LocalDate checkInDate;
    LocalDate checkOutDate;
    List<DateEntry> availableRoomsCalendar;

    public AvailableRoomCalendar(BookedRoomCalendar bookings, int maxRooms) {
        this.roomId = bookings.getRoomId();
        this.checkInDate = bookings.getCheckInDate();
        this.checkOutDate = bookings.getCheckOutDate();

        this.availableRoomsCalendar = bookings.getDates();
        this.availableRoomsCalendar.sort(Comparator.comparing(DateEntry::getDate));
        this.availableRoomsCalendar.forEach(booking -> booking.setNumberOfRooms(maxRooms - booking.getNumberOfRooms()));

        var negativeReserve = this.availableRoomsCalendar.stream()
                .filter(booking -> booking.getNumberOfRooms() < 0)
                .findAny();

        if (negativeReserve.isPresent()) {
            throw new RoomUnavailableException(roomId, negativeReserve.get().getDate());
        }
    }

    public String getRoomId() {
        return roomId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    /// Sorted by date
    public List<DateEntry> getDates() {
        return availableRoomsCalendar;
    }
}
