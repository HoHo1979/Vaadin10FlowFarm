package com.iotarch.farm.repository;


import com.iotarch.farm.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

@Component
@ApplicationScope
public class UserRepositoryImp implements UserRepository {
    @Override
    public User findByEmailIgnoreCase(String email) {
        return null;
    }
}
