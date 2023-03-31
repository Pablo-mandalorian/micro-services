package com.pablotelles.bikeservice.service;

import java.util.List;

import com.pablotelles.bikeservice.entity.Bike;

public interface BikeService {
    Bike createBike(Bike bike);
    List<Bike> getAllBikes();
    Bike getBikeById(Long id);
    Boolean deleteBike(Long id);
    List<Bike> findByUserId(Long userId);
}
