package com.pancarte.controller;

import com.pancarte.Model.*;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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

    @RequestMapping(value = {"/content"}, method = RequestMethod.GET)
    public ModelAndView content() throws ParseException {
        ModelAndView model = new ModelAndView();
        User user = new User();
        List<Book_List> book = new ArrayList<>();
        List<Borrow> borrow = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int userId = 0;
        if (!auth.getName().equals("anonymousUser")) {
            model.addObject("userName", user.getName() + " " + user.getLastName());
        } else {
            model.addObject("userName", "0");
        }

        List<Book_Reservation> bookRes = reservation();

        List<Book_Reservation> resaNoDuplicate = new ArrayList<>();

        model.addObject("bookRes", bookRes);
        String search = "0";
        String borrowedBookByTitle = "xxx";
        String reservedBook = "__";
        String resaString = "__";
        List<Reservation> reservation = new ArrayList<>();
        String reservationTitle = "";
        for (Book_Reservation reservations : bookRes) {
            reservationTitle = reservationTitle + reservations.getTitle();
        }
        model.addObject("resaNoDuplicate", resaNoDuplicate);
        model.addObject("reservationTitle", reservationTitle);
        model.addObject("reservation", reservation);
        String borrowIdBook = idBookborrow();
        model.addObject("search", search);
        model.addObject("shortBorrow", borrow);
        model.addObject("borrowIdBook", borrowIdBook);
        model.addObject("userId", userId);
        model.addObject("booklist", book);
        model.addObject("resaString", resaString);
        model.addObject("reservedBook", reservedBook);
        model.addObject("borrow", borrow);
        model.addObject("user", user);
        model.addObject("book", book);
        model.addObject("now", Date.valueOf(LocalDate.now()));
        model.addObject("borrowedBookByTitle", borrowedBookByTitle);
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

    List<Borrow> borowReturnDate() throws ParseException {
        List<Borrow> borrow2 = new ArrayList();
        List<Borrow> borrow = Library.getallborrowedBook();
        List<Book> book = Library.getBook();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date1 = format.parse("01-01-2000");
        java.util.Date date2 = format.parse("12-08-2019");
        String lastTitle = "";

        for (Book books : book) {
            String title = books.getTitle();

            Borrow dummyBorrow = new Borrow();
            for (Borrow borrows : borrow
            ) {
                java.util.Date dummy = format.parse("01-01-2000");

                for (Book books2 : book) {
                    if (books2.getTitle().equals(title) && !lastTitle.contains(title)) {
                        for (Borrow borrowsSec : borrow
                        ) {
                            if (books2.getIdBook() == borrowsSec.getIdBook() && dummy.compareTo(borrowsSec.getReturnDate()) < 0) {

                                dummy = borrowsSec.getReturnDate();

                                dummyBorrow = borrowsSec;
                            }
                        }
                        if (!lastTitle.contains(title)) {
                            lastTitle = lastTitle + "," + title;
                            borrow2.add(dummyBorrow);
                        }
                    }
                }
            }
        }

        return borrow2;
    }

    String idBookborrow() {
        List<Borrow> borrow = Library.getallborrowedBook();
        String idBorrowedBook = "";
        for (Borrow borrows : borrow
        ) {
            idBorrowedBook = idBorrowedBook + "," + borrows.getIdBook();
        }
        return idBorrowedBook;
    }

    List<Book_Reservation> reservation() throws ParseException {
        List<Book> book = Library.getBook();
        List<Borrow> borrow = Library.getallborrowedBook();
        List<Reservation> reservation = Library.getAllReservation();
        List<Book_Reservation> bookRes = new ArrayList<>();

        for (Reservation reservations : reservation
        ) {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date dummy = format.parse("01-01-2000");
            java.util.Date dummy2 = format.parse("01-01-2000");

            int nbres = 0;
            Book_Reservation res = new Book_Reservation();
            Date now = Date.valueOf(LocalDate.now());
            LocalDate init = Instant.ofEpochMilli(now.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate next2Day = init.minus(1, ChronoUnit.YEARS);


            int nbMax = 0;
            int rank = 1;

            for (Book books : book
            ) {

                if (reservations.getTitle().equals(books.getTitle())) {
                    nbMax++;

                    for (Borrow borrows : borrow
                    ) {

                        if (borrows.getIdBook() == books.getIdBook()) {


                            if (dummy.compareTo(borrows.getReturnDate()) < 0) {
                                dummy.setTime(borrows.getReturnDate().getTime());
                                dummy.setTime(borrows.getReturnDate().getTime());
                            }
                        }
                    }
                }
            }
            //rand positionner
            for (Reservation reser : reservation
            ) {
                if (reservations.getTitle().equals(reser.getTitle())) {

                    if (reservations.getIdUser() != reser.getIdUser()) {
                        rank++;
                    } else {
                        break;
                    }
                }
            }
            //nb resa
            for (Reservation reser : reservation
            ) {
                if (reservations.getTitle().equals(reser.getTitle())) {
                    nbres++;
                }
            }


            res.setRanking(rank);
            res.setAvalaibleDate(dummy);

            res.setIdUser(reservations.getIdUser());
            res.setNbres(nbres);
            res.setNbMaxRes(nbMax * 2);
            res.setTitle(reservations.getTitle());
            bookRes.add(res);
        }

        return bookRes;
    }

    List<Book_Reservation> resaNoDuplicate() throws ParseException {
        List<Book_Reservation> bookRes = reservation();
        Book_Reservation reservationBook = new Book_Reservation();
        List<Book_Reservation> resaNoDuplicate = new ArrayList<>();
        String reservationTitle = "";
        for (Book_Reservation reservations : bookRes) {

            if (!reservationTitle.contains(reservations.getTitle())) {
                reservationBook = reservations;
                resaNoDuplicate.add(reservationBook);
            }
            reservationTitle = reservationTitle + reservations.getTitle();
        }
        return resaNoDuplicate;
    }

    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(value = "search", required = false) String search) throws ParseException {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = Library.findUserByEmail(auth.getName());
        List<Book_List> book = new ArrayList<>();

        if (search == null) {
            search = "0";
            book = Library.getAllBooks();
        } else {
            String cap = search.substring(0, 1).toUpperCase() + search.substring(1);
            String caps = search.toUpperCase();
            book = Library.searchBooks(search);
            book.addAll(Library.searchBooks(cap));
            book.addAll(Library.searchBooks(caps));
            model.addObject("book", book);
        }

        String borrowedBookByTitle = "__";
        String reservedBook = "__";
        String resaString = "_";
        List<Borrow> shortBorrow = borowReturnDate();
        int userId = 0;
        List<Reservation> reservation = Library.getAllReservation();
        List<Borrow> borrowed = Library.getallborrowedBook();
        String borrowIdBook = idBookborrow();
        if (!auth.getName().equals("anonymousUser")) {
            borrowed = Library.getborrowedBook(user.getId());

            for (Borrow borrowedBook : borrowed) {
                if (borrowedBook.getIdUser() == user.getId()) {
                    for (Book_List books : book
                    ) {
                        if (books.getIdBook() == borrowedBook.getIdBook()) {
                            borrowedBookByTitle += books.getTitle() + ",";
                        }
                    }
                }
            }

            for (Reservation reservations : reservation) {
                resaString = resaString + reservations.getTitle();
                if (reservations.getIdUser() == user.getId()) {
                    for (Book_List books : book
                    ) {

                        if (books.getIdBook() == reservations.getIdBook()) {
                            reservedBook += books.getTitle() + ",";
                        }
                    }
                }
            }
            userId = user.getId();
            model.addObject("userName", user.getName() + " " + user.getLastName());
            model.addObject("userId", userId);
        } else {
            model.addObject("userName", "0");
            model.addObject("user", userId);
        }
        List<Book_Reservation> bookRes = reservation();

        for (Reservation reservations : reservation) {
            resaString = resaString + reservations.getTitle();
        }
        String reservationTitle = "";
        Book_Reservation reservationBook = new Book_Reservation();
        List<Book_Reservation> resaNoDuplicate = new ArrayList<>();

        for (Book_Reservation reservations : bookRes) {
            if (!reservationTitle.contains(reservations.getTitle())) {
                reservationBook = reservations;
                resaNoDuplicate.add(reservationBook);
            }
            reservationTitle = reservationTitle + reservations.getTitle();

        }

        bookRes = resaNoDuplicate();
        model.addObject("resaNoDuplicate", resaNoDuplicate);
        model.addObject("reservationTitle", reservationTitle);
        model.addObject("reservation", reservation);
        model.addObject("search", search);
        model.addObject("borrowIdBook", borrowIdBook);
        model.addObject("borrow", borrowed);
        model.addObject("bookRes", bookRes);
        model.addObject("resaString", resaString);
        model.addObject("borrowedBookByTitle", borrowedBookByTitle);
        model.addObject("reservedBook", reservedBook);
        model.addObject("book", book);
        model.addObject("shortBorrow", shortBorrow);
        model.addObject("view", "home");
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = {"/search"}, method = RequestMethod.POST)
    public String search(String search) {

        return "redirect:/?search=" + search;
    }

    @RequestMapping(value = {"/returnbook"}, method = RequestMethod.POST)
    public String returnBook(@RequestParam("idborrow") int idborrow) {
        Library.returnBook(idborrow);
        return "redirect:/loggedhome";
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

        List<Book_List> book = Library.searchBooks(search);
        book.addAll(Library.searchBooks(cap));
        book.addAll(Library.searchBooks(caps));
        model.addObject("book", book);

        model.addObject("search", search);

        model.addObject("view", "home");

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
            Library.createUser(user.getLastName(), user.getName(), user.getEmail(), bCryptPasswordEncoder.encode(user.getPassword()));
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
    public ModelAndView loggedHome() throws ParseException {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = Library.findUserByEmail(auth.getName());
        List<Book> book = Library.getBook();
        List<Borrow> borrow = Library.getborrowedBook(user.getId());
        List<Book_List> booklist = Library.getAllBooks();
        String borrowedBookByTitle = "__";
        if (!auth.getName().equals("anonymousUser")) {
            model.addObject("userName", user.getName() + " " + user.getLastName());
        } else {
            model.addObject("userName", "0");
        }
        for (Borrow borrowedBook : borrow) {

            for (Book books : book
            ) {

                if (books.getIdBook() == borrowedBook.getIdBook()) {
                    borrowedBookByTitle += books.getTitle() + ",";
                }
            }
        }
        String resaString = "0";

        List<Reservation> reservation = Library.getAllReservation();

        for (Reservation reservations : reservation) {

            if (reservations.getIdUser() == user.getId()) {
                resaString = resaString + reservations.getTitle();
            }
        }
        String listBook = "";
        List<Book_List> bookListNoDuplicate = new ArrayList<>();
        for (Book_List book_lists : booklist) {

            if (!listBook.contains(book_lists.getTitle())) {
                bookListNoDuplicate.add(book_lists);
            }
            listBook = listBook + book_lists.getTitle();
        }
        List<Book_Reservation> bookRes = reservation();

        Book_Reservation reservationBook = new Book_Reservation();
        List<Book_Reservation> resaNoDuplicate = new ArrayList<>();
        String reservationTitle = "";
        for (Book_Reservation reservations : bookRes) {

            if (reservations.getIdUser() == user.getId()) {
                reservationBook = reservations;
                resaNoDuplicate.add(reservationBook);
            }
            reservationTitle = reservationTitle + reservations.getTitle();

        }
        String idBorrows = idBookborrow();
        model.addObject("borrowIdBook", idBorrows);
        model.addObject("resaString", resaString);
        model.addObject("bookRes", resaNoDuplicate);
        model.addObject("borrowedBookByTitle", borrowedBookByTitle);
        model.addObject("borrow", borrow);
        model.addObject("user", user);
        model.addObject("booklist", bookListNoDuplicate);
        model.addObject("book", booklist);
        model.addObject("now", Date.valueOf(LocalDate.now()));

        model.addObject("view", "loggedhome");

        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = {"/reserv"}, method = RequestMethod.POST)
    public String reserv(@RequestParam("idbook") int idBook) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = Library.findUserByEmail(auth.getName());
        Library.reserv(idBook, user.getId());

        return "redirect:/loggedhome";
    }

    @RequestMapping(value = {"/cancel"}, method = RequestMethod.POST)
    public String cancel(@RequestParam("title") String title) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = Library.findUserByEmail(auth.getName());
        Library.cancel(title, user.getId());

        return "redirect:/loggedhome";
    }

    @RequestMapping(value = {"/borrow"}, method = RequestMethod.POST)
    public String borrow(@RequestParam("idbook") int idBook) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = Library.findUserByEmail(auth.getName());
        List<Reservation> reservation = Library.getAllReservation();
        List<Book> books = Library.getBook();
        for (Book book : books) {
            if (book.getIdBook() == idBook) {
                for (Reservation reservations : reservation) {
                    if (reservations.getIdUser() == user.getId() && reservations.getTitle().contains(book.getTitle())) {
                        Library.deleteReservations(reservations.getIdreservation());
                    }
                }
            }
        }

        Library.borrow(idBook, user.getId());

        return "redirect:/loggedhome";
    }

    @RequestMapping(value = {"/borrowed"}, method = RequestMethod.POST)
    public String borrowed(@RequestParam("idborrow") int idBorrow) {

        Library.extendBorrow(idBorrow);
        return "redirect:/loggedhome";
    }
}
