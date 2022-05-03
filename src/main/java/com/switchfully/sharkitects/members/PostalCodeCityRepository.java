package com.switchfully.sharkitects.members;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostalCodeCityRepository extends JpaRepository<PostalCodeCity, Long> {

    PostalCodeCity findPostalCodeCityByZipCode(String zipcode);
}
