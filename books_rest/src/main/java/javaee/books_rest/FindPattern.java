package javaee.books_rest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindPattern{
    private String find;

    public boolean filter(Book book){
        return book.getIsbn().contains(find) || book.getBook_name().contains(find);
    }
}