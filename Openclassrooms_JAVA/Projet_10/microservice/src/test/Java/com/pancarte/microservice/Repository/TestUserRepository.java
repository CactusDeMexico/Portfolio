package com.pancarte.microservice.Repository;

import com.pancarte.microservice.model.Role;
import com.pancarte.microservice.model.User;
import com.pancarte.microservice.repository.UserRepository;
import com.pancarte.microservice.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserRepository {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    private Date date = format.parse("02-06-2018");
    Set<Role> role = new HashSet<>();

    private User user = new User();
    private User user2 = new User();
    //  private List<Role> role = new ArrayList();
    private Role dummy = new Role();

    public TestUserRepository() throws ParseException {
    }

    @Before
    public void setUp() {
        dummy.setId(1);
        dummy.setRole("admin");
        role.add(dummy);

        user = new User(1, "jean", "jule", "j@gmail.com", "253", 1, date, date, role);
        user2 = new User(2, "jeanne", "julie", "lk@gmail.com", "253", 1, date, date, role);
    }

    @Test
    public void findUserByID() {
        int id = 1;
        Mockito.when(userRepository.findById(id)).thenReturn(user);
        assertEquals(1, userRepository.findById(id).getId());
    }

    @Test
    public void findUserByIDWrongId() {
        int id = 2;
     ;
        Mockito.when(userRepository.findById(id)).thenReturn(null);
        assertNull(userService.findById(id));
    }

    @Test
    public void findUserByEmail() {
        String mail = "j@gmail.com";

        Mockito.when(userRepository.findByEmail(mail)).thenReturn(user);
        assertEquals(user,userRepository.findByEmail(mail));
    }

    @Test
    public void findUserByEmailWrongEmail() {
        String mail = "j@gmail.com";

        Mockito.when(userRepository.findByEmail(mail)).thenReturn(null);
        assertNull(userService.findUserByEmail(mail));
    }

    @Test
    public void findAllUser() {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        assertEquals(2,userList.size());
    }

    @Test
    public void findAllUserNoUser() {
        List<User> userList = new ArrayList<>();

        Mockito.when(userRepository.findAll()).thenReturn(userList);
        assertEquals(0,userList.size());
    }
}
