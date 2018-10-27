package com.iotarch.farm.repository;


import com.iotarch.farm.entity.User;

public interface UserRepository {
    User findByEmailIgnoreCase(String email);
}
