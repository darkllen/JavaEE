package javaee.books_rest.db;

import javaee.books_rest.dto.Book;
import javaee.books_rest.utilities.FindPattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final EntityManager entityManager;

    @Transactional
    public Book createBook(Book book) {
        return entityManager.merge(book);
    }
    @Transactional
    public List<Book> findAllBooks() {
        return entityManager.createQuery("SELECT u FROM Book u", Book.class)
                .getResultList();
    }

    @Transactional
    public Book findByIsbn(String isbn) {
        return entityManager.find(Book.class, isbn);
    }



//    @Transactional
//    public List<Book> findBookByPattern(FindPattern pattern) {
//        return entityManager.createQuery("SELECT u FROM Book u WHERE u.isbn LIKE :query OR u.lastName LIKE :query", UserEntity.class)
//                .setParameter("query", '%' + searchText + '%')
//                .getResultList();
//    }

}
