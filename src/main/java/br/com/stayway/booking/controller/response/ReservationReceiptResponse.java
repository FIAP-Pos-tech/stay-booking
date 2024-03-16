package br.com.stayway.booking.controller.response;

import java.time.LocalDate;

import br.com.stayway.booking.model.Reservation;
import br.com.stayway.booking.model.ReservationReceipt;

public record ReservationReceiptResponse(LocalDate checkInDate, LocalDate checkOutDate, ReservationReceipt receipt) {
    public ReservationReceiptResponse(Reservation reservation) {
        this(reservation.getCheckin(), reservation.getCheckout(), reservation.getReceipt());
    }
}
