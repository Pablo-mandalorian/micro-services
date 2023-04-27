package com.pablotelles.userservice.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pablotelles.userservice.models.Car;

//Paso 2 con feign client
@FeignClient(name = "car-service")
public interface CarFeignClient {
    
    @PostMapping("/api/v1/cars/create")
    public Car createCar(@RequestBody Car car);

    @GetMapping("/api/v1/cars/userid/{id}")
    public List<Car> getCarByUserId(@PathVariable("id") Long id);

}
