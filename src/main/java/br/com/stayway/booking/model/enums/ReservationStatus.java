package br.com.stayway.booking.model.enums;

public enum ReservationStatus {
    OPENED("Opened"),
    PROCESSING("Processing"),
    CONFIRMED("Confirmed"),
    MAINTENANCE("Under Maintenance"),
    CANCELLED("Cancelled");

    private final String status;

    private ReservationStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
