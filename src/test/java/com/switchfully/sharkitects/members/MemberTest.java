package com.switchfully.sharkitects.members;

import com.switchfully.sharkitects.members.dtos.MemberDto;
import com.switchfully.sharkitects.members.dtos.RegisterMemberDto;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MemberTest {
    @Autowired
    MemberService memberService;

    @Test
    void givenMemberWithoutMembershipLevel_returnBronzeMembershipLeven() {
        RegisterMemberDto expected = new RegisterMemberDto("Baby",
                "Shark",
                new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                "0474555999",
                "baby.shark@music.bad",
                new LicensePlate("SHRK123", "Belgium"));
        MemberDto actual = memberService.registerMember(expected);
        Assertions.assertThat(actual.getMembershipLevelName()).isEqualTo(MembershipLevelName.BRONZE.toString());
    }
}