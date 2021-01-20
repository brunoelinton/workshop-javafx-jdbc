package db;


import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
	private static Connection conn = null;
	/**
	 * METHOD:		loadProperties()
	 * OBJECTIVE:	load database properties connection:
	 * 				user, password, url and ssl configuration
	 */
	public static Properties loadProperties() {
		// STARTING PROCESS READ FILE OF PROPERTIES
		try(FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch(IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	/* METHOD: 		getConnection()
	 * OBJECTIVE:	establish connection with database
	 * */
	public static Connection getConnection() {
		// IF CONNECTION WASN'T BE DONE
		if(conn == null) {
			try {
				Properties props = loadProperties();			// FIRST, GET THE PROPERTIES OF CONNECTION
				String url = props.getProperty("dburl");		// SECOND, GET THE URL OF DATA BASE
				conn = DriverManager.getConnection(url, props);	// NOW, MAKE A CONNECTION WITH THE DATA BASE
			} catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}
	
	/* 
	 * METHOD:		closeConnection()
	 * OBJECTIVE:	close connection with database
	 * */
	public static void closeConnection() {
		// IF CONNECTION WAS BE STABILISHED
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	/*
	 * METHOD:		closeStatement(Statement st)
	 * OBJECTIVE:	close sql resource responsible for assembly querys
	 * */
	public static void closeStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
		
	/*
	 * METHOD: 		closeResultStatement(ResultSet rs)
	 * OBJECTIVE: 	close sql resource responsible by save results of querys
	 */
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}
