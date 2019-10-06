package spring.books.booksexample.domain;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class Order {
    private  long orderId;
    private LocalDate orderDate;
    private Customer customer;
    private List<OrderItem> orderedItems;

}
