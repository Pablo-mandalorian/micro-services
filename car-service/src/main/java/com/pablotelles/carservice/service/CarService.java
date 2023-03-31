package com.pablotelles.carservice.service;

import java.util.List;

import com.pablotelles.carservice.entity.Car;

public interface CarService {
    Car createCar(Car car);
    List<Car> list();
    Car getCar(Long id);
    Boolean deleteCar(Long id);
    Car updateCar(Car car);
    List<Car> findByUserId(Long id);
}
