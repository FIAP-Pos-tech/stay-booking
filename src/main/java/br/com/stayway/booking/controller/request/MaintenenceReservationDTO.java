package br.com.stayway.booking.controller.request;

import java.time.LocalDate;
import java.util.List;

import br.com.stayway.booking.model.Reservation;
import br.com.stayway.booking.model.entries.RoomEntry;
import br.com.stayway.booking.model.enums.ReservationStatus;

public record MaintenenceReservationDTO(String userId, LocalDate checkin, LocalDate checkout, String hotelId,
        List<RoomEntry> bookedRooms) {

    public Reservation toEntity() {
        return new Reservation(null, userId, ReservationStatus.MAINTENANCE, 0, checkin, checkout, hotelId, bookedRooms);
    }
}