package com.pablotelles.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pablotelles.userservice.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
    
}
