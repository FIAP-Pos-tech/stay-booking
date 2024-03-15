package br.com.stayway.booking.controller;

import br.com.stayway.booking.exception.ReservationNotFoundException;
import br.com.stayway.booking.model.Reservation;
import br.com.stayway.booking.model.entries.RoomEntry;
import br.com.stayway.booking.model.enums.ReservationStatus;
import br.com.stayway.booking.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {
    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @Test
    public void shouldCreateReservationTest() {

        Reservation reservation = new Reservation();
        reservation.setId("1");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setNumberOfguests(2);
        reservation.setCheckin(LocalDate.of(2024, 3, 15));
        reservation.setCheckout(LocalDate.of(2024, 3, 20));
        reservation.setHotelId("123456");

        RoomEntry roomEntry = new RoomEntry();
        roomEntry.setRoomId("room123");
        roomEntry.setNumberOfRooms(1);

        List<RoomEntry> bookedRooms = new ArrayList<>();
        bookedRooms.add(roomEntry);
        reservation.setBookedRooms(bookedRooms);

        doNothing().when(reservationService).addReservation(reservation);
        ResponseEntity<Void> response = reservationController.addReservation(reservation);
        verify(reservationService, times(1)).addReservation(reservation);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void shouldGetAllReservationsTest() {

        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation = new Reservation();
        reservation.setId("1");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setNumberOfguests(2);
        reservation.setCheckin(LocalDate.of(2024, 3, 15));
        reservation.setCheckout(LocalDate.of(2024, 3, 20));
        reservation.setHotelId("123456");

        RoomEntry roomEntry = new RoomEntry();
        roomEntry.setRoomId("room123");
        roomEntry.setNumberOfRooms(1);

        List<RoomEntry> bookedRooms = new ArrayList<>();
        bookedRooms.add(roomEntry);
        reservation.setBookedRooms(bookedRooms);
        reservations.add(reservation);

        when(reservationService.searchAllReservations()).thenReturn(reservations);
        ResponseEntity<List<Reservation>> response = reservationController.getAllReservas();
        verify(reservationService, times(1)).searchAllReservations();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(reservations);
    }

    @Test
    public void shouldGetReservationByIdTest() {

        Reservation reservation = new Reservation();
        reservation.setId("1");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setNumberOfguests(2);
        reservation.setCheckin(LocalDate.of(2024, 3, 15));
        reservation.setCheckout(LocalDate.of(2024, 3, 20));
        reservation.setHotelId("123456");

        RoomEntry roomEntry = new RoomEntry();
        roomEntry.setRoomId("room123");
        roomEntry.setNumberOfRooms(1);

        List<RoomEntry> bookedRooms = new ArrayList<>();
        bookedRooms.add(roomEntry);
        reservation.setBookedRooms(bookedRooms);

        when(reservationService.searchReservationById("1")).thenReturn(reservation);
        when(reservationService.searchReservationById("2")).thenThrow(new ReservationNotFoundException("Reservation with ID 2 not found"));
        ResponseEntity<Reservation> response1 = reservationController.getReservationById("1");
        verify(reservationService, times(1)).searchReservationById("1");
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response1.getBody()).isEqualTo(reservation);

        ResponseEntity<Reservation> response2 = reservationController.getReservationById("2");
        verify(reservationService, times(1)).searchReservationById("2");
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void shouldUpdateReservationByIdTest() {

        Reservation reservation = new Reservation();
        reservation.setId("1");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setNumberOfguests(2);
        reservation.setCheckin(LocalDate.of(2024, 3, 15));
        reservation.setCheckout(LocalDate.of(2024, 3, 20));
        reservation.setHotelId("123456");

        RoomEntry roomEntry = new RoomEntry();
        roomEntry.setRoomId("room123");
        roomEntry.setNumberOfRooms(1);

        List<RoomEntry> bookedRooms = new ArrayList<>();
        bookedRooms.add(roomEntry);
        reservation.setBookedRooms(bookedRooms);

        ResponseEntity<Void> response = reservationController.updateReservation("1", reservation);
        verify(reservationService, times(1)).updateReservation("1", reservation);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldDeleteReservationByIdTest() {

        ResponseEntity<Void> response = reservationController.deleteReservation("1");
        verify(reservationService, times(1)).deleteReservation("1");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
