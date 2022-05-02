package com.switchfully.sharkitects.members;

import com.switchfully.sharkitects.members.dtos.MemberDto;
import com.switchfully.sharkitects.members.dtos.RegisterMemberDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MemberTest {
    @Autowired
    MemberService memberService;

    String membershipLevel = null;

    @Test
    void givenMemberWithoutMembershipLevel_returnBronzeMembershipLevel() {
        RegisterMemberDto expected = new RegisterMemberDto("Baby",
                "Shark",
                new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                "0474555999",
                "baby.shark@music.bad",
                new LicensePlate("SHRK123", "Belgium"), membershipLevel);
        MemberDto actual = memberService.registerMember(expected);
        Assertions.assertThat(actual.getMembershipLevelName()).isEqualTo(MembershipLevelName.BRONZE.toString());
    }
    @Test
    void givenMemberWithSilverMembershipLevel_returnSilverMembershipLevel() {
        RegisterMemberDto expected = new RegisterMemberDto("Baby",
                "Shark",
                new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                "0474555999",
                "baby.shark@music.bad",
                new LicensePlate("SHRK123", "Belgium"), "silver");
        MemberDto actual = memberService.registerMember(expected);
        Assertions.assertThat(actual.getMembershipLevelName()).isEqualTo(MembershipLevelName.SILVER.toString());
    }
    @Test
    void givenMemberWithGoldMembershipLevel_returnGoldMembershipLevel() {
        RegisterMemberDto expected = new RegisterMemberDto("Baby",
                "Shark",
                new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                "0474555999",
                "baby.shark@music.bad",
                new LicensePlate("SHRK123", "Belgium"), "gold");
        MemberDto actual = memberService.registerMember(expected);
        Assertions.assertThat(actual.getMembershipLevelName()).isEqualTo(MembershipLevelName.GOLD.toString());
    }

    @Test
    void givenMemberWithBlankMembershipLevel_returnGoldMembershipLevel() {
        RegisterMemberDto expected = new RegisterMemberDto("Baby",
                "Shark",
                new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                "0474555999",
                "baby.shark@music.bad",
                new LicensePlate("SHRK123", "Belgium"), " ");
        MemberDto actual = memberService.registerMember(expected);
        Assertions.assertThat(actual.getMembershipLevelName()).isEqualTo(MembershipLevelName.BRONZE.toString());
    }
    @Test
    void givenMemberWithEmptyMembershipLevel_returnGoldMembershipLevel() {
        RegisterMemberDto expected = new RegisterMemberDto("Baby",
                "Shark",
                new Address("Annoying music st.", "6", new PostalCodeCity("1000", "Brussels")),
                "0474555999",
                "baby.shark@music.bad",
                new LicensePlate("SHRK123", "Belgium"), "");
        MemberDto actual = memberService.registerMember(expected);
        Assertions.assertThat(actual.getMembershipLevelName()).isEqualTo(MembershipLevelName.BRONZE.toString());
    }
}