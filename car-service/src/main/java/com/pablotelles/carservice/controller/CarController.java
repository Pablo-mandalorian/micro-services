package com.pablotelles.carservice.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pablotelles.carservice.entity.Car;
import com.pablotelles.carservice.service.implementation.CarServiceImpl;

@RestController
@RequestMapping("api/v1/cars")
public class CarController {
    
    @Autowired
    private CarServiceImpl carServiceImpl;

    @GetMapping
    public ResponseEntity<List<Car>> getCars(){
        return ResponseEntity.ok(carServiceImpl.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCar(@PathVariable("id") Long id){
        return ResponseEntity.ok(carServiceImpl.getCar(id));
    }

    @GetMapping("/userid/{id}")
    public ResponseEntity<List<Car>> getCarByUserId(@PathVariable("id") Long id){
        return ResponseEntity.ok(carServiceImpl.findByUserId(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Car> createCar(@RequestBody Car car){
        try {
            Car savedCar = this.carServiceImpl.createCar(car);
            return ResponseEntity.created(new URI("/api/v1/cars/"+car.getId())).body(savedCar);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Car> updateCar(@RequestBody Car car){
        return ResponseEntity.ok(carServiceImpl.updateCar(car));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCar(@PathVariable("id") Long id){
        return ResponseEntity.ok(carServiceImpl.deleteCar(id));
    }

}
