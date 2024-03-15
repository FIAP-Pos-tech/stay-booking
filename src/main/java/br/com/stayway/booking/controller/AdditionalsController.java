package br.com.stayway.booking.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.stayway.booking.model.entries.AdditionalsEntry;
import br.com.stayway.booking.service.AdditionalsService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/reservation-additionals")
public class AdditionalsController {

    private final AdditionalsService additionalsService;
    
    public AdditionalsController(AdditionalsService additionalsService) {
        this.additionalsService = additionalsService;
    }

    @PostMapping("{reservationId}")
    public ResponseEntity<Void> updateAddicional(@PathVariable String reservationId, @RequestBody AdditionalsEntry additional) {
        additionalsService.updateAddicional(reservationId, additional);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{reservationId}/{additionalId}")
    public ResponseEntity<Void> removeAdditional(@PathVariable String reservationId, @PathVariable String additionalId) {
        additionalsService.removeAdditional(reservationId, additionalId);
        return ResponseEntity.noContent().build();
    }
    
}
