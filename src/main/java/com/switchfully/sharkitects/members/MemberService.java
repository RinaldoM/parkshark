package com.switchfully.sharkitects.members;

import com.switchfully.sharkitects.members.dtos.MemberDto;
import com.switchfully.sharkitects.members.dtos.RegisterMemberDto;
import org.springframework.stereotype.Service;

@Service
public class MemberService {


    private final MemberMapper memberMapper;
//    private final MemberRepository memberRepository;

    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public MemberDto registerMember(RegisterMemberDto registerMemberDto) {
        Member memberToRegister = memberMapper.toMember(registerMemberDto);
//        Member registeredMember = memberRepository.save(memberToRegister);
        return memberMapper.toDto(memberToRegister);

    }
}
