package com.switchfully.sharkitects.parking_lot;

import com.switchfully.sharkitects.members.Address;
import com.switchfully.sharkitects.members.PostalCodeCity;
import com.switchfully.sharkitects.parking_lot.dto.CreateParkingLotDto;
import com.switchfully.sharkitects.parking_lot.dto.ParkingLotMapper;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ParkingLotControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingLotMapper parkingLotMapper;

    @Test
    void givenParkingLot_whenParkingLotIsCreated_returnParkingLot() {

        CreateParkingDto parkingLot = new CreateParkingDto("name", Category.ABOVE_GROUND_BUILDING, 10,
                new Address("Stefaniestraat", "1B",
                        new PostalCodeCity("3600", "Genk")),
                new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                        new Address("Stefaniestraat", "1B",
                                new PostalCodeCity("3600", "Genk"))),
                10);

     RestAssured
                .given()
                .port(port)
                .body(parkingLot)
                .contentType(JSON)
                .when()
                .accept(JSON)
                .post("/parking-lots")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());


         Assertions.assertThat(parkingLotRepository.findAll()).extracting(c -> c.getName()).contains("name");

    }
}