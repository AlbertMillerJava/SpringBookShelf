package spring.books.booksexample.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import spring.books.booksexample.domain.Order;
import spring.books.booksexample.dto.OrderDto;
import spring.books.booksexample.facade.OrderFacade;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    OrderFacade orderFacade;

    @GetMapping("{id}")
    public Order getOrder(@PathVariable("id") int orderId) {
        return orderFacade.getOrder(orderId);
    }
    @PutMapping("/change")
    public long changeOrder(@RequestBody Order order) {
        return orderFacade.changeOrder(order);
    }
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public long addOrder(@RequestBody OrderDto orderDto) {
        Order order =orderDto.createOrder();
        return orderFacade.addOrder(order);
    }
    @GetMapping
    public List<Order> getAllOrders() {
        return orderFacade.getAllOrders();
    }
}
