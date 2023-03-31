package com.pablotelles.bikeservice.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pablotelles.bikeservice.entity.Bike;
import com.pablotelles.bikeservice.repository.BikeRepository;
import com.pablotelles.bikeservice.service.BikeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BikeServiceImpl implements BikeService{

    private final BikeRepository bikeRepository;

    @Override
    public Bike createBike(Bike bike) {
        log.info("Creating new Bike: {}",bike.getBrand());
        return bikeRepository.save(bike);
    }

    @Override
    public List<Bike> getAllBikes() {
        log.info("Fetching all Bikes");
        return bikeRepository.findAll();
    }

    @Override
    public Bike getBikeById(Long id) {
        log.info("Fetching bike:{}", id);
        return bikeRepository.findById(id).get();
    }

    @Override
    public Boolean deleteBike(Long id) {
        log.info("Deleting bike: {}",id);
        bikeRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Bike> findByUserId(Long userId) {
        log.info("Fetching Bikes by user id: {}", userId);
        return bikeRepository.findBikeByUserId(userId);   
    }
    
}
