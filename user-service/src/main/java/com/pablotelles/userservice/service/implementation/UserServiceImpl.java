package com.pablotelles.userservice.service.implementation;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pablotelles.userservice.entity.User;
import com.pablotelles.userservice.feignclients.BikeFeignClient;
import com.pablotelles.userservice.feignclients.CarFeignClient;
import com.pablotelles.userservice.models.Bike;
import com.pablotelles.userservice.models.Car;
import com.pablotelles.userservice.repository.UserRepository;
import com.pablotelles.userservice.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private final UserRepository userRepository;

    // 2. Injectar Rest Template
    //3. Crear modelos que representan las entidades de los otros dos servicios
    //en un paquete nuevo
    @Autowired
    private RestTemplate restTemplate;

    //  Injectar Car Feign Client
    @Autowired
    private CarFeignClient carFeignClient;

    @Autowired
    private BikeFeignClient bikeFeignClient;

    @Override
    public User createUser(User user) {
        log.info("Saving new User: {}", user.getFirstName());
        return this.userRepository.save(user);
    }

    @Override
    public Collection<User> list() {
        log.info("Fetching all users...");
        return this.userRepository.findAll();
    }

    @Override
    public User getPerson(Long id) {
        log.info("Fetching user: {}", id);
        return this.userRepository.findById(id).get();
    }

    @Override
    public User updatePerson(User user) {
        log.info("Updating server: {}", user.getId());
        return this.userRepository.save(user);
    }

    @Override
    public Boolean deletePerson(Long id) {
       log.info("Deleting user: {}", id);
       this.userRepository.deleteById(id);
       return true;
    }


    //Comunicacion con el servicio car-service
    @Override
    public List<Car> getCars(Long userId) {
        log.info("Fetching car for the user: {}", userId);
        List<Car> cars = restTemplate.getForObject("http://localhost:8002/api/v1/cars/userid/"+userId, List.class);
        return cars;
    }


    //Comunicacion con el servicio bike-service
    @Override
    public List<Bike> getBikes(Long userId) {
        log.info("Fetching by userId: {}", userId);
        List<Bike> bikes = restTemplate.getForObject("http://localhost:8003/api/v1/bikes/userId/"+userId, List.class);
        return bikes;
    }

    public Car saveCar(Long userId, Car car){
        car.setUserId(userId);
        log.info("Saving new car: {}", car.getBrand());
        return carFeignClient.createCar(car);
    }
    
    public Bike saveBike(Long userId, Bike bike){
        bike.setUserId(userId);
        log.info("Saving new car: {}", bike.getBrand());
        return bikeFeignClient.createBike(bike);
    }

    public Map<String, Object> getUserAndVehicles(Long userId){
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);
        if (user==null) {
            result.put("Mensaje", "No exite el usuario");
            return result;
        }

        result.put("User", user);
        List<Car> cars = carFeignClient.getCarByUserId(userId);
        if (cars.isEmpty()) {
            result.put("Cars", "No tiene autos");
        }else{
            result.put("Cars", cars);
        }

        List<Bike> bikes = bikeFeignClient.getBikeByUserId(userId);

        if (bikes.isEmpty()) {
            result.put("Bikes", "No tiene autos");
        }else{
            result.put("Bikes", bikes);
        }

        return result;

    }


}
