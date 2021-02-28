package javaee.books_rest;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Set;

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
    public String add_book(@ModelAttribute("book") Book book){
        if (book.isValid()) books_storage.add(book);
        return "redirect:/all_books";
    }

    @RequiredArgsConstructor
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
}
