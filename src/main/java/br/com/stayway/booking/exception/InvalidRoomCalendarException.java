package br.com.stayway.booking.exception;

public class InvalidRoomCalendarException extends RuntimeException{
    public InvalidRoomCalendarException() {
        super("Internal Server Error: Invalid room calendar.");
    }
}
