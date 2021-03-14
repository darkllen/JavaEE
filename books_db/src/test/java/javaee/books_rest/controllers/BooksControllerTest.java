package javaee.books_rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javaee.books_rest.dto.Book;
import javaee.books_rest.utilities.FindPattern;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@AutoConfigureMockMvc
@SpringBootTest
//@WebMvcTest(BooksController.class)
class BooksControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final Book book = new Book("title", "isbn", "author");

    @Test
    /*
      get books with such filter, that there is valid book for it
     */
    void get_books_right_filer() throws Exception {
        FindPattern pattern = new FindPattern();
        pattern.setFind_isbn("i");
        pattern.setFind_author("");
        pattern.setFind_title("");
        final String jsonRequest = objectMapper.writeValueAsString(pattern);
        final String expectedResponse = objectMapper.writeValueAsString(List.of(book));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/get_books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        )
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
    }

    @Test
    /*
      get books with such filter, that there is no valid book for it
     */
    void get_books_wrong_filer() throws Exception {
        FindPattern pattern = new FindPattern();
        pattern.setFind_isbn("f");
        pattern.setFind_author("");
        pattern.setFind_title("");
        final String jsonRequest = objectMapper.writeValueAsString(pattern);
        final String expectedResponse = objectMapper.writeValueAsString(List.of());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/get_books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        )
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));
    }

    @Test
    /*
      add book to empty repository
     */
    void add_one_book() throws Exception {
        final String jsonRequest = objectMapper.writeValueAsString(book);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/add_book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(jsonRequest));
    }

    @Test
    /*
      add book, that already exists in repository
     */
    void add_the_same_book_again() throws Exception {
        final String jsonRequest = objectMapper.writeValueAsString(book);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/add_book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonRequest));
    }
    }



