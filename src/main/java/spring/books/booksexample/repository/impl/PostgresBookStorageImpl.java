package spring.books.booksexample.repository.impl;

import spring.books.booksexample.database.DatabaseConnection;
import org.springframework.stereotype.Repository;
import spring.books.booksexample.repository.BookStorage;
import spring.books.booksexample.domain.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class PostgresBookStorageImpl implements BookStorage {


    @Override
    public Book getBook(long id) {
        final String getOneBook = "SELECT * FROM books WHERE book_id= ?;";
        Connection connection = DatabaseConnection.initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(getOneBook);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("book_id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPageSum(resultSet.getInt("page_sum"));
                book.setYearOfPublish(resultSet.getInt("year_of_published"));
                book.setPublishingHouse(resultSet.getString("publishing_house"));
                return book;
            }
        } catch (SQLException s) {
            System.err.println("Cant stand writing these exceptions anymore .." + s.getMessage());
            throw new RuntimeException("Cant stand writing these exceptions anymore ..");
        } finally {
            DatabaseConnection.closeDatabaseResources(connection, preparedStatement);
        }

        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        final String getAll = "SELECT * FROM books";
        Connection connection = DatabaseConnection.initializeDataBaseConnection();
        Statement statement = null;
        List<Book> books = new ArrayList<>();

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getAll);
            while (resultSet.next()) {
                Book book = new Book();

                book.setId(resultSet.getLong("book_id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPageSum(resultSet.getInt("page_sum"));
                book.setYearOfPublish(resultSet.getInt("year_of_published"));
                book.setPublishingHouse(resultSet.getString("publishing_house"));

                books.add(book);
            }
        } catch (SQLException s) {
            System.err.println("Failed in geting sql query in getallbooks" + s.getMessage());
            throw new RuntimeException("Failed in geting sql query in getallbooks");

        } finally {
            DatabaseConnection.closeDatabaseResources(connection, statement);
        }
        return books;
    }

    @Override
    public int addBook(Book book) {
        final String sqlInsertBook = "INSERT INTO books( " + "book_id, title, author, page_sum, year_of_published," +
                " publishing_house) "
                + "VALUES (nextval('sequence'),?,?,?,?,?) returning book_id;";
        int book_id = 2;
        Connection connection = DatabaseConnection.initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;
        try {

            preparedStatement = connection.prepareStatement(sqlInsertBook);
            // preparedStatement.setLong(1, book.getId());
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getPageSum());
            preparedStatement.setInt(4, book.getYearOfPublish());
            preparedStatement.setString(5, book.getPublishingHouse());

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                book_id = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed in update sql query books section");
        } finally {
            DatabaseConnection.closeDatabaseResources(connection, preparedStatement);
        }
        System.out.println(book_id);
        return book_id;

    }
}
