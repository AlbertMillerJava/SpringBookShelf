package spring.books.booksexample.facade;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spring.books.booksexample.domain.Order;
import spring.books.booksexample.repository.OrderStorage;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderFacade {
    OrderStorage orderStorage;

    public Order getOrder(int orderId) {
        return orderStorage.getOrder(orderId);
    }

    public long changeOrder(Order order) {
        return orderStorage.changeOrder(order);
    }

    public Order addOrder(Order order) {
        return orderStorage.addOrder(order);
    }

    public List<Order> getAllOrders() {
        return orderStorage.getAllOrders();
    }
}
