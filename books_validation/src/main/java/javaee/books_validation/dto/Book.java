package javaee.books_validation.dto;

import javaee.books_validation.utilities.ISBNValidator;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "books")
@Getter
@Setter
@ToString
// so in collection no books with same isbn are possible
@EqualsAndHashCode(of = {"isbn"})
public class Book {
    @Column(name = "title")
    @NotEmpty(message = "title shouldn't be empty")
    private String title;
    @Id
    @Column(name = "isbn")
    @ISBNValidator
    private String isbn;
    @Column(name = "author")
    @NotEmpty(message = "author shouldn't be empty")
    private String author;
}
