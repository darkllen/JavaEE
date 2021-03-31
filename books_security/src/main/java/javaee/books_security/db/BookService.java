package javaee.books_security.db;

import javaee.books_security.dto.Book;
import javaee.books_security.utilities.FindPattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepo repo;

    /**
     *
     * @param book to create
     * @return created book
     */
    @Transactional
    public Book createBook(Book book) {
        return repo.save(book);
    }

    /**
     *
     * @param isbn of book to find
     * @return Book with isbn or null
     */
    @Transactional
    public Optional<Book> findByIsbn(String isbn) {
        return repo.findById(isbn);
    }

    /**
     * @param pattern to filter with
     * @return books suited with filter
     */
    @Transactional
    public List<Book> findBookByPattern(FindPattern pattern) {
        return repo.findAllByAuthorContainsAndIsbnContainsAndTitleContains(pattern.getFind_author(), pattern.getFind_isbn(), pattern.getFind_title());
    }

}
