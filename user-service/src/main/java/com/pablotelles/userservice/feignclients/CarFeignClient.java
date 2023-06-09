package com.pablotelles.userservice.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pablotelles.userservice.models.Car;

//Paso 2 con feign client
@FeignClient(name = "car-service", url = "http://localhost:8002/api/v1/cars")
public interface CarFeignClient {
    
    @PostMapping("/create")
    public Car createCar(@RequestBody Car car);

    @GetMapping("/userid/{id}")
    public List<Car> getCarByUserId(@PathVariable("id") Long id);

}
