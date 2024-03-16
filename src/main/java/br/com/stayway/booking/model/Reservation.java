package br.com.stayway.booking.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.stayway.booking.model.entries.AdditionalEntry;
import br.com.stayway.booking.model.entries.RoomEntry;
import br.com.stayway.booking.model.enums.ReservationStatus;

// location -> hotel -> quartos
// uma reserva pode 
@Document(collection = "reservations")
public class Reservation {
    @Id
    private String id;

    private String userId;

    private ReservationStatus status;

    private Integer numberOfguests;

    private LocalDate checkin;

    private LocalDate checkout;

    private String hotelId;

    private List<RoomEntry> bookedRooms;

    private List<AdditionalEntry> additionals;

    public Reservation() {
    }

    public Reservation(String id, String userId, ReservationStatus status, Integer numberOfguests, LocalDate checkin,
            LocalDate checkout, String hotelId, List<RoomEntry> bookedRooms) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.numberOfguests = numberOfguests;
        this.checkin = checkin;
        this.checkout = checkout;
        this.hotelId = hotelId;
        this.bookedRooms = bookedRooms;
        this.additionals = new ArrayList<>();
    }

    // -----
    // Getters and Setters
    // -----

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Integer getNumberOfguests() {
        return numberOfguests;
    }

    public void setNumberOfguests(Integer numberOfguests) {
        this.numberOfguests = numberOfguests;
    }

    public LocalDate getCheckin() {
        return checkin;
    }

    public void setCheckin(LocalDate checkin) {
        this.checkin = checkin;
    }

    public LocalDate getCheckout() {
        return checkout;
    }

    public void setCheckout(LocalDate checkout) {
        this.checkout = checkout;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public List<RoomEntry> getBookedRooms() {
        return bookedRooms;
    }

    public void setBookedRooms(List<RoomEntry> bookedRooms) {
        this.bookedRooms = bookedRooms;
    }

    public List<AdditionalEntry> getAdditionals() {
        return additionals;
    }

    public void setAdditionals(List<AdditionalEntry> additionals) {
        this.additionals = additionals;
    }
}
