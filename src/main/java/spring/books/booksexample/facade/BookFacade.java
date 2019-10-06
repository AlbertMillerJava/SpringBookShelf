package spring.books.booksexample.facade;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spring.books.booksexample.repository.BookStorage;
import spring.books.booksexample.domain.Book;

import java.util.List;

@Service
@AllArgsConstructor
public class BookFacade {

    private BookStorage bookStorage;

    public Book getBook(long id){
        return bookStorage.getBook(id);
    }

    public List<Book> getAllBooks(){
        return bookStorage.getAllBooks();
    }

    public int addBook (Book book){
        return bookStorage.addBook(book);
    }
}
