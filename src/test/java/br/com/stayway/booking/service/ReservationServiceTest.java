package br.com.stayway.booking.service;

import br.com.stayway.booking.exception.ReservationNotFoundException;
import br.com.stayway.booking.model.Reservation;
import br.com.stayway.booking.model.entries.RoomEntry;
import br.com.stayway.booking.model.enums.ReservationStatus;
import br.com.stayway.booking.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

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

        reservationService.addReservation(reservation);
        verify(reservationRepository, times(1)).save(reservation);
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

        when(reservationRepository.findAll()).thenReturn(reservations);
        List<Reservation> result = reservationService.searchAllReservations();
        verify(reservationRepository, times(1)).findAll();
        assertThat(result).isEqualTo(reservations);
    }

    @Test
    public void shouldSearchReservationByIdTest() {

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

        when(reservationRepository.findById("1")).thenReturn(Optional.of(reservation));
        Reservation result = reservationService.searchReservationById("1");
        verify(reservationRepository, times(1)).findById("1");
        assertEquals(reservation, result);
    }

    @Test
    public void shouldNotSearchReservationByIdNotFound() {

        when(reservationRepository.findById("2")).thenReturn(Optional.empty());
        assertThrows(ReservationNotFoundException.class, () -> {
            reservationService.searchReservationById("2");
        });

        verify(reservationRepository, times(1)).findById("2");
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


        Reservation updatedReservation = new Reservation();
        updatedReservation.setId("1");
        updatedReservation.setStatus(ReservationStatus.CANCELLED);
        updatedReservation.setNumberOfguests(3);
        updatedReservation.setCheckin(LocalDate.of(2024, 3, 15));
        updatedReservation.setCheckout(LocalDate.of(2024, 3, 20));
        updatedReservation.setHotelId("123456");

        RoomEntry updatedRoomEntry = new RoomEntry();
        updatedRoomEntry.setRoomId("room123");
        updatedRoomEntry.setNumberOfRooms(1);

        List<RoomEntry> updateBookedRooms = new ArrayList<>();
        updateBookedRooms.add(updatedRoomEntry);
        updatedReservation.setBookedRooms(updateBookedRooms);


        when(reservationRepository.findById("1")).thenReturn(Optional.of(updatedReservation));
        reservationService.updateReservation("1", updatedReservation);
        verify(reservationRepository, times(1)).findById("1");
        verify(reservationRepository, times(1)).save(updatedReservation);
        assertEquals(ReservationStatus.CANCELLED, updatedReservation.getStatus());
        assertEquals(3, updatedReservation.getNumberOfguests());
        assertEquals(LocalDate.of(2024, 3, 15), updatedReservation.getCheckin());
        assertEquals(LocalDate.of(2024, 3, 20), updatedReservation.getCheckout());
        assertEquals("123456", updatedReservation.getHotelId());
        assertEquals(1, updatedReservation.getBookedRooms().size());
        assertEquals("room123", updatedRoomEntry.getRoomId());
        assertEquals(1, updatedRoomEntry.getNumberOfRooms());
    }

    @Test
    public void shouldNotUpdateReservationNotFound() {

        when(reservationRepository.findById("2")).thenReturn(Optional.empty());
        assertThrows(ReservationNotFoundException.class, () -> {
            reservationService.updateReservation("2", new Reservation());
        });

        verify(reservationRepository, times(1)).findById("2");
        verify(reservationRepository, never()).save(any());
    }

    @Test
    public void shouldDeleteReservationById() {

        Reservation existingReservation = new Reservation();
        existingReservation.setId("1");
        existingReservation.setStatus(ReservationStatus.CONFIRMED);
        existingReservation.setNumberOfguests(2);
        RoomEntry roomEntry = new RoomEntry();
        roomEntry.setRoomId("room123");
        roomEntry.setNumberOfRooms(1);
        List<RoomEntry> bookedRooms = new ArrayList<>();
        bookedRooms.add(roomEntry);
        existingReservation.setBookedRooms(bookedRooms);

        when(reservationRepository.findById("1")).thenReturn(Optional.of(existingReservation));
        reservationService.deleteReservation("1");
        verify(reservationRepository, times(1)).findById("1");
        verify(reservationRepository, times(1)).delete(existingReservation);
    }

    @Test
    public void shouldNotDeleteReservationByIdNotFound() {

        when(reservationRepository.findById("2")).thenReturn(Optional.empty());
        assertThrows(ReservationNotFoundException.class, () -> {
            reservationService.deleteReservation("2");
        });

        verify(reservationRepository, times(1)).findById("2");
        verify(reservationRepository, never()).delete(any());
    }
}
