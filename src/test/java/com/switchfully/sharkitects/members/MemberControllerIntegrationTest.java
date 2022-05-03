package com.switchfully.sharkitects.members;

import com.switchfully.sharkitects.infrastructure.exceptions.EmptyInputException;
import com.switchfully.sharkitects.infrastructure.exceptions.InvalidEmailFormatException;
import com.switchfully.sharkitects.members.dtos.DisplayMemberDto;
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
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.util.Lists.newArrayList;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MemberControllerIntegrationTest {
    String membershipLevel = null;
    @LocalServerPort
    private int port;

    @Autowired
    private MemberRepository memberRepository;

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
                new LicensePlate("SHRK123", "Belgium"),
                membershipLevel);

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

    @Test
    void givenAnAlreadyExistingNameAndLicensePlateCombination_whenRegisterMember_thenBadRequestIsReturnedAndExceptionIsThrown() {
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

        RegisterMemberDto expected = new RegisterMemberDto(
                "Baby",
                "Shark",
                new Address("other place", "8", new PostalCodeCity("5000", "Namur")),
                "01234",
                "some@mail.com",
                new LicensePlate("SHRK123", "Belgium"),
                membershipLevel);


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
                .isInstanceOf(NameLicensePlateCombinationExistsException.class)
                .hasMessage("A member with the same name and the same license plate already exists");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty first name");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty first name");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty first name");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty last name");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty last name");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty last name");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty street name");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty street name");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty street name");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty street number");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty street number");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty street number");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty zip code");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty zip code");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty zip code");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty city");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty city");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty city");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty phone number");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty phone number");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty phone number");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty email address");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty email address");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty email address");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .hasMessage("An invalid email address format has been provided");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .hasMessage("An invalid email address format has been provided");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .hasMessage("An invalid email address format has been provided");
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
                        new LicensePlate("SHRK123", "Belgium"),
                        membershipLevel);

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
                        .hasMessage("An invalid email address format has been provided");
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
                        new LicensePlate(null, "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty license plate number");
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
                        new LicensePlate("", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty license plate number");
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
                        new LicensePlate("  ", "Belgium"),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty license plate number");
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
                        new LicensePlate("SHRK123", null),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty license plate issuing country");
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
                        new LicensePlate("SHRK123", ""),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty license plate issuing country");
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
                        new LicensePlate("SHRK123", "  "),
                        membershipLevel);

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
                        .isInstanceOf(EmptyInputException.class)
                        .hasMessage("Empty license plate issuing country");
            }


        }
    }
    @Test
    @Sql("classpath:add_member.sql")
    void getAllMembers() {
        List<DisplayMemberDto> actualMemberList = newArrayList(given()
                .baseUri("http://localhost")
                .port(port)
                .when()
                .contentType(JSON)
                .get("/members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().as(DisplayMemberDto[].class));

        Assertions.assertThat(actualMemberList).extracting(DisplayMemberDto::getFirstName).contains("Baby");
        Assertions.assertThat(actualMemberList).extracting(DisplayMemberDto::getEmail).contains("shark@baby.com");
    }
    @Test
    void givenPostalCityCodeThatAlreadyExists_whenRegisterMember_thenMemberRegisteredWithExistingPostalCodeCity() {
        //GIVEN
        RegisterMemberDto expected = new RegisterMemberDto(
                "Baby",
                "Shark",
                new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                "0474555999",
                "baby.shark@music.bad",
                new LicensePlate("SHRK123", "Belgium"),
                membershipLevel);
        RegisterMemberDto testMember = new RegisterMemberDto(
                "Daddy",
                "Shark",
                new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                "0474555999",
                "baby.shark@music.bad",
                new LicensePlate("GSG123", "Belgium"),
                membershipLevel);
        MemberDto testMemberDto = memberService.registerMember(testMember);

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
        Assertions.assertThat(actual.getAddress().getPostalCodeCity().getId()).isEqualTo((testMemberDto.getAddress().getPostalCodeCity().getId()));

    }
    @Test
    void givenPostalCityCodeThatDoesNotExists_whenRegisterMember_thenMemberRegisteredWithNewPostalCodeCity() {
        //GIVEN
        RegisterMemberDto expected = new RegisterMemberDto(
                "Baby",
                "Shark",
                new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                "0474555999",
                "baby.shark@music.bad",
                new LicensePlate("SHRK123", "Belgium"),
                membershipLevel);
        RegisterMemberDto testMember = new RegisterMemberDto(
                "Daddy",
                "Shark",
                new Address("Annoying music st.", "6", new PostalCodeCity("3600", "Genk")),
                "0474555999",
                "baby.shark@music.bad",
                new LicensePlate("GSG123", "Belgium"),
                membershipLevel);
        MemberDto testMemberDto = memberService.registerMember(testMember);

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
        Assertions.assertThat(actual.getAddress().getPostalCodeCity().getId()).isNotEqualTo((testMemberDto.getAddress().getPostalCodeCity().getId()));

    }



}