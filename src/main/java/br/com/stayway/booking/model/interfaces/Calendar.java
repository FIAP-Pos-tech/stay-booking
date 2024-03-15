package br.com.stayway.booking.model.interfaces;

import java.time.LocalDate;
import java.util.List;

import br.com.stayway.booking.model.entries.DateEntry;

public interface Calendar {
    public String getRoomId();

    public LocalDate getCheckInDate();

    public LocalDate getCheckOutDate();

    public List<DateEntry> getDates();
}
