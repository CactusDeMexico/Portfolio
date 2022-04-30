package com.pancarte.proxy;

import com.pancarte.Model.*;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.*;


import java.util.List;

@FeignClient(name = "microservice-library", url = "http://localhost:9090", decode404 = true)
public interface MicroserviceLibraryProxy {

    @RequestMapping(value = "/test")
    String test();

    @RequestMapping(value = {"/returnbook"})
    void returnBook(@RequestParam("idborrow") int idborrow);

    @RequestMapping(value = {"/getallreser"})
    public List<Reservation> getAllReservation();

    @RequestMapping(value = {"/getallborrow"})
    public List<Borrow> getallborrowedBook();

    @RequestMapping(value = {"/deleteresa"})
    public void deleteReservations(@RequestParam("idresa") int idresa);
    @RequestMapping(value = {"/reserv"})
    void reserv(@RequestParam("idbook") int idBook, @RequestParam("iduser") int idUser);

    @RequestMapping(value = {"/user"})
    String queryUser(@RequestParam("email") String email);

    @RequestMapping(value = {"/cancel"})
    void cancel(@RequestParam("title") String title, @RequestParam("iduser") int idUser);

    @RequestMapping(value = {"/getresnbybook"})
    List<Reservation> getAllReservations(@RequestParam("title") String title);

    @RequestMapping(value = "/role")
    String queryRole(@RequestParam("email") String email);

    @RequestMapping(value = "/find")
    User findUserByEmail(@RequestParam("email") String email);

    @RequestMapping(value = "/search")
    List<Book_List> searchBooks(@RequestParam("search") String search);

    @RequestMapping(value = "/getAllBooks")
    List<Book_List> getAllBooks();

    @RequestMapping(value = {"/getBooks"})
    public List<Book> getBook();

    //@RequestParam("user") @Valid User user
    @RequestMapping(value = {"/createUser"})
    void createUser(@RequestParam("lastName") String lastName, @RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password);

    @RequestMapping(value = {"/getborrow"})
    public List<Borrow> getborrowedBook(@RequestParam("idUser") int idUser);

    @RequestMapping(value = {"/borrow"})
    public void borrow(@RequestParam("idBook") int idBook, @RequestParam("idUser") int idUser);

    @RequestMapping(value = {"/extendBorrow"})
    public void extendBorrow(@RequestParam("idBorrow") int idBorrow);
}
