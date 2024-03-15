package br.com.stayway.booking.exception;

public class ReservationUpdateException extends RuntimeException {
    public ReservationUpdateException() {
        super("Reserve is closed and cannot be updated anymore.");
    }
}
