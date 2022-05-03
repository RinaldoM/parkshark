package com.switchfully.sharkitects.members;

import oracle.jdbc.proxy.annotation.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostalCodeCityService {
    private final PostalCodeCityRepository postalCodeCityRepository;

    public PostalCodeCityService(PostalCodeCityRepository postalCodeCityRepository) {
        this.postalCodeCityRepository = postalCodeCityRepository;
    }

    public List<PostalCodeCity> getAllPostalCodeCity(){
        return postalCodeCityRepository.findAll();
    }

    public Boolean checkIfPostalCodeCityAlreadyExists(PostalCodeCity postalCodeCity){
        List<PostalCodeCity> allPostalCodeCity = getAllPostalCodeCity();
        return allPostalCodeCity.stream()
                .filter(city -> city.equals(postalCodeCity))
                .toList()
                .size()>0;
    }

    public PostalCodeCity getByZipcode(String zipcode){
        return postalCodeCityRepository.findPostalCodeCityByZipCode(zipcode);
    }
}
