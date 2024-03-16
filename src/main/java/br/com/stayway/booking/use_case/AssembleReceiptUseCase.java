package br.com.stayway.booking.use_case;

import java.util.List;

import br.com.stayway.booking.exception.NoSuchAdditionalException;
import br.com.stayway.booking.exception.NoSuchRoomException;
import br.com.stayway.booking.integration.response.AdditionalResponse;
import br.com.stayway.booking.integration.response.RoomResponse;
import br.com.stayway.booking.model.Reservation;
import br.com.stayway.booking.model.ReservationReceipt;
import br.com.stayway.booking.model.entries.ReceiptItem;

public class AssembleReceiptUseCase {
    public static ReservationReceipt assembleReservation(Reservation reservation, List<RoomResponse> roomData, List<AdditionalResponse> additionalData) {
        ReservationReceipt receipt = new ReservationReceipt();

        reservation.getBookedRooms().forEach(room -> {
            RoomResponse item = roomData.stream().filter(rd -> rd.id() == room.getRoomId()).findFirst().orElseThrow(() -> new NoSuchRoomException());
            receipt.addItem(new ReceiptItem(item.description(), "Room", room.getNumberOfRooms(), item.valor()));
        });

        reservation.getAdditionals().forEach(additional -> {
            AdditionalResponse item = additionalData.stream().filter(ad -> ad.id() == additional.getId()).findFirst().orElseThrow(() -> new NoSuchAdditionalException());
            receipt.addItem(new ReceiptItem(item.obs(), item.type(), item.quantidade(), item.valor()));
        });

        return receipt;
    }
}