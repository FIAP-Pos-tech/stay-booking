package br.com.stayway.booking.model.entries;

public class RoomEntry {

    private String roomId;

    private int numberOfRooms;

    public RoomEntry() {
    }

    public RoomEntry(String roomId, int numberOfRooms) {
        this.roomId = roomId;
        this.numberOfRooms = numberOfRooms;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }
}
