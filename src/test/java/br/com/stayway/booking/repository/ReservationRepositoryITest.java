package br.com.stayway.booking.repository;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import br.com.stayway.booking.model.Reservation;
import br.com.stayway.booking.model.dto.BookedRoomDTO;
import br.com.stayway.booking.model.entries.RoomEntry;
import br.com.stayway.booking.model.enums.ReservationStatus;

@Testcontainers
@DataMongoTest
public class ReservationRepositoryITest {

    @Autowired
    ReservationRepository reservationRepository;

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"))
            .withExposedPorts(27017);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    private static LocalDate beginingDate = LocalDate.of(2024, 1, 27);
    private static LocalDate endDate = LocalDate.of(2024, 1, 30);

    @BeforeEach
    public void setup() {

        List<Reservation> reserves = List.of(
                new Reservation(null, ReservationStatus.CONFIRMED, 4, beginingDate, endDate, "0",
                        List.of(new RoomEntry("0", 2))),
                new Reservation(null, ReservationStatus.CONFIRMED, 4, beginingDate, endDate, "0",
                        List.of(new RoomEntry("0", 2), new RoomEntry("1", 2))),
                new Reservation(null, ReservationStatus.CONFIRMED, 7, beginingDate.plusDays(1), endDate.minusDays(1), "0",
                        List.of(new RoomEntry("0", 3), new RoomEntry("1", 1)))

        // new Reservation(null, ReservationStatus.MAINTENANCE, 0, inicio, fim, "0",
        // List.of(new RoomEntry("0", 1), new RoomEntry("1", 2))),
        // new Reservation(null, ReservationStatus.CANCELLED, 4, inicio, fim, "0",
        // List.of(new RoomEntry("0", 2))),
        );

        reservationRepository.saveAll(reserves);
    }

    @AfterEach
    public void tearDown() {
        reservationRepository.deleteAll();
    }

    @Test
    public void findHotelBookings() {
        String hotelId = "0";
        LocalDate checkInDate = LocalDate.of(2024, 1, 26);
        LocalDate checkOutDate = LocalDate.of(2024, 1, 31);

        var result = reservationRepository.findHotelBookings(hotelId, checkInDate, checkOutDate);

        List<BookedRoomDTO> expected = List.of(
            new BookedRoomDTO("0", beginingDate, endDate, 2),
            new BookedRoomDTO("0", beginingDate, endDate, 2),
            new BookedRoomDTO("1", beginingDate, endDate, 2),
            new BookedRoomDTO("0", beginingDate.plusDays(1), endDate.minusDays(1), 3),
            new BookedRoomDTO("1", beginingDate.plusDays(1), endDate.minusDays(1), 1)
        );

        assertEquals(expected, result);
    }

    @Test
    public void findRoomsBookings() {
        LocalDate checkInDate = LocalDate.of(2024, 1, 26);
        LocalDate checkOutDate = LocalDate.of(2024, 1, 31);

        var result = reservationRepository.findRoomsBookings(checkInDate, checkOutDate, List.of("1"));

        List<BookedRoomDTO> expected = List.of(
            new BookedRoomDTO("1", beginingDate, endDate, 2),
            new BookedRoomDTO("1", beginingDate.plusDays(1), endDate.minusDays(1), 1)
        );

        assertEquals(expected, result);
    }
}
