package com.switchfully.sharkitects.members;

import com.switchfully.sharkitects.members.dtos.MemberDto;
import com.switchfully.sharkitects.members.dtos.RegisterMemberDto;
import com.switchfully.sharkitects.members.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {


    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;

    private final Logger logger = LoggerFactory.getLogger(MemberService.class);

    public MemberService(MemberMapper memberMapper, MemberRepository memberRepository) {
        this.memberMapper = memberMapper;
        this.memberRepository = memberRepository;
    }

    public MemberDto registerMember(RegisterMemberDto registerMemberDto) {
        logger.info("Registering a new member");

        checkEachInputField(registerMemberDto);

        Member memberToRegister = memberMapper.toMember(registerMemberDto);
        Member registeredMember = memberRepository.save(memberToRegister);
        logger.info("New member has been registered");
        return memberMapper.toDto(registeredMember);

    }

    private void checkEachInputField(RegisterMemberDto registerMemberDto) {
        inputValidation(isNullEmptyOrBlank(registerMemberDto.getFirstName()), new MissingFirstNameException());
        inputValidation(isNullEmptyOrBlank(registerMemberDto.getLastName()), new MissingLastNameException());
        inputValidation(isNullEmptyOrBlank(registerMemberDto.getAddress().getStreetName()), new MissingStreetNameException());
        inputValidation(isNullEmptyOrBlank(registerMemberDto.getAddress().getStreetNumber()), new MissingStreetNumberException());
        inputValidation(isNullEmptyOrBlank(registerMemberDto.getAddress().getPostalCodeCity().getZipCode()), new MissingZipCodeException());
        inputValidation(isNullEmptyOrBlank(registerMemberDto.getAddress().getPostalCodeCity().getCity()), new MissingCityException());
        inputValidation(isNullEmptyOrBlank(registerMemberDto.getPhoneNumber()), new MissingPhoneNumberException());
        inputValidation(isNullEmptyOrBlank(registerMemberDto.getEmail()), new MissingEmailException());
        inputValidation(isNullEmptyOrBlank(registerMemberDto.getLicensePlate().getNumber()), new MissingLicensePlateNumberException());
        inputValidation(isNullEmptyOrBlank(registerMemberDto.getLicensePlate().getIssuingCountry()), new MissingLicensePlateIssuingCountryException());
        inputValidation(!registerMemberDto.getEmail().matches("^(\\S+)@(\\S+)\\.([a-zA-Z]+)$"), new InvalidEmailFormatException());
    }

    private void inputValidation (boolean isInvalidInput, RuntimeException exception) {
        if (isInvalidInput) {
            logger.error(exception.getMessage());
            throw exception;
        }
    }

    private boolean isNullEmptyOrBlank(String stringToCheck) {
        return stringToCheck == null || stringToCheck.isEmpty() || stringToCheck.isBlank();
    }
}
