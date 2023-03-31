package com.pablotelles.userservice.service;

import java.util.Collection;
import java.util.List;

import com.pablotelles.userservice.entity.User;
import com.pablotelles.userservice.models.Bike;
import com.pablotelles.userservice.models.Car;

public interface UserService {
    User createUser(User user);
    Collection<User> list();
    User getPerson(Long id);
    User updatePerson(User user);
    Boolean deletePerson(Long id);
    List<Car> getCars(Long userId);
    List<Bike> getBikes(Long userId);
}
