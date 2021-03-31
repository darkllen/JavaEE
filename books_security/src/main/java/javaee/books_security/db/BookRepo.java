package javaee.books_security.db;

import javaee.books_security.dto.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepo extends JpaRepository<Book, String> {
    List<Book> findAllByAuthorContainsAndIsbnContainsAndTitleContains(String author, String isbn, String title);
}
