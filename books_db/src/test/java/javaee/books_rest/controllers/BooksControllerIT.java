package javaee.books_rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import javaee.books_rest.dto.Book;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import java.io.*;
import java.util.List;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BooksControllerIT {

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    void setPort(int port) {
        RestAssured.port = port;
    }

    @Test
    /*
      get books with such filter, that there is valid book for it
     */
    void get_books_right_filer() throws Exception {
        InputStream stream = BooksControllerIT.class.getResourceAsStream("/request_add.json");
        final Book book= objectMapper.readValue(stream, Book.class);

        final String expected = objectMapper.writeValueAsString(List.of(book));

        RestAssured
                .given()
                .body(BooksControllerIT.class.getResourceAsStream("/request_get_exist.json"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/get_books")
                .then()
                .statusCode(200)
                .body(CoreMatchers.is(expected));
    }

    @Test
    /*
      get books with such filter, that there is no valid book for it
     */
    void get_books_wrong_filer() throws Exception {
        final String expected = objectMapper.writeValueAsString(List.of());
        RestAssured
                .given()
                .body(BooksControllerIT.class.getResourceAsStream("/request_get_absent.json"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/get_books")
                .then()
                .statusCode(200)
                .body(CoreMatchers.is(expected));
    }

    @Test
    /*
      add book to empty repository
     */
    void add_one_book() throws IOException {
        InputStream stream = BooksControllerIT.class.getResourceAsStream("/request_add.json");
        final Book book= objectMapper.readValue(stream, Book.class);

        RestAssured
                .given()
                .body(BooksControllerIT.class.getResourceAsStream("/request_add.json"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/add_book")
                .then()
                .statusCode(201)
                .body("book_name", CoreMatchers.is(book.getBook_name()))
                .body("isbn", CoreMatchers.is(book.getIsbn()))
                .body("author", CoreMatchers.is(book.getAuthor()));
    }

    @Test
    /*
      add book, that already exists in repository
     */
    void add_the_same_book_again() throws Exception {
        InputStream stream = BooksControllerIT.class.getResourceAsStream("/request_add.json");
        final Book book= objectMapper.readValue(stream, Book.class);

        RestAssured
                .given()
                .body(BooksControllerIT.class.getResourceAsStream("/request_add.json"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/add_book")
                .then()
                .statusCode(200)
                .body("book_name", CoreMatchers.is(book.getBook_name()))
                .body("isbn", CoreMatchers.is(book.getIsbn()))
                .body("author", CoreMatchers.is(book.getAuthor()));
    }
}