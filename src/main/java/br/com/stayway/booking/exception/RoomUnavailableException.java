package br.com.stayway.booking.exception;

import java.time.LocalDate;

public class RoomUnavailableException extends RuntimeException {
    public RoomUnavailableException(String room, LocalDate date) {
        super("Room " + room + " is unavailable at date " + date);
    }
}
