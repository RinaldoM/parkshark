package com.switchfully.sharkitects.members;

import com.switchfully.sharkitects.members.dtos.DisplayMemberDto;
import com.switchfully.sharkitects.members.dtos.MemberDto;
import com.switchfully.sharkitects.members.dtos.RegisterMemberDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
                member.getRegistrationDate(),
                member.getMembershipLevel().getMembershipLevelName().toString());
    }

    public DisplayMemberDto toDisplayMemberDto(Member member) {
        return new DisplayMemberDto(member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getPhoneNumber(),
                member.getEmail(),
                member.getLicensePlate().getNumber(),
                member.getRegistrationDate().toString(),
                member.getMembershipLevel().getMembershipLevelName().toString());
    }


    public List<DisplayMemberDto> toDisplayMemberDto(List<Member> memberList) {
        return memberList.stream()
                .map(this::toDisplayMemberDto)
                .collect(Collectors.toList());
    }
}
