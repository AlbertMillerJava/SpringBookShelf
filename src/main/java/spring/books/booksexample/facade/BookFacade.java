package spring.books.booksexample.facade;

import org.springframework.stereotype.Service;
import spring.books.booksexample.repository.BookStorage;
import spring.books.booksexample.domain.Book;

import java.util.List;

@Service
public class BookFacade {

    private BookStorage bookStorage;

    public BookFacade(BookStorage bookStorage) {
        this.bookStorage = bookStorage;
    }

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
