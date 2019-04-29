package weatherData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

public class DatabaseHandler {
	private static String dbURL = "jdbc:mysql://localhost:3306/projectweather;user=user1;password=FPGXUASjGgjBGkY9";
//	private static String dbURL = "jdbc:mysql://localhost:3306/weatherdata","user1","ElZBtmJBM1H4uP8Z";
	private static String tableName = "dataset";

	private static Connection connection = null;
	private static Statement statement = null;


	public static void createConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/weatherdata",
					"user1","ElZBtmJBM1H4uP8Z");
//			connection = DriverManager.getConnection(dbURL);
			statement = connection.createStatement();
		} catch (Exception except) {
			except.printStackTrace();
		}
	}

	public static java.util.List<DataHolder> selectWeatherEntries() {
		java.util.List<DataHolder> entries = new java.util.LinkedList<DataHolder>();
		try {
			statement = connection.createStatement();
			ResultSet results = statement.executeQuery("SELECT * FROM " + tableName);
			
			while (results.next()) {
				int id = results.getInt("id");
				java.sql.Timestamp timestamp = results.getTimestamp("timestamp");
				float humidity = results.getFloat("humidity");
				float temperature = results.getFloat("temperature");								
				String city = results.getString("city");

				DataHolder entry = new DataHolder(id, timestamp, humidity, temperature, city);
				entries.add(entry);
			}
			results.close();
			statement.close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		return entries;
	}

	public static void insertEntry(DataHolder entry) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO " + tableName + "(id, timestamp, humidity, temperature, city) values (?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, entry.getId());
			preparedStatement.setTimestamp(2, entry.getTimestamp());
			preparedStatement.setFloat(3, (float) entry.getHumidity());
			preparedStatement.setFloat(4, (float) entry.getTemperature());
			preparedStatement.setString(5, entry.getCity());

			preparedStatement.executeUpdate();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
	}
	
	public static void deleteAllEntries() {
		try {
			statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM " + tableName);
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
	}
	
	public static void removeEntryWithId(int id) {
		try {
			statement = connection.createStatement();
			String execution = "DELETE FROM " + tableName + " WHERE id=" + id ;
//			System.out.println(execution);
			statement.executeUpdate(execution);
		}
		catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
			System.out.println("Can not remove row from database");	}
	}
	
	public static int selectId() {
		int id_number = 0; 
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT MAX(id) AS highestid FROM " + tableName);
			while (rs.next()) {

				String id = rs.getString("highestid");
				System.out.println("id : " + id);
				if(id == null) {
					id_number = 0;
				} else {
					id_number = Integer.parseInt(id);
				}
			}
			
//			preparedStatement.setTimestamp(2, entry.getTimestamp());
//			preparedStatement.setFloat(3, (float) entry.getHumidity());
//			preparedStatement.setFloat(4, (float) entry.getTemperature());
//			preparedStatement.setString(5, entry.getCity());

			//preparedStatement.executeUpdate();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		return id_number;
	}

	public static void shutdown() {
		try {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				DriverManager.getConnection(dbURL + ";shutdown=true");
				connection.close();
			}
		} catch (SQLException sqlExcept) {

		}
	}
}
