package br.com.stayway.booking.service;

import org.springframework.stereotype.Service;

import br.com.stayway.booking.exception.ReservationNotFoundException;
import br.com.stayway.booking.model.entries.AdditionalsEntry;
import br.com.stayway.booking.repository.ReservationRepository;
import br.com.stayway.booking.use_case.AdditionalsUseCase;

@Service
public class AdditionalService {

    private final ReservationRepository reservationRepository;

    public AdditionalService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void updateAdditional(String reservationId, AdditionalsEntry additional) {
        var reserveOpt = reservationRepository.findById(reservationId);
        if (reserveOpt.isEmpty()) {
            throw new ReservationNotFoundException(reservationId);
        }

        var newReserve = AdditionalsUseCase.updateAdditional(reserveOpt.get(), additional);
        reservationRepository.save(newReserve);
    }

    public void removeAdditional(String reservationId, String additionalId) {
        var reserveOpt = reservationRepository.findById(reservationId);
        if (reserveOpt.isEmpty()) {
            throw new ReservationNotFoundException(reservationId);
        }
        
        var newReserve = AdditionalsUseCase.removeAditional(reserveOpt.get(), additionalId);
        reservationRepository.save(newReserve);
    }
}