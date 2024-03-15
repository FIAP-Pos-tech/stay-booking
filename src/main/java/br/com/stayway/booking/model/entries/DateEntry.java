package br.com.stayway.booking.model.entries;

import java.time.LocalDate;

public class DateEntry {
    private LocalDate date;
    private int numberOfRooms;

    public DateEntry(LocalDate date, int numberOfRooms) {
        this.date = date;
        this.numberOfRooms = numberOfRooms;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }
}
