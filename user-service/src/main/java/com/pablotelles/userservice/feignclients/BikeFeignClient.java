package com.pablotelles.userservice.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pablotelles.userservice.models.Bike;

@FeignClient(name = "bike-service")
public interface BikeFeignClient {
    
    @PostMapping("/create")
    public Bike createBike(@RequestBody Bike bike);

    @GetMapping("/userid/{id}")
    public List<Bike> getBikeByUserId(@PathVariable("id") Long id);

}
