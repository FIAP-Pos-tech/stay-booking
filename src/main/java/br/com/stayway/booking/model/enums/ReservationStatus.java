package br.com.stayway.booking.model.enums;

public enum ReservationStatus {
    CONFIRMED("Confirmed"),
    MAINTENANCE("Maintenance"),
    CANCELLED("Cancelled");

    private final String status;

    private ReservationStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
