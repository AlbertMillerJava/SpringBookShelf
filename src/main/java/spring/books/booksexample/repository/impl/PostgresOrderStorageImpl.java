package spring.books.booksexample.repository.impl;

import org.springframework.stereotype.Repository;
import spring.books.booksexample.database.DatabaseConnection;
import spring.books.booksexample.domain.Book;
import spring.books.booksexample.domain.Customer;
import spring.books.booksexample.domain.Order;
import spring.books.booksexample.domain.OrderItem;
import spring.books.booksexample.repository.OrderStorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostgresOrderStorageImpl implements OrderStorage {


    @Override
    public Order getOrder(int orderId) {
        final String sqlGetOrder = "select orders.order_id, orders.order_date, customers.name, customers.customer_id," +
                " books.title, books.book_id, books.author, books.page_sum, books.year_of_published," +
                " books.publishing_house, order_items.amount, order_items.order_item_id " +
                "from orders " +
                "left outer join customers using (customer_id) " +
                "left outer join order_items using (order_id) " +
                "left outer join books using (book_id) " +
                "where orders.order_id = " + orderId;
        Connection connection = DatabaseConnection.initializeDataBaseConnection();
        Statement statement = null;
        System.out.println("bla1");
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlGetOrder);
            Order order = new Order();
            Customer customer = new Customer();
            List<OrderItem> orderedItems = new ArrayList<>();

            boolean duplicateRecord = true;
            while (resultSet.next()) {
                if (duplicateRecord) {
                    order.setOrderId(resultSet.getLong("order_id"));
                    order.setOrderDate(resultSet.getDate("order_date").toLocalDate());
                    customer.setName(resultSet.getString("name"));
                    customer.setCustomerId(resultSet.getInt("customer_id"));
                    order.setCustomer(customer);
                }
                duplicateRecord = false;
                Book book = new Book();
                book.setId(resultSet.getInt("book_id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPageSum(resultSet.getInt("page_sum"));
                book.setPublishingHouse(resultSet.getString("publishing_house"));
                book.setYearOfPublish(resultSet.getInt("year_of_published"));
                OrderItem orderItem = new OrderItem();
                orderItem.setAmount(resultSet.getInt("amount"));
                orderItem.setBook(book);
                orderItem.setOrderItemId(resultSet.getInt("order_item_id"));
                orderedItems.add(orderItem);
            }

            order.setOrderedItems(orderedItems);
            return order;
        } catch (SQLException e) {
            throw new RuntimeException("Failed in update sql query order section");
        } finally {
            DatabaseConnection.closeDatabaseResources(connection, statement);
        }
    }

    @Override
    public long changeOrder(Order order) {
        final String sqlDelete = " Update order_items set is_active = false where order_id=" + order.getOrderId();
        final String sqlAddNewItems = "Insert into order_items (order_item_id, order_id, book_id,amount,is_active)" +
                "Values (nextval('items_sequence')," + order.getOrderId() + ", ?,?,true)";

        long orderId = 0;

        Connection connection = DatabaseConnection.initializeDataBaseConnection();
        PreparedStatement updateStatement = null;
        PreparedStatement insertStatement = null;
        try {
            updateStatement = connection.prepareStatement(sqlDelete);
            insertStatement = connection.prepareStatement(sqlAddNewItems);
            updateStatement.execute();

            for (OrderItem orderItem : order.getOrderedItems()) {
                insertStatement.setLong(1, orderItem.getBook().getId());
                insertStatement.setInt(2, orderItem.getAmount());
                insertStatement.executeUpdate();

            }
            ResultSet resultSet = updateStatement.getResultSet();
            while (resultSet.next()) {
                orderId = resultSet.getInt(1);
            }


        } catch (SQLException e) {
            throw new RuntimeException("Failed in update sql query order section");
        } finally {
            DatabaseConnection.closeDatabaseResources(connection, updateStatement);
            DatabaseConnection.closeDatabaseResources(connection, insertStatement);
        }
        return orderId;
    }

    @Override
    public Order addOrder(Order order) {
        final String sqlAddOrder = "insert into orders(order_id ,order_date ,customer_id)" +
                "Values (nextval('order_sequence'),current_date , ?) returning order_id;";
        final String sqlListOfItems = "insert into order_items (order_item_id,order_id,book_id,amount,is_active) " +
                "values(nextval('items_sequence'),?,?,?,true);";
        long selectOrderId = 0;
        Order order1 = new Order();
        long orderId = 0;
        Connection connection = DatabaseConnection.initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementAddItems = null;
        try {
            preparedStatement = connection.prepareStatement(sqlAddOrder);
            preparedStatementAddItems = connection.prepareStatement(sqlListOfItems);
            preparedStatement.setLong(1, order.getCustomer().getCustomerId());

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                orderId = resultSet.getInt(1);
            }

            for (OrderItem orderItem : order.getOrderedItems()) {

                preparedStatementAddItems.setLong(1, orderId);
                preparedStatementAddItems.setLong(2, orderItem.getBook().getId());
                preparedStatementAddItems.setInt(3, orderItem.getAmount());
                preparedStatementAddItems.executeUpdate();
            }
            order1 = getOrder((int)orderId);

        } catch (SQLException e) {
            throw new RuntimeException("Failed in update sql query order section");
        } finally {
            DatabaseConnection.closeDatabaseResources(connection, preparedStatement);
            DatabaseConnection.closeDatabaseResources(connection, preparedStatementAddItems);
        }
        return order1;
    }

    @Override
    public List<Order> getAllOrders() {
        final String getAllOrders = "SELECT * FROM orders";
        Connection connection = DatabaseConnection.initializeDataBaseConnection();
        Statement statement = null;
        List<Order> orders = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getAllOrders);
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getLong(1));
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt(3));
                order.setCustomer(customer);
                order.setOrderDate(resultSet.getDate(2).toLocalDate());
                orders.add(order);
            }

        } catch (SQLException e) {
            System.err.println("Failed in geting sql query in getAllOrders" + e.getMessage());
            throw new RuntimeException("Failed in geting sql query in getallOrders");

        } finally {
            DatabaseConnection.closeDatabaseResources(connection, statement);
        }
        return orders;
    }
}
