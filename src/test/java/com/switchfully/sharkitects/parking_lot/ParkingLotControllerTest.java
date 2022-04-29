package com.switchfully.sharkitects.parking_lot;

import com.switchfully.sharkitects.infrastructure.EmptyInputException;
import com.switchfully.sharkitects.infrastructure.InvalidNumberException;
import com.switchfully.sharkitects.members.Address;
import com.switchfully.sharkitects.members.LicensePlate;
import com.switchfully.sharkitects.members.PostalCodeCity;
import com.switchfully.sharkitects.members.dtos.RegisterMemberDto;
import com.switchfully.sharkitects.members.exceptions.MissingCityException;
import com.switchfully.sharkitects.members.exceptions.MissingStreetNameException;
import com.switchfully.sharkitects.members.exceptions.MissingStreetNumberException;
import com.switchfully.sharkitects.members.exceptions.MissingZipCodeException;
import com.switchfully.sharkitects.parking_lot.dto.CreateParkingLotDto;
import com.switchfully.sharkitects.parking_lot.dto.ParkingLotMapper;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

    @Autowired
    private ParkingLotService parkingLotService;

    @Test
    void givenParkingLot_whenParkingLotIsCreated_returnParkingLot() {

        ParkingLot parkingLot = new ParkingLot("name", Category.ABOVE_GROUND_BUILDING, 10,
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

    @Nested
    @DisplayName("Parking Name validation tests")
    class ParkingNameValidationTest {
        @Test
        void givenNullName_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto
                    (null, Category.ABOVE_GROUND_BUILDING, 10,
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk")),
                            new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                                    new Address("Stefaniestraat", "1B",
                                            new PostalCodeCity("3600", "Genk"))),
                            10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty parking name");
        }

        @Test
        void givenEmptyName_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address("Stefaniestraat", "1B",
                            new PostalCodeCity("3600", "Genk")),
                    new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty parking name");
        }

        @Test
        void givenBlankName_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("  ", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address("Stefaniestraat", "1B",
                            new PostalCodeCity("3600", "Genk")),
                    new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty parking name");
        }
    }

    @Nested
    @DisplayName("Capacity validation tests")
    class CapacityValidationTest {
        @Test
        void givenNegativeCapacity_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("name", Category.ABOVE_GROUND_BUILDING, -5,
                    new Address("Stefaniestraat", "1B",
                            new PostalCodeCity("3600", "Genk")),
                    new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    10);

            //WHEN

            RestAssured
                    .given()
                    .port(port)
                    .body(expected)
                    .contentType(JSON)
                    .when()
                    .accept(JSON)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(InvalidNumberException.class)
                    .hasMessage("Invalid value entered for max capacity");
        }


        @Test
        void givenCapacityEquals0_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("name", Category.ABOVE_GROUND_BUILDING, 0,
                    new Address("Stefaniestraat", "1B",
                            new PostalCodeCity("3600", "Genk")),
                    new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    10);

            //WHEN

            RestAssured
                    .given()
                    .port(port)
                    .body(expected)
                    .contentType(JSON)
                    .when()
                    .accept(JSON)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(InvalidNumberException.class)
                    .hasMessage("Invalid value entered for max capacity");
        }
    }

    @Nested
    @DisplayName("Address validation tests")
    class AddressValidationTest {
        @Test
        void givenNullStreetName_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("name", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address(null, "1B",
                            new PostalCodeCity("3600", "Genk")),
                    new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty street name");
        }

        @Test
        void givenEmptyStreetName_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("name", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address("", "1B",
                            new PostalCodeCity("3600", "Genk")),
                    new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty street name");
        }

        @Test
        void givenBlankStreetName_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("name", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address("  ", "1B",
                            new PostalCodeCity("3600", "Genk")),
                    new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty street name");
        }

        @Test
        void givenNullStreetNumber_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("name", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address("Stefaniestraat", null,
                            new PostalCodeCity("3600", "Genk")),
                    new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty street number");
        }

        @Test
        void givenEmptyStreetNumber_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("name", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address("Stefaniestraat", "",
                            new PostalCodeCity("3600", "Genk")),
                    new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty street number");
        }

        @Test
        void givenBlankStreetNumber_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("name", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address("Stefaniestraat", "  ",
                            new PostalCodeCity("3600", "Genk")),
                    new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty street number");
        }

        @Test
        void givenNullZipCode_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("name", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address("Stefaniestraat", "1B",
                            new PostalCodeCity(null, "Genk")),
                    new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty zip code");
        }

        @Test
        void givenEmptyZipCode_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("name", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address("Stefaniestraat", "1B",
                            new PostalCodeCity("", "Genk")),
                    new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty zip code");
        }

        @Test
        void givenBlankZipCode_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("name", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address("Stefaniestraat", "1B",
                            new PostalCodeCity("  ", "Genk")),
                    new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty zip code");
        }

        @Test
        void givenNullCity_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("name", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address("Stefaniestraat", "1B",
                            new PostalCodeCity("3600", null)),
                    new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty city");
        }

        @Test
        void givenEmptyCity_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("name", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address("Stefaniestraat", "1B",
                            new PostalCodeCity("3600", "")),
                    new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty city");
        }

        @Test
        void givenBlankCity_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("name", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address("Stefaniestraat", "1B",
                            new PostalCodeCity("3600", "  ")),
                    new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty city");
        }
    }

    @Nested
    @DisplayName("Price per hour validation tests")
    class PricePerHourValidationTest {
        @Test
        void givenNegativePricePerHour_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("name", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address("Stefaniestraat", "1B",
                            new PostalCodeCity("3600", "Genk")),
                    new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    -10);

            //WHEN

            RestAssured
                    .given()
                    .port(port)
                    .body(expected)
                    .contentType(JSON)
                    .when()
                    .accept(JSON)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(InvalidNumberException.class)
                    .hasMessage("Invalid value entered for price per hour");
        }

        @Test
        void givenPricePerHourEquals0_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("name", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address("Stefaniestraat", "1B",
                            new PostalCodeCity("3600", "Genk")),
                    new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    0);

            //WHEN

            RestAssured
                    .given()
                    .port(port)
                    .body(expected)
                    .contentType(JSON)
                    .when()
                    .accept(JSON)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(InvalidNumberException.class)
                    .hasMessage("Invalid value entered for price per hour");
        }
    }

    @Nested
    @DisplayName("Contact Person Name validation tests")
    class ContactPersonNameValidationTest {
        @Test
        void givenNullFirstName_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto
                    ("baby", Category.ABOVE_GROUND_BUILDING, 10,
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk")),
                            new ContactPerson(null, "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                                    new Address("Stefaniestraat", "1B",
                                            new PostalCodeCity("3600", "Genk"))),
                            10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty first name");
        }

        @Test
        void givenEmptyFirstName_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("Baby", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address("Stefaniestraat", "1B",
                            new PostalCodeCity("3600", "Genk")),
                    new ContactPerson("", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty first name");
        }

        @Test
        void givenBlankFirstName_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("Baby", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address("Stefaniestraat", "1B",
                            new PostalCodeCity("3600", "Genk")),
                    new ContactPerson(" ", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty first name");
        }

        @Test
        void givenNullLastName_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto
                    ("baby", Category.ABOVE_GROUND_BUILDING, 10,
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk")),
                            new ContactPerson("baby", null, "04893543135", "60564035", "stefanie@mail.com",
                                    new Address("Stefaniestraat", "1B",
                                            new PostalCodeCity("3600", "Genk"))),
                            10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty last name");
        }

        @Test
        void givenEmptyLastName_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("Baby", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address("Stefaniestraat", "1B",
                            new PostalCodeCity("3600", "Genk")),
                    new ContactPerson("baby", "", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty last name");
        }

        @Test
        void givenBlankLastName_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto("Baby", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address("Stefaniestraat", "1B",
                            new PostalCodeCity("3600", "Genk")),
                    new ContactPerson("baby", " ", "04893543135", "60564035", "stefanie@mail.com",
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk"))),
                    10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty last name");
        }
    }

    @Nested
    @DisplayName("Contact Person Phone number validation tests")
    class ContactPersonPhoneNumberValidationTest {
        @Test
        void givenMobilePhoneNumberAndPhoneNumberAreNull_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto
                    ("baby", Category.ABOVE_GROUND_BUILDING, 10,
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk")),
                            new ContactPerson("baby", "Vloemans", null, null, "stefanie@mail.com",
                                    new Address("Stefaniestraat", "1B",
                                            new PostalCodeCity("3600", "Genk"))),
                            10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty mobile phone number and telephone number");
        }

        @Test
        void givenMobilePhoneNumberAndPhoneNumberAreEmpty_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto
                    ("baby", Category.ABOVE_GROUND_BUILDING, 10,
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk")),
                            new ContactPerson("baby", "Vloemans", "", "", "stefanie@mail.com",
                                    new Address("Stefaniestraat", "1B",
                                            new PostalCodeCity("3600", "Genk"))),
                            10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty mobile phone number and telephone number");
        }

        @Test
        void givenMobilePhoneNumberAndPhoneNumberAreBlank_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            CreateParkingLotDto expected = new CreateParkingLotDto
                    ("baby", Category.ABOVE_GROUND_BUILDING, 10,
                            new Address("Stefaniestraat", "1B",
                                    new PostalCodeCity("3600", "Genk")),
                            new ContactPerson("baby", "Vloemans", " ", " ", "stefanie@mail.com",
                                    new Address("Stefaniestraat", "1B",
                                            new PostalCodeCity("3600", "Genk"))),
                            10);

            //WHEN
            RestAssured
                    .given()
                    .body(expected)
                    .accept(JSON)
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .post("/parking-lots")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());

            Throwable thrown = Assertions.catchThrowable(() -> parkingLotService.createParkingLot(expected));

            //THEN
            Assertions.assertThat(thrown)
                    .isInstanceOf(EmptyInputException.class)
                    .hasMessage("Empty mobile phone number and telephone number");
        }
    }


    @Nested
    @DisplayName("Contact Person Address validation tests")
    class ContactPersonAddressValidationTest {
        @Test
        void givenContactPersonStreetNameAndStreetNumberAreNull_whenCreateParkingLot_thenBadRequestIsReturnedAndExceptionIsThrown() {
            //GIVEN
            ParkingLot parkingLot = new ParkingLot("name", Category.ABOVE_GROUND_BUILDING, 10,
                    new Address("Stefaniestraat", "1B",
                            new PostalCodeCity("3600", "Genk")),
                    new ContactPerson("Stefanie", "Vloemans", "04893543135", "60564035", "stefanie@mail.com",
                            new Address(null, null,
                                    new PostalCodeCity("3600", "Genk"))),
                    10);
            //WHEN
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

            //THEN
            Assertions.assertThat(parkingLotRepository.findAll()).extracting(c -> c.getName()).contains("name");
        }


    }
}