package javaee.books_validation.dto;

import javaee.books_validation.utilities.ISBNValidator;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Entity
@Table(name = "books")
@Getter
@Setter
@ToString
// so in collection no books with same isbn are possible
@EqualsAndHashCode(of = {"isbn"})
public class Book {
    @Column(name = "title")
    @NotEmpty
    private String title;
    @Id
    @Column(name = "isbn")
    @ISBNValidator
    private String isbn;
    @Column(name = "author")
    @NotEmpty
    private String author;
}
