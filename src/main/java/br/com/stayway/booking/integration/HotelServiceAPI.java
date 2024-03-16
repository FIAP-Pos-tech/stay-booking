package br.com.stayway.booking.integration;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.stayway.booking.integration.request.ObtainAdditionalRequest;
import br.com.stayway.booking.integration.response.AdditionalResponse;
import br.com.stayway.booking.integration.response.RoomResponse;

@FeignClient(value = "hotel", url = "localhost:8080")
public interface HotelServiceAPI {

    @GetMapping(value = "/hotel/{id}/quartos")
    List<RoomResponse> getHotelRooms(@PathVariable String hotelId);

    @PostMapping(value = "/adicional/lista")
    List<AdditionalResponse> obtainAdditionals(List<ObtainAdditionalRequest> ObtainAdditionalsRequest);

}
