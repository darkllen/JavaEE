package javaee.books_rest.utilities;

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
}