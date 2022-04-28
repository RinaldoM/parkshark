package com.switchfully.sharkitects.members;

import com.switchfully.sharkitects.members.dtos.MemberDto;
import com.switchfully.sharkitects.members.dtos.RegisterMemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "members")
public class MemberController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto registerMember (@RequestBody RegisterMemberDto registerMemberDto ) {
        return new MemberDto(
                "123",
                "Baby",
                "Shark",
                new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                "0474555999",
                "baby.shark@music.bad",
                new LicensePlate("SHRK123", "Belgium"),
                registerMemberDto.getRegistrationDate()
        );
    }
}
