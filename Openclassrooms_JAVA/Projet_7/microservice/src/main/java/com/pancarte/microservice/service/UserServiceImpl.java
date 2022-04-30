package com.pancarte.microservice.service;


import com.pancarte.microservice.model.Role;
import com.pancarte.microservice.model.User;
import com.pancarte.microservice.repository.RoleRepository;
import com.pancarte.microservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Qualifier("roleRepository")
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public String queryUser() {
        return queryUser();
    }

    public List<User> findAll(){return  userRepository.findAll();}

    @Override
    public void saveUser(User user) {

        System.out.println("en cours "+user.getLastName()+user.getName()+user.getEmail()+user.getPassword());
        Date now = Date.valueOf(LocalDate.now());
        user.setCreationDate(now);
        user.setUpdateDate(now);
        user.setPassword(user.getPassword());
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

}