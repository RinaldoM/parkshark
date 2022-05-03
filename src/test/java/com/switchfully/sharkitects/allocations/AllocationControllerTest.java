package com.switchfully.sharkitects.allocations;

import com.switchfully.sharkitects.allocations.dtos.AllocatedSpotDto;
import com.switchfully.sharkitects.allocations.dtos.StartAllocatingParkingSpotDto;
import com.switchfully.sharkitects.allocations.exceptions.IdDoesNotExist;
import com.switchfully.sharkitects.allocations.exceptions.licensePlateNotLinkedToMember;
import com.switchfully.sharkitects.members.*;
import com.switchfully.sharkitects.parking_lot.*;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;

import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AllocationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private AllocationService allocationService;

    @Test
    void startAllocatingParkingSpot() {
        //GIVEN
        Member existingMember = new Member(
                "Baby",
                "Shark",
                new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                "0474555999",
                "baby.shark@music.bad",
                new LicensePlate("SHRK123", "Belgium"),
                LocalDateTime.now()
        );
        memberRepository.save(existingMember);

        ParkingLot existingParkingLot = new ParkingLot("name", Category.ABOVE_GROUND_BUILDING, 10,
                new Address("Stefaniestraat", "1B",
                        new PostalCodeCity("3600", "Genk")),
                new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                        new Address("Stefaniestraat", "1B",
                                new PostalCodeCity("3600", "Genk"))),
                10);
        parkingLotRepository.save(existingParkingLot);

        StartAllocatingParkingSpotDto expected = new StartAllocatingParkingSpotDto(
                existingMember.getId(),
                "1",
                existingParkingLot.getId()
        );

        //WHEN
        AllocatedSpotDto actual = RestAssured
                .given()
                .body(expected)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .port(port)
                .post("/allocations")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(AllocatedSpotDto.class);

        //THEN
        Assertions.assertThat(actual.getId()).isNotEqualTo(0);
        Assertions.assertThat(actual.getMemberId()).isEqualTo(expected.getMemberId());
        Assertions.assertThat(actual.getParkingLotId()).isEqualTo(expected.getParkingLotId());
        Assertions.assertThat(actual.getLicensePlate()).isEqualTo(expected.getLicensePlate());
    }

    @Test
    void givenMemberDoesNotExist_whenAllocateParkingSpot_thenBadRequestIsReturnedAndExceptionIsThrown() {
        //GIVEN
        ParkingLot existingParkingLot = new ParkingLot("name", Category.ABOVE_GROUND_BUILDING, 10,
                new Address("Stefaniestraat", "1B",
                        new PostalCodeCity("3600", "Genk")),
                new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                        new Address("Stefaniestraat", "1B",
                                new PostalCodeCity("3600", "Genk"))),
                10);
        parkingLotRepository.save(existingParkingLot);

        StartAllocatingParkingSpotDto expected = new StartAllocatingParkingSpotDto(
                "notExistingId",
                "1",
                existingParkingLot.getId()
        );

        //WHEN
        RestAssured
                .given()
                .body(expected)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .port(port)
                .post("/allocations")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        Throwable thrown = Assertions.catchThrowable(() -> allocationService.startAllocatingParkingSpot(expected));

        //THEN
        Assertions.assertThat(thrown)
                .isInstanceOf(IdDoesNotExist.class)
                .hasMessage("No member with this ID exists");
    }

    @Test
    void givenParkingLotDoesNotExist_whenAllocateParkingSpot_thenBadRequestIsReturnedAndExceptionIsThrown() {
        //GIVEN
        Member existingMember = new Member(
                "Baby1",
                "Shark1",
                new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                "0474555999",
                "baby.shark@music.bad",
                new LicensePlate("plate1", "Belgium"),
                LocalDateTime.now()
        );
        memberRepository.save(existingMember);

        StartAllocatingParkingSpotDto expected = new StartAllocatingParkingSpotDto(
                existingMember.getId(),
                existingMember.getLicensePlate().getNumber(),
                56941
        );

        //WHEN
        RestAssured
                .given()
                .body(expected)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .port(port)
                .post("/allocations")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        Throwable thrown = Assertions.catchThrowable(() -> allocationService.startAllocatingParkingSpot(expected));

        //THEN
        Assertions.assertThat(thrown)
                .isInstanceOf(IdDoesNotExist.class)
                .hasMessage("No parking lot with this ID exists");
    }

    @Test
    void givenLicensePlateNotLinkedToMember_whenAllocateParkingSpot_thenBadRequestIsReturnedAndExceptionIsThrown() {
        //GIVEN
        Member existingMember1 = new Member(
                "Baby1",
                "Shark1",
                new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                "0474555999",
                "baby.shark@music.bad",
                new LicensePlate("plate1", "Belgium"),
                LocalDateTime.now()
        );
        memberRepository.save(existingMember1);

        Member existingMember2 = new Member(
                "Baby",
                "Shark",
                new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                "0474555999",
                "baby.shark@music.bad",
                new LicensePlate("plate2", "Belgium"),
                LocalDateTime.now()
        );
        memberRepository.save(existingMember2);

        ParkingLot existingParkingLot = new ParkingLot("name", Category.ABOVE_GROUND_BUILDING, 10,
                new Address("Stefaniestraat", "1B",
                        new PostalCodeCity("3600", "Genk")),
                new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                        new Address("Stefaniestraat", "1B",
                                new PostalCodeCity("3600", "Genk"))),
                10);
        parkingLotRepository.save(existingParkingLot);

        StartAllocatingParkingSpotDto expected = new StartAllocatingParkingSpotDto(
                existingMember1.getId(),
                existingMember2.getLicensePlate().getNumber(),
                existingParkingLot.getId()
        );

        //WHEN
        RestAssured
                .given()
                .body(expected)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .port(port)
                .post("/allocations")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        Throwable thrown = Assertions.catchThrowable(() -> allocationService.startAllocatingParkingSpot(expected));

        //THEN
        Assertions.assertThat(thrown)
                .isInstanceOf(licensePlateNotLinkedToMember.class)
                .hasMessage("The provided license plate number is not linked to the provided member id");
    }
}