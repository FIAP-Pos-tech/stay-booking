package br.com.stayway.booking.model.dto;

import java.time.LocalDate;

public record BookedRoomDTO(String roomId, LocalDate checkInDate, LocalDate checkOutDate, int numberOfRooms) {
    
}
