package com.pablotelles.userservice.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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

import com.pablotelles.userservice.entity.User;
import com.pablotelles.userservice.models.Bike;
import com.pablotelles.userservice.models.Car;
import com.pablotelles.userservice.service.implementation.UserServiceImpl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    
    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public ResponseEntity<Collection<User>> getAllUsers(){
        return ResponseEntity.ok(this.userServiceImpl.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.userServiceImpl.getPerson(id));
    }

    @PostMapping("/create")
    public ResponseEntity<User> createrUser(@RequestBody User user){
        try {
            User userToBeSave = this.userServiceImpl.createUser(user);
            return ResponseEntity.created(new URI("/api/v1/users/"+userToBeSave.getId())).body(userToBeSave);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        return ResponseEntity.ok(this.userServiceImpl.updatePerson(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.userServiceImpl.deletePerson(id));
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCars")
    @GetMapping("/car/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(userServiceImpl.getCars(userId));
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCar")
    @PostMapping("/savecar/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable("userId")Long userId, @RequestBody Car car){
        return ResponseEntity.ok(userServiceImpl.saveCar(userId, car));
    }  

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackGetBikes")
    @GetMapping("/bike/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(userServiceImpl.getBikes(userId));
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackSaveBike")
    @PostMapping("/savebike/{userId}")
    public ResponseEntity<Bike> saveBike(@PathVariable("userId") Long userId, @RequestBody Bike bike){
        return ResponseEntity.ok(userServiceImpl.saveBike(userId, bike));
    }

    @CircuitBreaker(name = "allCB", fallbackMethod = "fallBackGetAll")
    @GetMapping("/getall/{id}")
    public ResponseEntity<Map<String,Object>> getAll(@PathVariable("id") Long id){
        Map<String, Object> results = userServiceImpl.getUserAndVehicles(id);
        return ResponseEntity.ok(results);
    }

    private ResponseEntity<List<Car>> fallBackGetCars(@PathVariable("userId") Long userId, RuntimeException e){
        return new ResponseEntity("El usuario "+userId+" no tiene coches", HttpStatus.OK);
    }

    private ResponseEntity<Car> fallBackSaveCar(@PathVariable("userId")Long userId, @RequestBody Car car, RuntimeException e){
        return new ResponseEntity("El usuario "+userId+" no tiene dinero para coches", HttpStatus.OK);
    }

    private ResponseEntity<List<Bike>> fallBackGetBikes(@PathVariable("userId") Long userId, RuntimeException e){
        return new ResponseEntity("El usuario "+userId+" no tiene motos", HttpStatus.OK);
    }

    private ResponseEntity<Bike> fallBackSaveBike(@PathVariable("userId")Long userId, @RequestBody Bike bike, RuntimeException e){
        return new ResponseEntity("El usuario "+userId+" no tiene dinero para motos", HttpStatus.OK);
    }

    private ResponseEntity<Map<String,Object>> fallBackGetAll(@PathVariable("id") Long id, RuntimeException e){
        return new ResponseEntity("El usuario "+id+" no tiene vehiculos!", HttpStatus.OK);
    }

}
