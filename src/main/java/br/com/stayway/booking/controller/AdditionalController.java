package br.com.stayway.booking.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.stayway.booking.model.entries.AdditionalEntry;
import br.com.stayway.booking.service.AdditionalService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/reservation/additional")
public class AdditionalController {

    private final AdditionalService additionalService;
    
    public AdditionalController(AdditionalService additionalService) {
        this.additionalService = additionalService;
    }

    @PutMapping("{/reservationId}")
    public ResponseEntity<Void> updateAdditional(@PathVariable String reservationId, @RequestBody AdditionalEntry additional) {
        additionalService.updateAdditional(reservationId, additional);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{/reservationId}/{additionalId}")
    public ResponseEntity<Void> removeAdditional(@PathVariable String reservationId, @PathVariable String additionalId) {
        additionalService.removeAdditional(reservationId, additionalId);
        return ResponseEntity.noContent().build();
    }
    
}
