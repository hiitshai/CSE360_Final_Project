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

		/*String query = "SELECT book.author " + "FROM book " + "JOIN borrow ON book.book_id = borrow.book_id "
				+ "JOIN user ON borrow.user_id = user.user_id " + "WHERE user.name = 'Jane Smith'";
		
		
		ResultSet rs = DataManipulation.query(query);

		try {
			while (rs.next()) {
			
				System.out.println("Author: " + rs.getString("book.author"));
				
			}
			rs.close();
			
		} catch (SQLException e) {
			
			System.out.println("No author is found.");
			
		}*/
        
    }
}