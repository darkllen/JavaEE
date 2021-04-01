package javaee.books_validation.dto;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


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
    @NotEmpty(message = "Login can't be empty")
    private String title;
    @Id
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "author")
    @NotEmpty(message = "Login can't be empty")
    private String author;

    /**
     *
     * @return true if there is no empty fields in book, otherwise false
     */
    public boolean isValid(){
        return !title.equals("") & !isbn.equals("") & !author.equals("");
    }
}
