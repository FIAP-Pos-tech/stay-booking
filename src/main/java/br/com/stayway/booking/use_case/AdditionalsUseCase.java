package br.com.stayway.booking.use_case;

import br.com.stayway.booking.exception.ReservationUpdateException;
import br.com.stayway.booking.model.Reservation;
import br.com.stayway.booking.model.entries.AdditionalsEntry;
import br.com.stayway.booking.model.enums.ReservationStatus;

public class AdditionalsUseCase {

    // Update if the additional is already present, add it otherwise
    public static Reservation updateAddicional(Reservation reserve, AdditionalsEntry additional) {
        if (reserve.getStatus() != ReservationStatus.OPENED) {
            throw new ReservationUpdateException();
        }

        var additionals = reserve.getAditionals();
        
        if (additionals.isEmpty()) {
            additionals.add(additional);
        } else {
            additionals.stream()
            .filter(item -> item.getId().equals(additional.getId()))
            .findFirst()
            .ifPresentOrElse(
                    item -> item.setQuantity(additional.getQuantity()),
                    () -> reserve.getAditionals().add(additional)
            );
        }
        
        return reserve;
    }

    public static Reservation removeAditional(Reservation reserve, String additionalId) {
        if (reserve.getStatus() != ReservationStatus.OPENED) {
            throw new ReservationUpdateException();
        }

        var additionals = reserve.getAditionals();

        if (additionals != null && !additionals.isEmpty()) {
            reserve.getAditionals().removeIf(item -> item.getId().equals(additionalId));
        }

        return reserve;
    }
}
