package com.switchfully.sharkitects.parking_lot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository extends JpaRepository<CreateParkingDto, Integer> {

}
