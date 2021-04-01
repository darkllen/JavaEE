package javaee.books_validation.controllers;

import javaee.books_validation.db.BookService;
import javaee.books_validation.db.PermissionsRepo;
import javaee.books_validation.db.UserRepo;
import javaee.books_validation.dto.PermissionEntity;
import javaee.books_validation.dto.User;
import javaee.books_validation.utilities.FindPattern;
import javaee.books_validation.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class BooksController {
    //storage for books
    @Autowired
    private BookService service;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PermissionsRepo permissionsRepo;

    /**
     *
     * @return main page
     */
    @RequestMapping(value = {"/", "all_books"}, method = RequestMethod.GET)
    public String index(){
        return "all_books";
    }


    @RequestMapping(value = { "registration"}, method = RequestMethod.GET)
    public String registration(){
        return "registration";
    }

    @RequestMapping(value = {"/register_user"}, method = RequestMethod.POST)
    public String register_user(@ModelAttribute("user") User user){
        if (userRepo.findByLogin(user.getLogin()).isEmpty()) {
            var permission = permissionsRepo.findPermissionEntityByPermission(PermissionEntity.Permission.USER);
            if (permission.isPresent()){
                user.setPermissions(List.of(permission.get()));
                userRepo.save(user);
            }
            return "redirect:/login";
        } else
        return "redirect:/registration";
    }


    @RequestMapping(value = { "favourites"}, method = RequestMethod.GET)
    public String get_favourites(Principal principal, Model model){
        Optional<User> user = userRepo.findByLogin(principal.getName());
        if (user.isPresent()){
            model.addAttribute("books", user.get().getFavourite_books());
        } else{
            model.addAttribute("books", List.of());
        }
        return "favourites";
    }


    @RequestMapping(value = "/add_to_favourite/{isbn}")
    public String add_to_favourite(Principal principal, @PathVariable String isbn){
        Optional<User> user = userRepo.findByLogin(principal.getName());
        Optional<Book> book = service.findByIsbn(isbn);
        if (book.isPresent() && user.isPresent()){
            if (!user.get().getFavourite_books().contains(book.get())) {
                user.get().getFavourite_books().add(book.get());
                userRepo.save(user.get());
            }
        }
        return "redirect:/favourites";
    }

    @RequestMapping(value = "/remove_from_favourite/{isbn}")
    public String remove_from_favourite(Principal principal, @PathVariable String isbn){
        Optional<User> user = userRepo.findByLogin(principal.getName());
        Optional<Book> book = service.findByIsbn(isbn);
        if (book.isPresent() && user.isPresent()){
            if (user.get().getFavourite_books().contains(book.get())){
                user.get().getFavourite_books().remove(book.get());
                userRepo.save(user.get());
            }
        }
        return "redirect:/favourites";
    }

    /**
     *
     * @param model will store book to show
     * @param isbn of book to show
     * @return templated page of 1 book
     */
    @RequestMapping(value = "/book/{isbn}", method = RequestMethod.GET)
    public String bookPage(Principal principal, Model model, @PathVariable String isbn){
        Optional<Book> book = service.findByIsbn(isbn);
        if (book.isPresent()){
            model.addAttribute("book", book.get());
            if (principal != null){
                Optional<User> user = userRepo.findByLogin(principal.getName());
                if (user.isPresent()) {
                    if (user.get().getFavourite_books().contains(book.get())) {
                        model.addAttribute("is_favourite", true);
                    } else {
                        model.addAttribute("is_favourite", false);
                    }
                }
            }
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
