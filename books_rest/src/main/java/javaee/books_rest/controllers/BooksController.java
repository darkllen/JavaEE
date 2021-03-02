package javaee.books_rest.controllers;

import javaee.books_rest.utilities.FindPattern;
import javaee.books_rest.dto.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class BooksController {
    // Set is used as collection with purpose have no duplicates
    Set<Book> books_storage = new HashSet<>();

    /**
     * main page with table of all books
     * @param model
     * @return template with books list
     */
    @RequestMapping(value = {"/", "all_books"}, method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("books", books_storage);
        return "all_books";
    }

    @ResponseBody
    @RequestMapping(value = {"/get_books"}, method = RequestMethod.POST)
    public List<Book> get_books(@RequestBody final FindPattern find){
        return books_storage.stream().filter(find::filter).collect(Collectors.toList());
    }

    /**
     * just return html page
     * @return page with form to add book
     */
    @RequestMapping(value = {"/add_book_form"}, method = RequestMethod.GET)
    public String redirect_to_form(){
        return "add_book";
    }

    /**
     * add book to collection if it is valid
     * @param book from form to add it to collection
     * @return redirect to start page
     */
    @RequestMapping(value = {"/add_book"}, method = RequestMethod.POST)
    public ResponseEntity<Book> add_book(@RequestBody final Book book){
        if (book.isValid() && !books_storage.contains(book)) {
            books_storage.add(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(book);
        } else  return ResponseEntity.status(HttpStatus.OK).body(book);
    }
}
