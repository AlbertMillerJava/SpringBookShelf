package spring.books.booksexample.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/book_shelf";
    private static final String DATABASE_USER = "postgres";
    private static final String DATABASE_PASS = "password";

    static {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException cnf) {
            System.err.println("Cant find postgresql driver ");
        }
    }

    public static Connection initializeDataBaseConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, DATABASE_USER, DATABASE_PASS);
        } catch (SQLException sql) {
            System.err.println("Server cant initialize connection to databse");
            throw new RuntimeException("Server cant initialize connection to databse");
        }
    }

    public static void closeDatabaseResources(Connection connection, Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException s) {
            System.err.println("Error during closing databse resources");
            throw new RuntimeException("Error during closing databse resources");
        }

    }
}
