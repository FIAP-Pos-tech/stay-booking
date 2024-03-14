package br.com.stayway.booking.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class RoomEntry {

    private String roomId;

    private int numberOfRooms;

    public RoomEntry(String roomId, int numberOfRooms) {
        this.roomId = roomId;
        this.numberOfRooms = numberOfRooms;
    }

    public String getRoomId() {
        return roomId;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }
}
