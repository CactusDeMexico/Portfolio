package com.pancarte.architecte.service;

import com.pancarte.architecte.model.Role;
import com.pancarte.architecte.model.User;
import com.pancarte.architecte.repository.RoleRepository;
import com.pancarte.architecte.repository.UserRepository;

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
    public User findById(int id){return userRepository.findById(id);}
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

         //Date now = Date.valueOf(LocalDate.now());

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setHidden(true);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

}