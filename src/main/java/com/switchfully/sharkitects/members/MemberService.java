package com.switchfully.sharkitects.members;

import com.switchfully.sharkitects.infrastructure.Infrastructure;
import com.switchfully.sharkitects.infrastructure.exceptions.EmptyInputException;
import com.switchfully.sharkitects.infrastructure.exceptions.InvalidEmailFormatException;
import com.switchfully.sharkitects.members.dtos.DisplayMemberDto;
import com.switchfully.sharkitects.members.dtos.MemberDto;
import com.switchfully.sharkitects.members.dtos.RegisterMemberDto;
import com.switchfully.sharkitects.members.exceptions.NameLicensePlateCombinationExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {


    public static final String DEFAULT_MEMBERSHIP_LEVEL = "bronze";
    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;
    private final MembershipLevelRepository membershipLevelRepository;
    private final PostalCodeCityService postalCodeCityService;

    private final Logger logger = LoggerFactory.getLogger(MemberService.class);

    public MemberService(MemberMapper memberMapper, MemberRepository memberRepository, MembershipLevelRepository membershipLevelRepository, PostalCodeCityService postalCodeCityService) {
        this.memberMapper = memberMapper;
        this.memberRepository = memberRepository;
        this.membershipLevelRepository = membershipLevelRepository;
        this.postalCodeCityService = postalCodeCityService;
    }

    public MemberDto registerMember(RegisterMemberDto registerMemberDto) {
        logger.info("Attempting to register a new member");
        checkEachInputField(registerMemberDto);
        Member memberToRegister = memberMapper.toMember(registerMemberDto);
        memberToRegister.setMembershipLevel(getByMembershipLevelName(registerMemberDto.getMembershipLevel()));

        if(postalCodeCityService.checkIfPostalCodeCityAlreadyExists(registerMemberDto.getAddress().getPostalCodeCity())){
            PostalCodeCity byZipcode = postalCodeCityService.getByZipcode(registerMemberDto.getAddress().getPostalCodeCity().getZipCode());
            memberToRegister.getAddress().setPostalCodeCity(byZipcode);
        }

        Member registeredMember = memberRepository.save(memberToRegister);
        logger.info("New member has been registered");
        return memberMapper.toDto(registeredMember);
    }

    private MembershipLevel getByMembershipLevelName(String name) {
        if(name == null){
            name = DEFAULT_MEMBERSHIP_LEVEL;
        }
        return membershipLevelRepository.findByMembershipLevelName(MembershipLevelName.getLevelName(name.toUpperCase()));
    }

    private boolean memberWithSameNameAndPlateExists(RegisterMemberDto registerMemberDto) {
        List<Member> membersWithSameNamePlate = memberRepository.findAllByFirstNameAndLastName(registerMemberDto.getFirstName(), registerMemberDto.getLastName()).stream()
                .filter(member -> member.getLicensePlate().equals(registerMemberDto.getLicensePlate()))
                .toList();
        return membersWithSameNamePlate.size() > 0;
    }

    private void checkEachInputField(RegisterMemberDto registerMemberDto) {
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(registerMemberDto.getFirstName()), new EmptyInputException("first name"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(registerMemberDto.getLastName()), new EmptyInputException("last name"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(registerMemberDto.getAddress().getStreetName()), new EmptyInputException("street name"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(registerMemberDto.getAddress().getStreetNumber()), new EmptyInputException("street number"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(registerMemberDto.getAddress().getPostalCodeCity().getZipCode()), new EmptyInputException("zip code"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(registerMemberDto.getAddress().getPostalCodeCity().getCity()), new EmptyInputException("city"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(registerMemberDto.getPhoneNumber()), new EmptyInputException("phone number"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(registerMemberDto.getEmail()), new EmptyInputException("email address"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(registerMemberDto.getLicensePlate().getNumber()), new EmptyInputException("license plate number"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(registerMemberDto.getLicensePlate().getIssuingCountry()), new EmptyInputException("license plate issuing country"));

        Infrastructure.inputValidation(Infrastructure.isEmailFormatIncorrect(registerMemberDto.getEmail()), new InvalidEmailFormatException());

        Infrastructure.inputValidation(memberWithSameNameAndPlateExists(registerMemberDto), new NameLicensePlateCombinationExistsException());
    }


    public List<DisplayMemberDto> getAllMembers() {
        logger.info("All members are displayed.");
        return memberMapper.toDisplayMemberDto(memberRepository.findAll());
    }


}
