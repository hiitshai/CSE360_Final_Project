package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {

	private static Stage primaryStage;

    @Override
    public void start(Stage primary) {
    	primaryStage = primary; 
        loginPage loginPage = new loginPage();

        Scene scene = new Scene(loginPage, 800, 400);
        loginPage.setStyle("-fx-background-color: #F5DEB3;");
        primary.setTitle("ASU Bookstore");
        primary.setScene(scene);
        primary.show();
    }
    
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
        
        String createBookTable = "CREATE TABLE IF NOT EXISTS book (" +
		        "book_id INT AUTO_INCREMENT PRIMARY KEY, " +
		        "title VARCHAR(255) NOT NULL, " +
		        "author VARCHAR(255) NOT NULL)";
		DataManipulation.update(createBookTable);

		String createUserTable = "CREATE TABLE IF NOT EXISTS user (" +
		        "user_id INT AUTO_INCREMENT PRIMARY KEY, " +
		        "name VARCHAR(255) NOT NULL, " +
		        "email VARCHAR(255) UNIQUE NOT NULL, " +
		        "join_date DATE)";
		DataManipulation.update(createUserTable);

		String createBorrowTable = "CREATE TABLE IF NOT EXISTS borrow (" +
		        "borrow_id INT AUTO_INCREMENT PRIMARY KEY, " +
		        "book_id INT NOT NULL, " +
		        "user_id INT NOT NULL, " +
		        "borrow_date DATE, " +
		        "return_date DATE, " +
		        "FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE CASCADE, " +
		        "FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE)";
		DataManipulation.update(createBorrowTable);

		
		
		
		
		String insertBooks = "INSERT INTO book (title, author) VALUES " +
		        "('The Great Gatsby', 'F. Scott Fitzgerald'), " +
		        "('1984', 'George Orwell'), " +
		        "('To Kill a Mockingbird', 'Harper Lee')";
		DataManipulation.update(insertBooks);

		String insertUsers = "INSERT INTO user (name, email, join_date) VALUES " +
		        "('John Doe', 'johndoe@example.com', '2023-01-15'), " +
		        "('Jane Smith', 'janesmith@example.com', '2023-02-10'), " +
		        "('Alice Johnson', 'alicej@example.com', '2023-03-05')";
		DataManipulation.update(insertUsers);

		String insertBorrows = "INSERT INTO borrow (book_id, user_id, borrow_date, return_date) VALUES " +
		        "(1, 1, '2023-11-01', '2023-11-15'), " +
		        "(2, 2, '2023-11-05', NULL), " +
		        "(3, 3, '2023-11-10', '2023-11-20')";
		DataManipulation.update(insertBorrows);


		
		String query = "SELECT book.author " + "FROM book " + "JOIN borrow ON book.book_id = borrow.book_id "
				+ "JOIN user ON borrow.user_id = user.user_id " + "WHERE user.name = 'Jane Smith'";
		
		
		ResultSet rs = DataManipulation.query(query);

		try {
			while (rs.next()) {
			
				System.out.println("Author: " + rs.getString("book.author"));
				
			}
			rs.close();
			
		} catch (SQLException e) {
			
			System.out.println("No author is found.");
			
		}
        
    }
}