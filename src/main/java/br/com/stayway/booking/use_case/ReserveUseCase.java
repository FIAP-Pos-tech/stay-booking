package br.com.stayway.booking.use_case;

import br.com.stayway.booking.exception.InvalidRoomCalendarException;
import br.com.stayway.booking.exception.RoomUnavailableException;
import br.com.stayway.booking.model.AvailableRoomCalendar;
import br.com.stayway.booking.model.dto.BookedRoomDTO;

public class ReserveUseCase {

    public static void checkRoomAvailability(BookedRoomDTO room, AvailableRoomCalendar calendar) {

        if (!room.roomId().equals(calendar.getRoomId())) {
            throw new InvalidRoomCalendarException();
        }

        var checkin = room.checkInDate();
        var checkout = room.checkOutDate();
        var bookedRooms = room.numberOfRooms();

        if (checkin.isBefore(calendar.getCheckInDate())
                || checkin.isAfter(calendar.getCheckOutDate())
                || checkout.isBefore(calendar.getCheckInDate())
                || checkout.isAfter(calendar.getCheckOutDate())) {
            throw new InvalidRoomCalendarException();
        }

        calendar.getDates().stream()
                .filter(date -> {
                    var availableRooms = date.getNumberOfRooms();
                    var reservedRooms = availableRooms - bookedRooms;
                    return reservedRooms < 0;
                })
                .findAny()
                .ifPresent(unavailableDate -> new RoomUnavailableException(room.roomId(), unavailableDate.getDate()));
    }

}
