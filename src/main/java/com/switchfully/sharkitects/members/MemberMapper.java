package com.switchfully.sharkitects.members;

import com.switchfully.sharkitects.members.dtos.MemberDto;
import com.switchfully.sharkitects.members.dtos.RegisterMemberDto;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toMember(RegisterMemberDto registerMemberDto) {
        return new Member(
                registerMemberDto.getFirstName(),
                registerMemberDto.getLastName(),
                registerMemberDto.getAddress(),
                registerMemberDto.getPhoneNumber(),
                registerMemberDto.getEmail(),
                registerMemberDto.getLicensePlate(),
                registerMemberDto.getRegistrationDate()
        );
    }

    public MemberDto toDto(Member member) {
        return new MemberDto(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getAddress(),
                member.getPhoneNumber(),
                member.getEmail(),
                member.getLicensePlate(),
                member.getRegistrationDate()
        );
    }
}
