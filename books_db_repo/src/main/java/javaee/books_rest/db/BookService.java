package javaee.books_rest.db;

import javaee.books_rest.dto.Book;
import javaee.books_rest.utilities.FindPattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * @return list of all books
     */
    @Transactional
    public List<Book> findAllBooks() {
        return repo.findAll();
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
     * for now method realisation is very ineffective, as it needs to find all books and then filter them
     * it will be fixed in next homework
     * @param pattern to filter with
     * @return books suited with filter
     */
    @Transactional
    public List<Book> findBookByPattern(FindPattern pattern) {
        return findAllBooks().stream().filter(pattern::filter).collect(Collectors.toList());
    }

}
