package com.pancarte.climb.demo.service;

import com.pancarte.climb.demo.model.User;

import java.util.List;

public interface UserService {

    public User findUserByEmail(String email);


    public void saveUser(User user);
    List<User> findAll();


}
