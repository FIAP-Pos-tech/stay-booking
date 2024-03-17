package br.com.stayway.booking.controller.response;

import java.time.LocalDate;

import br.com.stayway.booking.model.Reservation;
import br.com.stayway.booking.model.ReservationReceipt;
import br.com.stayway.booking.model.enums.ReservationStatus;

public record ReservationReceiptResponse(ReservationStatus status, LocalDate checkInDate, LocalDate checkOutDate, ReservationReceipt receipt) {
    public ReservationReceiptResponse(Reservation reservation) {
        this(reservation.getStatus(), reservation.getCheckin(), reservation.getCheckout(), reservation.getReceipt());
    }

    public ReservationReceiptResponse(Reservation reservation, ReservationReceipt receipt) {
        this(reservation.getStatus(), reservation.getCheckin(), reservation.getCheckout(), receipt);
    }
}
