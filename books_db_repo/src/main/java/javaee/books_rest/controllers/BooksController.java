package javaee.books_rest.controllers;

import javaee.books_rest.db.BookService;
import javaee.books_rest.utilities.FindPattern;
import javaee.books_rest.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class BooksController {
    //storage for books
    @Autowired
    private BookService service;

    /**
     *
     * @return main page
     */
    @RequestMapping(value = {"/", "all_books"}, method = RequestMethod.GET)
    public String index(){
        return "all_books";
    }

    /**
     *
     * @param model will store book to show
     * @param isbn of book to show
     * @return templated page of 1 book
     */
    @RequestMapping(value = "/book/{isbn}", method = RequestMethod.GET)
    public String bookPage(Model model, @PathVariable String isbn){
        Optional<Book> book = service.findByIsbn(isbn);
        if (book.isPresent()){
            model.addAttribute("book", book.get());
            return "book";
        } else {
            model.addAttribute("error", "No book with such isbn");
            return "error";
        }
    }

    /**
     *
     * @param find books by pattern
     * @return List of filtered books
     */
    @ResponseBody
    @RequestMapping(value = {"/get_books"}, method = RequestMethod.POST)
    public List<Book> get_books(@RequestBody final FindPattern find){
        return service.findBookByPattern(find);
    }

    /**
     * add book to collection if it is valid
     * @param book from form to add it to collection
     * @return redirect to start page
     */
    @RequestMapping(value = {"/add_book"}, method = RequestMethod.POST)
    public ResponseEntity<Book> add_book(@RequestBody final Book book){
        if (book.isValid() && service.findByIsbn(book.getIsbn()).isEmpty()) {
            service.createBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(book);
        } else  return ResponseEntity.status(HttpStatus.OK).body(book);
    }
}
