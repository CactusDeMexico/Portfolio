package com.pancarte.proxy;

import com.pancarte.Model.Book_List;
import com.pancarte.Model.Borrow;
import com.pancarte.Model.User;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

import java.util.List;

@FeignClient(name = "microservice-library", url = "http://localhost:9090", decode404 = true)
public interface MicroserviceLibraryProxy {

    @RequestMapping(value = "/test")
    String test();

    @RequestMapping(value = {"/user"})
    String queryUser(@RequestParam("email") String email);

    @RequestMapping(value = "/role")
    String queryRole(@RequestParam("email") String email);

    @RequestMapping(value = "/find")
    User findUserByEmail(@RequestParam("email") String email);

    @RequestMapping(value = "/search")
    List<Book_List> searchBooks(@RequestParam("search") String search);

    @RequestMapping(value = "/getAllBooks")
    List<Book_List> getAllBooks();
//@RequestParam("user") @Valid User user
    @RequestMapping(value = {"/createUser"})
     void createUser(@RequestParam("lastName") String lastName,@RequestParam("name") String name,@RequestParam("email") String email,@RequestParam("password") String password);
    @RequestMapping(value = {"/getborrow"})
    public  List<Borrow> getborrowedBook(@RequestParam("idUser") int idUser);

    @RequestMapping(value = {"/borrow"})
    public void borrow(@RequestParam("idBook") int idBook,@RequestParam("idUser") int idUser);

    @RequestMapping(value = {"/extendBorrow"})
    public void extendBorrow(@RequestParam("idBorrow") int idBorrow);

    }
