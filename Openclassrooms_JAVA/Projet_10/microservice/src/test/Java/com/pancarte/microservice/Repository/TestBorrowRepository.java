package com.pancarte.microservice.Repository;

import com.pancarte.microservice.MicroserviceApplication;
import com.pancarte.microservice.model.Borrow;

import com.pancarte.microservice.repository.BorrowRepository;

import com.pancarte.microservice.service.BookService;
import com.pancarte.microservice.service.BorrowService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestBorrowRepository {
    @Autowired
    private BorrowService borrowService;

    @MockBean
    BorrowRepository borrowRepository;

    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    private Date date1 = format.parse("02-06-2018");
    private Date date2 = format.parse("12-08-2019");
    private Borrow borrow;
    private Borrow borrow2;

    @Before
    public void setUp() {
        Borrow borrow = new Borrow(1, 1, 2, true, date1, date2, false);
        Borrow borrow2 = new Borrow(2, 4, 3, true, date1, date2, false);
    }

    public TestBorrowRepository() throws ParseException {
    }

    @Test
    public void testGetAllBorrow() throws ParseException {

        List<Borrow> borrowList = new ArrayList<>();
        borrowList.add(borrow);
        borrowList.add(borrow2);
        Mockito.when(borrowRepository.findAllBorrowBook()).thenReturn(
                borrowList);
        assertEquals(2, borrowService.findAllBorrowBook().size());
    }

    @Test
    public void testGetAllBorrowWithNoBorrows() throws ParseException {

        List<Borrow> borrowList = new ArrayList<>();

        Mockito.when(borrowRepository.findAllBorrowBook()).thenReturn(
                borrowList);
        assertEquals(0, borrowService.findAllBorrowBook().size());
    }

    @Test
    public void testGetBorrowByIdUser() {
        int idUser = 2;
        List<Borrow> borrowList = new ArrayList<>();
        borrowList.add(borrow);
        Mockito.when(borrowRepository.findBorrowedBookByIUser(2)).thenReturn(borrowList);
        assertEquals(1, borrowService.findBorrowedBookByIUser(idUser).size());
    }

    @Test
    public void testGetBorrowByIdUserWrongId() {
        int idUser = 2;
        List<Borrow> borrow = new ArrayList<>();
        Mockito.when(borrowRepository.findBorrowedBookByIUser(2)).thenReturn(borrow);
        assertEquals(0, borrowService.findBorrowedBookByIUser(idUser).size());
    }
}
