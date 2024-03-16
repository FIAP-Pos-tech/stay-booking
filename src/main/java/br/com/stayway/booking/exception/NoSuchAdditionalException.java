package br.com.stayway.booking.exception;

public class NoSuchAdditionalException extends RuntimeException{
    public NoSuchAdditionalException() {
        super("Additional not listed.");
    }
}
