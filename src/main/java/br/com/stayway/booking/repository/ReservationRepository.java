package br.com.stayway.booking.repository;

import br.com.stayway.booking.model.Reservation;
import br.com.stayway.booking.model.dto.BookedRoomDTO;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {

    // checkin and checkout date are swapped in the query
    @Aggregation(pipeline = {
        "{$match: { 'hotelId': ?0, 'checkin': {$lte: ?2} , 'checkout': {$gte: ?1}, 'status': { $ne: 'CANCELLED' }}}",
        "{$unwind: '$bookedRooms'}",
        "{$project: { 'checkInDate': '$checkin', 'checkOutDate': '$checkout', 'roomId': '$bookedRooms.roomId', 'numberOfRooms': '$bookedRooms.numberOfRooms' }}",
        "{$sort: { 'checkInDate': 1, 'roomId': 1 }}"
    })
    List<BookedRoomDTO> findHotelBookings(String hotelId, LocalDate checkInDate, LocalDate checkOutDate);

    @Aggregation(pipeline = {
        "{$match: { 'checkin': {$lte: ?1} , 'checkout': {$gte: ?0}, 'status': { $ne: 'CANCELLED' }}}",
        "{$unwind: '$bookedRooms'}",
        "{$match: { 'bookedRooms.roomId': { $in: ?2 } }}",
        "{$project: { 'checkInDate': '$checkin', 'checkOutDate': '$checkout', 'roomId': '$bookedRooms.roomId', 'numberOfRooms': '$bookedRooms.numberOfRooms' }}",
        "{$sort: { 'checkInDate': 1, 'roomId': 1 }}"
    })
    List<BookedRoomDTO> findRoomsBookings(LocalDate checkInDate, LocalDate checkOutDate, List<String> roomIds);

}
