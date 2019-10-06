package spring.books.booksexample.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.books.booksexample.domain.Customer;
import spring.books.booksexample.domain.Order;
import spring.books.booksexample.domain.OrderItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    int customerId;
    List<OrderItemDto> listOfItems;

    public  Order createOrder(){
        List<OrderItem> orders = new ArrayList<>();
        for(OrderItemDto orderItemDto: listOfItems){
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(orderItemDto.createOrderItem().getBook());
            orderItem.setAmount(orderItemDto.createOrderItem().getAmount());
            orders.add(orderItem);
        }


        Order order =  new Order();
        order.setOrderedItems(orders);
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        order.setCustomer(customer);
        return order;
    }
}
