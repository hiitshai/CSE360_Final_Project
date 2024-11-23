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

        Scene scene = new Scene(loginPage, 800, 600);
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
        
		
		String createUserTable = "CREATE TABLE IF NOT EXISTS user (" +
		        "email VARCHAR(255) UNIQUE NOT NULL, " +
		        "username VARCHAR(255) UNIQUE NOT NULL, " +
		        "password VARCHAR(255) NOT NULL, " +
		        "is_buyer BOOLEAN DEFAULT FALSE, " +
		        "is_seller BOOLEAN DEFAULT FALSE, " +
		        "is_admin BOOLEAN DEFAULT FALSE" +
		        ")";
		DataManipulation.update(createUserTable);


		String insertUsers = "INSERT INTO user (email, username, password, is_buyer, is_seller, is_admin) VALUES " +
		        "('hdtang1@asu.edu', 'hdtang1', 'password', '0', '0', '1')," + //My SQL does not support boolean data types, it replaces false with 0 and true with 1
		        "('test', 'test', 'test', '1', '1', '0')";
		DataManipulation.update(insertUsers);


		String createBookTable = "CREATE TABLE IF NOT EXISTS books (" +
		        "book_id INT AUTO_INCREMENT PRIMARY KEY, " +
		        "title VARCHAR(255) NOT NULL, " +
		        "category VARCHAR(50), " +
		        "`condition` VARCHAR(20), " +  // Enclosed in backticks
		        "price DECIMAL(10,2), " +
		        "available BOOLEAN DEFAULT TRUE, " +
		        "publication_year INT" +
		        ")";
		DataManipulation.update(createBookTable);


		String insertBooks = "INSERT INTO books (title, category, `condition`, price, available, publication_year) VALUES " +
		        "('Java Programming', 'Computer Books', 'Used Like New', 59.99, 1, 2023)," +
		        "('Calculus I', 'Math Books', 'Moderate Use', 45.99, 1, 2022)," +
		        "('Chemistry Basics', 'Natural Science Books', 'Heavy Use', 29.99, 1, 2021)," +
		        "('English Literature', 'English Language Books', 'Used Like New', 35.99, 1, 2023)," +
		        "('Physics 101', 'Natural Science Books', 'Moderate Use', 49.99, 1, 2022)";
		DataManipulation.update(insertBooks);


    }
}