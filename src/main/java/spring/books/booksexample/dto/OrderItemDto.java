package spring.books.booksexample.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.books.booksexample.domain.Book;
import spring.books.booksexample.domain.Order;
import spring.books.booksexample.domain.OrderItem;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    int bookId;
    int amount;

    public OrderItem createOrderItem(){
        OrderItem orderItem = new OrderItem();
        orderItem.setAmount(amount);
        Book book = new Book();
        book.setId(bookId);
        orderItem.setBook(book);
        return  orderItem;
    }
}
