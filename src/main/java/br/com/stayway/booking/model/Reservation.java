package br.com.stayway.booking.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.stayway.booking.model.entries.RoomEntry;
import br.com.stayway.booking.model.enums.ReservationStatus;

// location -> hotel -> quartos
// uma reserva pode 
@Document(collection = "reservations")
public class Reservation {
    @Id
    private String id;

    private ReservationStatus status;

    private Integer numberOfguests;

    private LocalDate checkin;

    private LocalDate checkout;

    private String hotelId;

    private List<RoomEntry> bookedRooms;

    // lista de adicionais

    public Reservation() {
    }

    public Reservation(String id, ReservationStatus status, Integer numberOfguests, LocalDate checkin,
            LocalDate checkout, String hotelId, List<RoomEntry> bookedRooms) {
        this.id = id;
        this.status = status;
        this.numberOfguests = numberOfguests;
        this.checkin = checkin;
        this.checkout = checkout;
        this.hotelId = hotelId;
        this.bookedRooms = bookedRooms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}

// check_month() -> Array[31](1 8)(2 6)


// Abacaxi
// 10 q standard

