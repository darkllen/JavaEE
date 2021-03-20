package javaee.books_rest.db;

import javaee.books_rest.dto.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book, String> {
}
