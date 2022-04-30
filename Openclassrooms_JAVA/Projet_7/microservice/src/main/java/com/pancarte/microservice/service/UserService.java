package com.pancarte.microservice.service;


import com.pancarte.microservice.model.User;

import java.util.List;

public interface UserService {

    public User findUserByEmail(String email);
    String queryUser();


    public void saveUser(User user);
    List<User> findAll();


}
