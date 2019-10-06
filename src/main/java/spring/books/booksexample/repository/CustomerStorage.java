package spring.books.booksexample.repository;

import spring.books.booksexample.domain.Customer;

import java.util.List;

public interface CustomerStorage {
    Customer getCustomer (int customerId);

    List<Customer> getAllCustomers();

    int addCustomer (Customer customer);

}
