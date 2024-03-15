package br.com.stayway.booking.model.dto;

import java.time.LocalDate;

import br.com.stayway.booking.model.entries.RoomEntry;

public record BookedRoomDTO(String roomId, LocalDate checkInDate, LocalDate checkOutDate, int numberOfRooms) {

    public RoomEntry toRoomEntry() {
        return new RoomEntry(roomId, numberOfRooms);
    }
    
}
