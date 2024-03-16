package br.com.stayway.booking.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(String id){
        super("Reservation with ID " + id + " not found.");
    }
}
