package spring.books.booksexample.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.books.booksexample.facade.BookFacade;
import spring.books.booksexample.domain.Book;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

    private BookFacade bookFacade;

    @GetMapping("{id}")
    public Book getBook(@PathVariable("id") long id){
        return bookFacade.getBook(id);
    }
    @GetMapping
    public List<Book> getAllBooks(){
        return  bookFacade.getAllBooks();
    }
    @PostMapping("/add")
    public int addBook(@RequestBody Book book){
        return bookFacade.addBook(book);
    }
}
