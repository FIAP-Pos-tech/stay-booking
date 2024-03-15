package br.com.stayway.booking.controller;

import br.com.stayway.booking.controller.request.ClienteReservationDTO;
import br.com.stayway.booking.exception.ReservationNotFoundException;
import br.com.stayway.booking.model.Reservation;
import br.com.stayway.booking.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Void> addReservation(@RequestBody ClienteReservationDTO reservation) {
        reservationService.addReservation(reservation.toEntity());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservas() {
        List<Reservation> reservations = reservationService.searchAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable String id) {
        try {
            Reservation reservation = reservationService.searchReservationById(id);
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        } catch (ReservationNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateReservation(@PathVariable String id, @RequestBody Reservation reservation) {
        reservationService.updateReservation(id, reservation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable String id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
