package com.switchfully.sharkitects.members;

import com.switchfully.sharkitects.members.dtos.MemberDto;
import com.switchfully.sharkitects.members.dtos.RegisterMemberDto;
import com.switchfully.sharkitects.members.exceptions.*;
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

import static io.restassured.http.ContentType.JSON;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MemberControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberService memberService;

    @Test
    void registerMember() {
        //GIVEN
        RegisterMemberDto expected = new RegisterMemberDto(
                "Baby",
                "Shark",
                new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                "0474555999",
                "baby.shark@music.bad",
                new LicensePlate("SHRK123", "Belgium")
        );

        //WHEN
        MemberDto actual = RestAssured
                .given()
                .body(expected)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .port(port)
                .post("/members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(MemberDto.class);

        //THEN
        Assertions.assertThat(actual.getId()).isNotBlank().isNotEmpty().isNotNull();
        Assertions.assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
        Assertions.assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
        Assertions.assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        Assertions.assertThat(actual.getPhoneNumber()).isEqualTo(expected.getPhoneNumber());
        Assertions.assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        Assertions.assertThat(actual.getLicensePlate()).isEqualTo(expected.getLicensePlate());
        Assertions.assertThat(actual.getRegistrationDate()).isEqualTo(expected.getRegistrationDate());
    }


    @Nested
    @DisplayName("Input validation tests")
    class InputValidationTest {
        @Nested
        @DisplayName("Name validation tests")
        class NameValidationTest {
            @Test
            void givenNullFirstName_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        null,
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingFirstNameException.class)
                        .hasMessage("No first name has been provided during member registration.");
            }

            @Test
            void givenEmptyFirstName_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingFirstNameException.class)
                        .hasMessage("No first name has been provided during member registration.");
            }

            @Test
            void givenBlankFirstName_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "     ",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingFirstNameException.class)
                        .hasMessage("No first name has been provided during member registration.");
            }

            @Test
            void givenNullLastName_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        null,
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingLastNameException.class)
                        .hasMessage("No last name has been provided during member registration.");
            }

            @Test
            void givenEmptyLastName_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingLastNameException.class)
                        .hasMessage("No last name has been provided during member registration.");
            }

            @Test
            void givenBlankLastName_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "    ",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingLastNameException.class)
                        .hasMessage("No last name has been provided during member registration.");
            }
        }
        @Nested
        @DisplayName("Address validation tests")
        class AddressValidationTest {
            @Test
            void givenNullStreetName_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address(null, "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingStreetNameException.class)
                        .hasMessage("No street name has been provided during member registration.");
            }

            @Test
            void givenEmptyStreetName_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingStreetNameException.class)
                        .hasMessage("No street name has been provided during member registration.");
            }

            @Test
            void givenBlankStreetName_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("    ", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingStreetNameException.class)
                        .hasMessage("No street name has been provided during member registration.");
            }

            @Test
            void givenNullStreetNumber_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", null, new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingStreetNumberException.class)
                        .hasMessage("No street number has been provided during member registration.");
            }

            @Test
            void givenEmptyStreetNumber_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingStreetNumberException.class)
                        .hasMessage("No street number has been provided during member registration.");
            }

            @Test
            void givenBlankStreetNumber_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "   ", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingStreetNumberException.class)
                        .hasMessage("No street number has been provided during member registration.");
            }

            @Test
            void givenNullZipCode_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity(null, "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingZipCodeException.class)
                        .hasMessage("No zip code has been provided during member registration.");
            }

            @Test
            void givenEmptyZipCode_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("", "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingZipCodeException.class)
                        .hasMessage("No zip code has been provided during member registration.");
            }

            @Test
            void givenBlankZipCode_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("  ", "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingZipCodeException.class)
                        .hasMessage("No zip code has been provided during member registration.");
            }

            @Test
            void givenNullCity_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", null)),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingCityException.class)
                        .hasMessage("No city has been provided during member registration.");
            }

            @Test
            void givenEmptyCity_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingCityException.class)
                        .hasMessage("No city has been provided during member registration.");
            }

            @Test
            void givenBlankCity_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "  ")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingCityException.class)
                        .hasMessage("No city has been provided during member registration.");
            }
        }
        @Nested
        @DisplayName("Phone validation tests")
        class PhoneValidationTest {
            @Test
            void givenNullPhoneNumber_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        null,
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingPhoneNumberException.class)
                        .hasMessage("No phone number has been provided during member registration.");
            }

            @Test
            void givenEmptyPhoneNumber_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingPhoneNumberException.class)
                        .hasMessage("No phone number has been provided during member registration.");
            }

            @Test
            void givenBlankPhoneNumber_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "  ",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingPhoneNumberException.class)
                        .hasMessage("No phone number has been provided during member registration.");
            }
        }
        @Nested
        @DisplayName("Email validation tests")
        class EmailValidationTest {
            @Test
            void givenNullEmail_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        null,
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingEmailException.class)
                        .hasMessage("No email address has been provided during member registration.");
            }

            @Test
            void givenEmptyEmail_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingEmailException.class)
                        .hasMessage("No email address has been provided during member registration.");
            }

            @Test
            void givenBlankEmail_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "  ",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingEmailException.class)
                        .hasMessage("No email address has been provided during member registration.");
            }

            @Test
            void givenInvalidEmailAddressFormatNoAtNoDot_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "babysharkisadumdum",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(InvalidEmailFormatException.class)
                        .hasMessage("An invalid email address format has been provided during member registration.");
            }

            @Test
            void givenInvalidEmailAddressFormatNoAt_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "babysharkisa.dumdum",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(InvalidEmailFormatException.class)
                        .hasMessage("An invalid email address format has been provided during member registration.");
            }

            @Test
            void givenInvalidEmailAddressFormatNoDot_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "babysharkisa@dumdum",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(InvalidEmailFormatException.class)
                        .hasMessage("An invalid email address format has been provided during member registration.");
            }

            @Test
            void givenInvalidEmailAddressFormatSpecialCharacter_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "babysharkisa@dum.d_um",
                        new LicensePlate("SHRK123", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(InvalidEmailFormatException.class)
                        .hasMessage("An invalid email address format has been provided during member registration.");
            }
        }
        @Nested
        @DisplayName("License plate validation tests")
        class LicensePlateValidationTest {
            @Test
            void givenNullLicensePlateNumber_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate(null, "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingLicensePlateNumberException.class)
                        .hasMessage("No license plate number has been provided during member registration.");
            }

            @Test
            void givenEmptyLicensePlateNumber_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingLicensePlateNumberException.class)
                        .hasMessage("No license plate number has been provided during member registration.");
            }

            @Test
            void givenBlankLicensePlateNumber_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("  ", "Belgium")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingLicensePlateNumberException.class)
                        .hasMessage("No license plate number has been provided during member registration.");
            }

            @Test
            void givenNullLicensePlateIssuingCountry_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", null)
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingLicensePlateIssuingCountryException.class)
                        .hasMessage("No license plate issuing country has been provided during member registration.");
            }

            @Test
            void givenEmptyLicensePlateIssuingCountry_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingLicensePlateIssuingCountryException.class)
                        .hasMessage("No license plate issuing country has been provided during member registration.");
            }

            @Test
            void givenBlankLicensePlateIssuingCountry_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
                //GIVEN
                RegisterMemberDto expected = new RegisterMemberDto(
                        "Baby",
                        "Shark",
                        new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                        "0474555999",
                        "baby.shark@music.bad",
                        new LicensePlate("SHRK123", "  ")
                );

                //WHEN
                RestAssured
                        .given()
                        .body(expected)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.BAD_REQUEST.value());

                Throwable thrown = Assertions.catchThrowable(() -> memberService.registerMember(expected));

                //THEN
                Assertions.assertThat(thrown)
                        .isInstanceOf(MissingLicensePlateIssuingCountryException.class)
                        .hasMessage("No license plate issuing country has been provided during member registration.");
            }
        }
    }
}