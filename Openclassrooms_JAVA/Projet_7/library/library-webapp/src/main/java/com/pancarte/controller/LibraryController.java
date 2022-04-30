package com.pancarte.controller;

import com.pancarte.Model.Book_List;
import com.pancarte.Model.Borrow;
import com.pancarte.Model.User;
import com.pancarte.proxy.MicroserviceLibraryProxy;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LibraryController {





    @Autowired
    private MicroserviceLibraryProxy Library;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public String user = "";
    public String role = "";
   public String password = "";
   public String email = "";


    @RequestMapping(value = {"/content"}, method = RequestMethod.GET)
    public ModelAndView content() {
        ModelAndView model = new ModelAndView();
        User user = new User();
        List<Book_List> book = new ArrayList<>();
        List<Borrow> borrow = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!auth.getName().equals("anonymousUser")) {
            model.addObject("userName", user.getName() + " " + user.getLastName());
        } else {
            model.addObject("userName", "0");
        }
        model.addObject("borrow", borrow);
        model.addObject("user", user);
        model.addObject("book", book);
        model.addObject("now", Date.valueOf(LocalDate.now()));
        model.setViewName("fragment/content");

        return model;
    }
    @RequestMapping(value = {"/header"}, method = RequestMethod.GET)
    public ModelAndView header() {
        ModelAndView model = new ModelAndView();
        model.addObject("userName", "0");


        model.setViewName("fragment/header");
        return model;
    }


    @RequestMapping(value = {"/index","/"}, method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = Library.findUserByEmail(auth.getName());
        if (!auth.getName().equals("anonymousUser")) {
            model.addObject("userName", user.getName() + " " + user.getLastName());
        } else {
            model.addObject("userName", "0");
        }


        List<Book_List> book;
        book = Library.getAllBooks();


        model.addObject("view", "home");
        model.addObject("book", book);


        model.setViewName("index");
        return model;
    }
    @RequestMapping(value = {"/search"}, method = RequestMethod.POST)
    public String search(String search) {


        return "redirect:/search/?search=" + search;
    }
    @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
    public ModelAndView searchIt(@RequestParam("search") String search) {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = Library.findUserByEmail(auth.getName());
        if (!auth.getName().equals("anonymousUser")) {
            model.addObject("userName", user.getName() + " " + user.getLastName());
        } else {
            model.addObject("userName", "0");
        }

        String cap = search.substring(0, 1).toUpperCase() + search.substring(1);
        String caps = search.toUpperCase();


        List<Book_List> book= Library.searchBooks(search);
        book.addAll(Library.searchBooks(cap));
        book.addAll(Library.searchBooks(caps));
        model.addObject("book", book);

        model.addObject("search", search);

        model.addObject("view", "search");
     model.setViewName("index");

        return model;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = Library.findUserByEmail(auth.getName());
        if (!auth.getName().equals("anonymousUser")) {
            model.addObject("userName", user.getName() + " " + user.getLastName());
        } else {
            model.addObject("userName", "0");
        }
        User userv = new User();
        model.addObject("view", "login");

        model.addObject("userName", "0");
        model.addObject("user", userv);


        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView model = new ModelAndView();
        User user = new User();
        model.addObject("user", user);
        // model.setViewName("user/signup");
        model.addObject("view", "signup");
        model.setViewName("index");

        model.addObject("userName", "0");

        return model;
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
    public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();
        User userExists = Library.findUserByEmail(user.getEmail());

        if (userExists != null) {
            bindingResult.rejectValue("email", "error.user", "L'email existe déja");
        }
        if (bindingResult.hasErrors()) {
            // model.setViewName("user/signup");
            model.addObject("view", "signup");
            model.setViewName("index");
        } else {
            //bCryptPasswordEncoder.encode(user.getPassword());
            Library.createUser(user.getLastName(),user.getName(),user.getEmail(),bCryptPasswordEncoder.encode(user.getPassword()));
            model.addObject("msg", "L'utilisateur à été enregistré");
            model.addObject("user", new User());

            model.addObject("userName", "0");

            //model.setViewName("user/signup");
            model.addObject("view", "login");
            model.setViewName("index");

        }

        return model;
    }


    @RequestMapping(value = {"/loggedhome"}, method = RequestMethod.GET)
    public ModelAndView loggedHome() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = Library.findUserByEmail(auth.getName());
        List<Book_List> book = Library.getAllBooks();
        List<Borrow> borrow = Library.getborrowedBook(user.getId());
        if (!auth.getName().equals("anonymousUser")) {
            model.addObject("userName", user.getName() + " " + user.getLastName());
        } else {
            model.addObject("userName", "0");
        }
        model.addObject("borrow", borrow);
        model.addObject("user", user);
        model.addObject("book", book);
        model.addObject("now", Date.valueOf(LocalDate.now()));



        model.addObject("view", "loggedhome");


        model.setViewName("index");
    return model;
    }
    @RequestMapping(value = {"/borrow"}, method = RequestMethod.POST)
    public String  borrow(@RequestParam("idbook") int idBook) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = Library.findUserByEmail(auth.getName());
        Library.borrow(idBook,user.getId());

        return "redirect:/loggedhome";

    }
 @RequestMapping(value = {"/borrowed"}, method = RequestMethod.POST)
    public String borrowed(@RequestParam("idborrow") int idBorrow) {

        Library.extendBorrow(idBorrow);
     return "redirect:/loggedhome";

    }

}
