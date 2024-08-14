package repository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Db {

	private static Connection connection;

	public static Connection getConnection() {

		if (connection == null) {

			try {

				Properties props = loadProperties();

				String url = props.getProperty("dburl");

				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, props);

			} catch (SQLException e) {
				
				throw new DbException(e.getMessage());
			
			} catch (ClassNotFoundException e) {
				
				throw new DbException(e.getMessage());
				
			}

		}

		return connection;

	}

	private static Properties loadProperties() {

		try (InputStream is = Db.class.getClassLoader().getResourceAsStream("./resources/db.properties")) {
			
			Properties props = new Properties();
			props.load(is);
			return props;

		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}

	}

	public static void closeConnection() {

		if (connection != null) {

			try {

				connection.close();

			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}

		}

	}

	public static void closeStatement(Statement st) {

		try {

			st.close();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	public static void closeResultSet(ResultSet rs) {

		try {

			rs.close();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

}
