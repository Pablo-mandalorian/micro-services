package com.pablotelles.carservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pablotelles.carservice.entity.Car;

public interface CarRepository extends JpaRepository<Car,Long>{

    List<Car> findByUserId(Long userId);
    
}
