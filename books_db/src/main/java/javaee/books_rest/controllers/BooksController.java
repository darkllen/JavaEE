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
import java.util.stream.Collectors;

@Controller
//for now Rest controller and Web controller are in the same class
//It will bi fixed when DB appear in project
public class BooksController {
    @Autowired
    private BookService service;

    @RequestMapping(value = {"/", "all_books"}, method = RequestMethod.GET)
    public String index(){
        return "all_books";
    }


    @RequestMapping(value = "/book/{isbn}", method = RequestMethod.GET)
    public String bookPage(Model model, @PathVariable String isbn){
        Book book = service.findByIsbn(isbn);
        if (book!=null){
            model.addAttribute("book", book);
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
        return service.findAllBooks().stream().filter(find::filter).collect(Collectors.toList());
    }

    /**
     * add book to collection if it is valid
     * @param book from form to add it to collection
     * @return redirect to start page
     */
    @RequestMapping(value = {"/add_book"}, method = RequestMethod.POST)
    public ResponseEntity<Book> add_book(@RequestBody final Book book){
        if (book.isValid() && service.findByIsbn(book.getIsbn())==null) {
            service.createBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(book);
        } else  return ResponseEntity.status(HttpStatus.OK).body(book);
    }
}
