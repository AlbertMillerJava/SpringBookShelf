package spring.books.booksexample.domain;

import lombok.Data;

@Data
public class OrderItem {
    private int orderItemId;
    private Book book;
    private int amount;
}
