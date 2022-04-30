package com.pancarte.microservice.controller;

import com.pancarte.microservice.model.*;

import com.pancarte.microservice.repository.BorrowRepository;
import com.pancarte.microservice.repository.RoleRepository;
import com.pancarte.microservice.repository.UserRepository;
import com.pancarte.microservice.service.BookService;
import com.pancarte.microservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.sql.Date;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.util.List;

@RestController
public class LibraryControleur {
    private final UserService userService;
    private final UserRepository userRepository;
    private final BookService bookService;
    private final BorrowRepository borrowRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public LibraryControleur(UserService userService, BookService bookService, @Qualifier("borrowRepository") BorrowRepository borrowRepository, @Qualifier("roleRepository") RoleRepository roleRepository, @Qualifier("userRepository") UserRepository userRepository, JavaMailSender javaMailSender) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.bookService = bookService;
        this.borrowRepository = borrowRepository;
        this.roleRepository = roleRepository;
        this.javaMailSender = javaMailSender;
    }

    @RequestMapping(value = {"/user"}, method = RequestMethod.GET)
    public String queryUser(@RequestParam("email") String email) {
        return userRepository.queryUser(email);
    }

    private final JavaMailSender javaMailSender;

   @Scheduled(cron = "0-10 * * * *  ?")
    public void sendEmail() {
        List<Borrow> borrowed = borrowRepository.findAllBorrowBook();
        for (Borrow borrowedBook : borrowed) {
            if (borrowedBook.getReturnDate().compareTo((java.sql.Date.valueOf(LocalDate.now()))) > 0) {
                User user = userRepository.findById(borrowedBook.getIdUser());
                SimpleMailMessage msg = new SimpleMailMessage();
                Book book = bookService.findById(borrowedBook.getIdBook());
                msg.setTo(user.getEmail());
                msg.setSubject("Livre à rendre");
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                String date = dateFormat.format(borrowedBook.getReturnDate());
                msg.setText("Vous deviez rendre le livre le :" +book.getTitle()+" le "+ date
                        + "\n Nous vous prions de retourner le livre "
                       );

                System.out.println("email sended");
                javaMailSender.send(msg);
            }
        }


/*
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("marj12@live.fr");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");
        System.out.println(msg);

        javaMailSender.send(msg);
*/
    }

    @RequestMapping(value = {"/role"}, method = RequestMethod.GET)
    public String queryRole(@RequestParam("email") String email) {

        return roleRepository.queryRole((email));
    }

    @RequestMapping(value = {"/find"}, method = RequestMethod.GET)
    public User findByMail(@RequestParam("email") String email) {

        return userService.findUserByEmail(email);
    }

    @RequestMapping(value = {"/createUser"}, method = RequestMethod.GET)
    public void createUser(@RequestParam("lastName") String lastName, @RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password) {
        User user = new User();
        user.setLastName(lastName);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        System.out.println("TEST" + user.getLastName() + user.getName() + user.getEmail() + user.getPassword());
        userService.saveUser(user);
    }

    @RequestMapping(value = {"/getborrow"}, method = RequestMethod.GET)
    public List<Borrow> getborrowedBook(@RequestParam("idUser") int idUser) {
        return borrowRepository.findBorrowedBookByIUser(idUser);
    }

    @RequestMapping(value = {"/borrow"})
    public void borrow(@RequestParam("idBook") int idBook, @RequestParam("idUser") int idUser) {
        Date now = Date.valueOf(LocalDate.now());
        Borrow borrow = new Borrow();

        borrow.setIdBook(idBook);
        borrow.setIdUser(idUser);
        borrow.setLoan(true);
        borrow.setCreationDate(now);
        LocalDate today = LocalDate.now();

        //add 4 week to the current date
        LocalDate next4Week = today.plus(4, ChronoUnit.WEEKS);

        borrow.setReturnDate(java.sql.Date.valueOf(next4Week));
        borrow.setExtended(false);
        borrowRepository.save(borrow);
    }

    @RequestMapping(value = {"/extendBorrow"}, method = RequestMethod.GET)
    public void extendBorrow(@RequestParam("idBorrow") int idBorrow) {

        Borrow borrowed = borrowRepository.findBorrowedBook(idBorrow);

        LocalDate creationDate = borrowed.getReturnDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate next4Week = creationDate.plus(4, ChronoUnit.WEEKS);
        borrowed.setReturnDate(java.sql.Date.valueOf(next4Week));
        borrowed.setExtended(true);
        borrowRepository.save(borrowed);
    }

    @RequestMapping(value = {"/search"})
    public List<Book_List> searchIt(@RequestParam("search") String search) {

        List<Book> books = bookService.findByTitle(search);
        List<Book_List> listBook = new ArrayList<>();
        List<Borrow> borrowedBook = borrowRepository.findAllBorrowBook();

        int nbCopy = 0;
        int nbBorrow = 0;
        int contain = 0;
        for (Book bookList : books) {
            nbBorrow = nbCopy = 0;
            for (Book allbooks : books) {

                if (bookList.getTitle().equals(allbooks.getTitle())) {
                    nbCopy++;

                    for (Borrow bookBorrowed : borrowedBook) {
                        if ((allbooks.getIdBook() == bookBorrowed.getIdBook()) && (bookBorrowed.isLoan())) {
                            nbBorrow++;
                        }
                    }
                }
                if (books.indexOf(allbooks) == books.size() - 1) {
                    Book_List listedBook = new Book_List();
                    contain = 0;
                    listedBook.setIdBook(bookList.getIdBook());
                    listedBook.setIdEditeur(bookList.getIdEditeur());
                    listedBook.setIdType(bookList.getIdType());
                    listedBook.setTitle(bookList.getTitle());
                    listedBook.setSummary(bookList.getSummary());
                    listedBook.setUrlImage(bookList.getUrlImage());
                    listedBook.setIsbn(bookList.getIsbn());
                    listedBook.setPurchaseDate(bookList.getPurchaseDate());
                    listedBook.setPrice(bookList.getPrice());
                    listedBook.setUpdateDate(bookList.getUpdateDate());
                    listedBook.setCreationDate(bookList.getCreationDate());
                    listedBook.setNbCopy(nbCopy);
                    listedBook.setNbCopyAvailable(nbCopy - nbBorrow);

                    for (Book_List boo : listBook) {
                        if (boo.getTitle().equals(listedBook.getTitle())) {
                            contain = 1;
                        }
                    }
                    if (contain == 0) {
                        listBook.add(listedBook);
                    }
                }
            }
        }

        return listBook;
    }

    @RequestMapping(value = {"/getAllBooks"})
    public List<Book_List> getAllBooks() {

        List<Book> books = bookService.findAll();
        List<Book_List> listBook = new ArrayList<>();
        List<Borrow> borrowedBook = borrowRepository.findAllBorrowBook();
        int nbCopy = 0;
        int nbBorrow = 0;
        int contain = 0;
        for (Book bookList : books) {
            nbBorrow = nbCopy = 0;
            for (Book allbooks : books) {

                if (bookList.getTitle().equals(allbooks.getTitle())) {
                    nbCopy++;

                    for (Borrow bookBorrowed : borrowedBook) {
                        if ((allbooks.getIdBook() == bookBorrowed.getIdBook()) && (bookBorrowed.isLoan())) {
                            nbBorrow++;
                        }
                    }
                }
                if (books.indexOf(allbooks) == books.size() - 1) {
                    Book_List listedBook = new Book_List();
                    contain = 0;
                    listedBook.setIdBook(bookList.getIdBook());
                    listedBook.setIdEditeur(bookList.getIdEditeur());
                    listedBook.setIdType(bookList.getIdType());
                    listedBook.setTitle(bookList.getTitle());
                    listedBook.setSummary(bookList.getSummary());
                    listedBook.setUrlImage(bookList.getUrlImage());
                    listedBook.setIsbn(bookList.getIsbn());
                    listedBook.setPurchaseDate(bookList.getPurchaseDate());
                    listedBook.setPrice(bookList.getPrice());
                    listedBook.setUpdateDate(bookList.getUpdateDate());
                    listedBook.setCreationDate(bookList.getCreationDate());
                    listedBook.setNbCopy(nbCopy);
                    listedBook.setNbCopyAvailable(nbCopy - nbBorrow);
                    System.out.println(nbBorrow + " hhhhhh");
                    System.out.println(nbCopy + "nnnn");

                    for (Book_List boo : listBook) {
                        if (boo.getTitle().equals(listedBook.getTitle())) {
                            contain = 1;
                        }
                    }
                    if (contain == 0) {
                        listBook.add(listedBook);
                    }
                }
            }
        }

        return listBook;
    }
}
