package com.pablotelles.carservice.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pablotelles.carservice.entity.Car;
import com.pablotelles.carservice.repository.CarRepository;
import com.pablotelles.carservice.service.CarService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CarServiceImpl implements CarService{

    @Autowired
    private final CarRepository carRepository;

    @Override
    public Car createCar(Car car) {
        log.info("Creating new Car: {} ", car.getBrand());
        return carRepository.save(car);
    }

    @Override
    public List<Car> list() {
        log.info("Fetching All cars...");
        return carRepository.findAll();
    }

    @Override
    public Car getCar(Long id) {
        log.info("Fetching car: {}", id);
        return carRepository.findById(id).get();
    }

    @Override
    public Boolean deleteCar(Long id) {
        log.info("Deleting car: {}", id);
        carRepository.deleteById(id);
        return true;
    }

    @Override
    public Car updateCar(Car car) {
        log.info("Updating car", car.getId());
        return carRepository.save(car);
    }

    @Override
    public List<Car> findByUserId(Long id) {
        log.info("Fetching car by userId: {}", id);
        return carRepository.findByUserId(id);
    }
    
}
