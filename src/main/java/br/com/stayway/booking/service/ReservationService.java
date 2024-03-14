package br.com.stayway.booking.service;

import br.com.stayway.booking.exception.ReservationNotFoundException;
import br.com.stayway.booking.model.Reservation;
import br.com.stayway.booking.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;


    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void addReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public List<Reservation> searchAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation searchReservationById(String id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation with ID " + id + " not found."));
    }

    public void updateReservation(String id, Reservation updatedReservation) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation with ID " + id + " not found."));

        existingReservation.setStatus(updatedReservation.getStatus());
        existingReservation.setNumberOfguests(existingReservation.getNumberOfguests());
        existingReservation.setCheckin(updatedReservation.getCheckin());
        existingReservation.setCheckout(updatedReservation.getCheckout());
        existingReservation.setHotelId(updatedReservation.getHotelId());
        existingReservation.setBookedRooms(updatedReservation.getBookedRooms());

        reservationRepository.save(existingReservation);
    }

    public void deleteReservation(String id) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation with ID " + id + " not found."));

        reservationRepository.delete(existingReservation);
    }


}
