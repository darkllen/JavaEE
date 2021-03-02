package javaee.books_rest.dto;

import lombok.*;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NotNull
// so in collection no books with same isbn are possible
@EqualsAndHashCode(of = {"isbn"})
public class Book {
    private String book_name;
    private String isbn;
    private String author;

    /**
     *
     * @return true if there is no empty fields in book, otherwise false
     */
    public boolean isValid(){
        return !book_name.equals("") & !isbn.equals("") & !author.equals("");
    }
}
