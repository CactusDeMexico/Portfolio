package com.pancarte.microservice.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pancarte.microservice.MicroserviceApplication;
import com.pancarte.microservice.controller.LibraryController;
import com.pancarte.microservice.model.Book;
import com.pancarte.microservice.repository.BookRepository;
import com.pancarte.microservice.repository.BorrowRepository;
import com.pancarte.microservice.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest

public class TestBookRepository {
/*   @Autowired
    private MockMvc mockMvc;*/

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;






    private Book book;
    private Book book2;
    private Book book3;

    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    private Date date1 = format.parse("02-06-2018");
    private Date date2 = format.parse("12-08-2019");

    public TestBookRepository() throws ParseException {
    }

    @Before
    public void setUp() {
        Book book = new Book(1,1, 1, "java", " résumer", "url", "dgd5", date1, 25, date2, date2);
        Book book2 = new Book(2,1, 1, "angular", " résumer", "url", "dgd5", date1, 25, date2, date2);
        Book book3=null;

    }

    @Test
    public void testGetAllBook() throws ParseException {

        //new Book_List(1,1,1,"titre 1 "," résumer","url","dgd5",date1,25,date2,date2,6,2),
        //                new Book_List(2,1,1,"titre 5 "," résumer","url","dgd5",date1,25,date2,date2,6,2)
        Mockito.when(bookRepository.findAll()).thenReturn(
                Stream.of(
                        new Book(1, 1, "java ", " résumer", "url", "dgd5", date1, 25, date2, date2),
                        new Book(1, 1, "titre 5 ", " résumer", "url", "dgd5", date1, 25, date2, date2)).collect(Collectors.toList()));
        assertEquals(2, bookService.findAll().size());
    }
    @Test
    public void testGetAllBookWithNoBook() throws ParseException {

        List<Book> bookList = new ArrayList<>();
        Mockito.when(bookRepository.findAll()).thenReturn(
                bookList);
        assertEquals(0, bookService.findAll().size());
    }


    @Test
    public void testGetBookByTitle() {
        String title = "java";
        Mockito.when(bookRepository.findByTitle(title)).thenReturn(
                Stream.of(new Book(1, 1, "java", " résumer", "url", "dgd5", date1, 25, date2, date2)).collect(Collectors.toList()));
        assertEquals(1, bookService.findByTitle(title).size());
    }
    @Test
    public void testGetBookByTitleWithNoBook() {
        String title = "java";
        List<Book> bookList = new ArrayList<>();
        Mockito.when(bookRepository.findByTitle(title)).thenReturn(
                bookList);
        assertEquals(0, bookService.findByTitle(title).size());
    }
    @Test
    public void testGetBookByTitleWithWrongTitle() {

        String title = "angular";
        List<Book> bookList = new ArrayList<>();


        Mockito.when(bookRepository.findByTitle(title)).thenReturn(
                bookList);
        assertEquals(0, bookService.findByTitle(title).size());
    }
    @Test
    public void testGetBookById() {
       int id = 1;

        Mockito.when(bookRepository.findById(id)).thenReturn(book);
        assertEquals(book, bookService.findById(id));
    } @Test
    public void testGetBookByIdWrongId() {
       int id = 1;
        Mockito.when(bookRepository.findById(id)).thenReturn(null);
        assertNull(bookService.findById(id));
    }
}

