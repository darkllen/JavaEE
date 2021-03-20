package javaee.books_rest.dto;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@NotNull
// so in collection no books with same isbn are possible
@EqualsAndHashCode(of = {"isbn"})
public class Book {
    @Column(name = "title")
    private String title;
    @Id
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "author")
    private String author;

    /**
     *
     * @return true if there is no empty fields in book, otherwise false
     */
    public boolean isValid(){
        return !title.equals("") & !isbn.equals("") & !author.equals("");
    }
}
