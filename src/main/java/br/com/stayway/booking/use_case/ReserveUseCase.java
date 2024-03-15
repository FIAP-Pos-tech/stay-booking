package br.com.stayway.booking.use_case;

import br.com.stayway.booking.exception.InvalidRoomCalendarException;
import br.com.stayway.booking.model.AvailableRoomCalendar;
import br.com.stayway.booking.model.dto.BookedRoomDTO;

public class ReserveUseCase {

    public static boolean checkRoomAvailability(BookedRoomDTO room, AvailableRoomCalendar calendar) {

        if (!room.roomId().equals(calendar.getRoomId())) {
            throw new InvalidRoomCalendarException();
        }

        var checkin = room.checkInDate();
        var checkout = room.checkOutDate();

        if (checkin.isBefore(calendar.getCheckInDate())
                || checkin.isAfter(calendar.getCheckOutDate())
                || checkout.isBefore(calendar.getCheckInDate())
                || checkout.isAfter(calendar.getCheckOutDate())) {
            throw new InvalidRoomCalendarException();
        }
        
        boolean available = calendar.getDates().stream().allMatch(date -> {
                var reservedRooms = date.getNumberOfRooms() - room.numberOfRooms();
                return reservedRooms >= 0;
            });

        return available;
    }

    }
