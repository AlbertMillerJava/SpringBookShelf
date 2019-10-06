package spring.books.booksexample.repository;

import spring.books.booksexample.domain.Book;

import java.util.List;

public interface BookStorage {

    Book getBook(long id);

    List<Book> getAllBooks();

    int addBook (Book book);

}
