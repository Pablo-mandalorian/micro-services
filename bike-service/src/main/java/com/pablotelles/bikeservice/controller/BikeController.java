package com.pablotelles.bikeservice.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pablotelles.bikeservice.entity.Bike;
import com.pablotelles.bikeservice.service.implementation.BikeServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/bikes")
public class BikeController {
    
    private final BikeServiceImpl bikeServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<Bike> createBike(@RequestBody Bike bike){
        try {
            Bike newBike = bikeServiceImpl.createBike(bike);
            return ResponseEntity.created(new URI("api/v1/bikes/"+bike.getId())).body(newBike);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Bike>> getAllBikes(){
        return ResponseEntity.ok(bikeServiceImpl.getAllBikes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bike> getBikeById(@PathVariable("id") Long id){
        return ResponseEntity.ok(bikeServiceImpl.getBikeById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBikeById(@PathVariable("id") Long id){
        return ResponseEntity.ok(bikeServiceImpl.deleteBike(id));
    }

    @GetMapping("/userid/{id}")
    public ResponseEntity<List<Bike>> getBikesByUserId(@PathVariable("id") Long id){
        return ResponseEntity.ok(bikeServiceImpl.findByUserId(id));
    }

}
