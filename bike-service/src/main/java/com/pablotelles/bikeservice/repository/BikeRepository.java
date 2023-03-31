package com.pablotelles.bikeservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pablotelles.bikeservice.entity.Bike;

public interface BikeRepository extends JpaRepository<Bike,Long>{
    
    List<Bike> findBikeByUserId(Long userId);

}
