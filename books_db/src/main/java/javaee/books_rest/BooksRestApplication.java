package javaee.books_rest;

import javaee.books_rest.db.BookService;
import javaee.books_rest.dto.Book;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BooksRestApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(BooksRestApplication.class, args);
    }

}
