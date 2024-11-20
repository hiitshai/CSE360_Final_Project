package application;

import java.sql.*;

public class DataManipulation {

	public static void update(String updateStatemnet) {

		try {
			Connection connection = DatabaseConnection.getConnection2DB();
			if (connection != null) {
				Statement stmt = connection.createStatement();
				stmt.executeUpdate(updateStatemnet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ResultSet query(String query) {

		ResultSet rs = null;

		try {
			Connection connection = DatabaseConnection.getConnection2DB();
			if (connection != null) {
				Statement stmt = connection.createStatement();
				rs = stmt.executeQuery(query);
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
}

