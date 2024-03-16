package br.com.stayway.booking.exception;

public class NoSuchRoomException extends RuntimeException {
    public NoSuchRoomException() {
        super("Room not listed.");
    }
}
