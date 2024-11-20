package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	// Database URL, username, and password
	private static final String url = "jdbc:mysql://localhost:3306/sys";
	private static final String user = "root";
	private static final String password = "password";
	private static Connection connection = null;


	public static Connection getConnection2DB() {
		
		// Establish connection
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);

		} catch (SQLException | ClassNotFoundException e) {

			System.out.println("Error connecting to the database.");
			e.printStackTrace();
		}

		return connection;

	}
}
