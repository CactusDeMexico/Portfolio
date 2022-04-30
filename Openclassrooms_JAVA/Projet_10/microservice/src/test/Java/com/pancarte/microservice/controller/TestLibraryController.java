package com.pancarte.microservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pancarte.microservice.model.Book;
import com.pancarte.microservice.model.Reservation;
import com.pancarte.microservice.model.Role;
import com.pancarte.microservice.repository.BookRepository;
import com.pancarte.microservice.repository.ReservationRepository;
import com.pancarte.microservice.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestLibraryController {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    private Book book;
    private Book book2;
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    private Date date1 = format.parse("02-06-2018");
    private Date date2 = format.parse("12-08-2019");

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;


    @MockBean
    private ReservationRepository reservationRepository;

    public TestLibraryController() throws ParseException {
    }

    @Before
    public void setUp() {

        Book book = new Book(1, 1, "java", " résumer", "url", "dgd5", date1, 25, date2, date2);
        Book book2 = new Book(1, 1, "angular", " résumer", "url", "dgd5", date1, 25, date2, date2);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllBookRest() throws Exception {

        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        bookList.add(book2);
       /* ObjectMapper mapper = new ObjectMapper();
        String transactionString = mapper.writeValueAsString(bookList);
        Mockito.when(bookRepository.findAll()).thenReturn(
                Stream.of(
                        new Book(1, 1, "titre 1 ", " résumer", "url", "dgd5", date1, 25, date2, date2),
                        new Book(1, 1, "titre 5 ", " résumer", "url", "dgd5", date1, 25, date2, date2)).collect(Collectors.toList()));
        assertEquals(2, bookService.findAll().size());
        MvcResult result = mockMvc.perform(get("/getBooks").content(transactionString).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        String responseBook = result.getResponse().getContentAsString();*/
        Mockito.when(bookRepository.findAll()).thenReturn(bookList);
        MvcResult result = mockMvc.perform(get("/getBooks")).andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(bookList), result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetAllBookWithNoBookRest() throws Exception {

        List<Book> bookList = new ArrayList<>();
            Mockito.when(bookRepository.findAll()).thenReturn(bookList);
        MvcResult result = mockMvc.perform(get("/getBooks")).andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(bookList), result.getResponse().getContentAsString(), false);
    }
     @Test
    public void testGetAllReservationRest() throws Exception {

        List<Reservation> reservations = new ArrayList<>();
            Mockito.when(reservationRepository.findAllReservation()).thenReturn(reservations);
        MvcResult result = mockMvc.perform(get("/getallreser")).andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(reservations), result.getResponse().getContentAsString(), false);
    }
    @Test
    public void testGetAllReservationWithNoReservationRest() throws Exception {

        List<Reservation> reservations = new ArrayList<>();
            Mockito.when(reservationRepository.findAllReservation()).thenReturn(reservations);
        MvcResult result = mockMvc.perform(get("/getallreser")).andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(reservations), result.getResponse().getContentAsString(), false);
    }



}