package spring.books.booksexample.domain;

import lombok.Data;

@Data
public class Book {
    private long id;
    private String title;
    private String author;
    private Integer pageSum;
    private Integer yearOfPublish;
    private String publishingHouse;

}
