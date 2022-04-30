package com.pancarte.microservice.controller;

import com.pancarte.microservice.model.*;

import com.pancarte.microservice.repository.*;
import com.pancarte.microservice.service.BookService;

import com.pancarte.microservice.service.BorrowedService;
import com.pancarte.microservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.web.bind.annotation.*;

import java.sql.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.util.List;

@RestController
public class LibraryController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final BorrowRepository borrowRepository;
    private final BorrowedRepository borrowedRepository;
    private final BorrowedService borrowedService;
    private final ReservationRepository reservationRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public LibraryController(UserService userService, BookService bookService, @Qualifier("borrowRepository") BorrowRepository borrowRepository, @Qualifier("bookRepository") BookRepository bookRepository, @Qualifier("borrowedRepository") BorrowedRepository borrowedRepository, BorrowedService borrowedService, @Qualifier("roleRepository") RoleRepository roleRepository, @Qualifier("userRepository") UserRepository userRepository, @Qualifier("reservationRepository") ReservationRepository reservationRepository, JavaMailSender javaMailSender) {
        this.borrowedRepository = borrowedRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.bookService = bookService;
        this.borrowRepository = borrowRepository;
        this.roleRepository = roleRepository;
        this.bookRepository = bookRepository;
        this.borrowedService = borrowedService;

        this.reservationRepository = reservationRepository;
        this.javaMailSender = javaMailSender;
    }

    @RequestMapping(value = {"/user"}, method = RequestMethod.GET)
    public String queryUser(@RequestParam("email") String email) {
        return userRepository.queryUser(email);
    }

    private final JavaMailSender javaMailSender;

    @Scheduled(cron = "* * * * 10  ?")
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
                msg.setText("Vous deviez rendre le livre le :" + book.getTitle() + " le " + date
                        + "\n Nous vous prions de retourner le livre "
                );

                System.out.println("email sended");
                javaMailSender.send(msg);
            }
        }
    }

    public List<Book_List> getBooks() {

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

    public List<Book_Reservation> reservation() {

        List<Book_List> book = getBooks();
        List<Borrow> borrow = getallborrowedBook();
        List<Reservation> reservation = getAllReservation();
        List<Book_Reservation> bookRes = new ArrayList<>();
        for (Reservation reservations : reservation
        ) {
            int nbres = 0;
            Book_Reservation res = new Book_Reservation();
            Date dummy = new Date(1999 - 01 - 01);

            int nbMax = 0;
            int rank = 1;

            for (Book_List books : book
            ) {
                if (reservations.getTitle().equals(books.getTitle())) {
                    nbMax++;
                    for (Borrow borrows : borrow
                    ) {
                        if (borrows.getIdBook() == books.getIdBook()) {
                            if (dummy.compareTo(borrows.getReturnDate()) < 0) {
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
                    nbres++;
                    if (reservations.getIdUser() != reser.getIdUser()) {
                        rank++;
                    } else {
                        break;
                    }
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

    @Scheduled(cron = "0 0 0 * *  ?")
    public void checkReservation() {

        List<Book_Reservation> reservation = reservation();
        List<Book_List> book = getAllBooks();
        List<Borrow> borrow = getallborrowedBook();
        LocalDate localDate = LocalDate.now();
        Date now = java.sql.Date.valueOf(localDate);
        for (Book_Reservation reservations : reservation
        ) {
            for (Book_List books : book
            ) {
                boolean bookAvailable = true;
                for (Borrow borrows : borrow) {
                    if (borrows.getIdBook() == books.getIdBook()) {
                        bookAvailable = false;
                    }
                }
                Reservation reserv = reservationRepository.findReservation(reservations.getIdUser(), reservations.getTitle());


                if (reservations.getRanking() == 1 && reservations.getTitle().equals(books.getTitle()) && bookAvailable && !reserv.isEmailSended()) {
//
                    int borrowedBook = borrowedService.findFirstBorrowById(books.getIdBook());

                    Borrowed borrowed = borrowedRepository.findBorrowedBook(borrowedBook);
                    LocalDate returnDate = Instant.ofEpochMilli(now.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate next2Day = returnDate.plus(2, ChronoUnit.DAYS);


                    SimpleMailMessage msg = new SimpleMailMessage();
                    User user = userRepository.findById(reservations.getIdUser());
                    msg.setTo(user.getEmail());
                    msg.setSubject("Livre disponlible");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");


                    String date = dateFormat.format(borrowed.getReturnDate());
                    msg.setText("Le livre :" + books.getTitle() + " est disponible jusqu'au " + next2Day

                    );

                    System.out.println("email sended");
                    javaMailSender.send(msg);
                    reserv.setEmailSended(true);
                    reserv.setLastDate(Date.from(next2Day.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    reservationRepository.save(reserv);
                }

                if (reservations.getTitle().equals(books.getTitle()) && books.getNbCopyAvailable() > 0 && reserv.isEmailSended() && reserv.getLastDate().compareTo(now) > 0) {
                    SimpleMailMessage msg = new SimpleMailMessage();
                    User user = userRepository.findById(reservations.getIdUser());
                    msg.setTo(user.getEmail());
                    msg.setSubject("Reservation annulé");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

                    msg.setText("La réservatio, pour le livre :" + books.getTitle() + " est annulée"
                    );
                    System.out.println("email sended");
                    javaMailSender.send(msg);
                    Reservation resa = reservationRepository.findReservation(reservations.getIdUser(), reservations.getTitle());
                    reservationRepository.delete(resa);
                }
            }
        }
    }

    @RequestMapping(value = {"/returnbook"}, method = RequestMethod.GET)
    public void returnBook(@RequestParam("idborrow") int idborrow) {

        Borrow borrow = borrowRepository.findBorrowedBook(idborrow);
        Borrowed borrowed = new Borrowed();
        Date now = Date.valueOf(LocalDate.now());
        borrowed.setCreationDate(borrow.getCreationDate());
        borrowed.setIdUser(borrow.getIdUser());
        borrowed.setIdBook(borrow.getIdBook());
        borrowed.setReturnDate(now);
        borrowedRepository.save(borrowed);
        borrowRepository.delete(borrow);

        checkReservation();
    }

    @RequestMapping(value = {"/role"}, method = RequestMethod.GET)
    public String queryRole(@RequestParam("email") String email) {

        return roleRepository.queryRole((email));
    }

    @RequestMapping(value = {"/deleteresa"}, method = RequestMethod.GET)
    public void deleteReservations(@RequestParam("idresa") int idresa) {

        Reservation resa = reservationRepository.findReservationById(idresa);
        reservationRepository.delete(resa);
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

        userService.saveUser(user);
    }

    @RequestMapping(value = {"/getresbytitle"}, method = RequestMethod.GET)
    public List<Reservation> getAllReservations(@RequestParam("title") String title) {
        return reservationRepository.findAllReservationByBook(title);
    }

    @RequestMapping(value = {"/cancel"})
    public void cancel(@RequestParam("title") String title, @RequestParam("iduser") int idUser) {
        Reservation reservation = reservationRepository.findReservation(idUser, title);
        reservationRepository.delete(reservation);
    }

    @RequestMapping(value = {"/reserv"})
    public void reserv(@RequestParam("idbook") int idBook, @RequestParam("iduser") int idUser) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date dummy = format.parse("01-01-2000");
        Reservation reservation = new Reservation();
        reservation.setIdBook(idBook);
        reservation.setIdUser(idUser);
        Book book = bookService.findById(idBook);
        reservation.setTitle(book.getTitle());
        reservation.setEmailSended(false);
        reservation.setLastDate(dummy);
        reservationRepository.save(reservation);
    }

    @RequestMapping(value = {"/getallreser"}, method = RequestMethod.GET)
    public List<Reservation> getAllReservation() {
        return reservationRepository.findAllReservation();
    }

    @RequestMapping(value = {"/getallborrow"}, method = RequestMethod.GET)
    public List<Borrow> getallborrowedBook() {
        return borrowRepository.findAllBorrowBook();
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
        List<Book_Reservation> reservation = reservation();

        int nbCopy = 0;
        int nbBorrow = 0;
        int contain = 0;
        int reservedBook = 0;
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
                for (Book_Reservation resa : reservation
                ) {
                    if (resa.getTitle().equals(bookList.getTitle())) {
                        reservedBook = resa.getNbres();
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

        List<Book_List> listBook = getBooks();


        return listBook;
    }

    @RequestMapping(value = {"/getBooks"})
    public List<Book> getBook() {
        List<Book> listBook = bookRepository.findAll();
        return listBook;
    }
}
