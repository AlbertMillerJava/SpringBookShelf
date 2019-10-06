package spring.books.booksexample.repository;

import spring.books.booksexample.domain.Order;

import java.util.List;

public interface OrderStorage {
    Order getOrder(int orderId);

    long changeOrder (Order order);

    long addOrder(Order order);

    List<Order> getAllOrders();
}
