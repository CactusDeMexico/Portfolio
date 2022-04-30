package com.pancarte.microservice.Repository;

import com.pancarte.microservice.model.Borrowed;
import com.pancarte.microservice.repository.BorrowedRepository;
import com.pancarte.microservice.service.BorrowedService;
import junit.framework.Assert;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest

public class TestBorrowedRepository {
    @Autowired
    private BorrowedService borrowedService;

    @MockBean
    private BorrowedRepository borrowedRepository;
    private  Borrowed borrowed ;
    private Borrowed borrowed2;
    private Borrowed borrowed3;

    SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
    private Date date1=format. parse("02-06-2018");
    private Date date2=format. parse("12-08-2019");

    public TestBorrowedRepository() throws ParseException {
    }

    @Before
    public void setUp() {
        Borrowed borrowed =new Borrowed(1,1,2,date1,date2);
        Borrowed borrowed2 =new Borrowed(2,4,3,date1,date2);
         Borrowed borrowed3=new Borrowed();
    }
    @Test
    public void testGetAllBorrow() throws ParseException {

        List<Borrowed> borrowedList = new ArrayList<>();
        borrowedList.add(borrowed);
        borrowedList.add(borrowed2);
        Mockito.when(borrowedRepository.findAllBorrowBook()).thenReturn(
                borrowedList);
        assertEquals(2, borrowedService.findAllBorrowBook().size());
    }

    @Test
    public void testGetAllBorrowWithNoBorrows() throws ParseException {

        List<Borrowed> borrowList = new ArrayList<>();

        Mockito.when(borrowedRepository.findAllBorrowBook()).thenReturn(
                borrowList);
        assertEquals(0, borrowedService.findAllBorrowBook().size());
    }

    @Test
    public void testGetBorrowById() {
        int idBook = 1;
        List<Borrowed> borrowedList = new ArrayList<>();
        borrowedList.add(borrowed);
        Mockito.when(borrowedRepository.findFirstBorrowById(1)).thenReturn(borrowed.getIdBorrowed());
        assertEquals(1, borrowedService.findFirstBorrowById(idBook)==1);
    }

    @Test
    public void testGetBorrowByIdWrongId() {
        int idBook = 2;
        List<Borrowed> borrowedList = new ArrayList<>();
        Mockito.when(borrowedRepository.findFirstBorrowById(2)).thenReturn(null);
        assertNull(borrowedService.findFirstBorrowById(idBook));
    }
}
