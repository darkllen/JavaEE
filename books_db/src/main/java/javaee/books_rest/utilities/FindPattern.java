package javaee.books_rest.utilities;

import javaee.books_rest.dto.Book;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindPattern{
    private String find_isbn;
    private String find_author;
    private String find_title;

    /**
     * @param book to filter
     * @return true if book is valid for this patter otherwise false
     */
    public boolean filter(Book book){
        return book.getIsbn().contains(find_isbn) &&
                book.getBook_name().contains(find_title) &&
                book.getAuthor().contains(find_author);
    }
}