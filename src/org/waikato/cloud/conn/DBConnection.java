package org.waikato.cloud.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection 
{
	private static String HOST_NAME = "karunrajadb.cishbzz0rvdp.us-west-2.rds.amazonaws.com";

	private static int PORT = 3306;
	
	private static String DB_NAME = "karunraja";
	
	private static String USER_NAME = "raja";
	
	private static String USER_PASSWORD = "21-robin-10";
	
	public static String DB_URL = "jdbc:mysql://" + HOST_NAME + ":" + PORT + "/" + DB_NAME + "?user=" + USER_NAME + "&password=" + USER_PASSWORD;
	
	public static Connection getConnection()
	{
		Connection conn = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			conn = DriverManager.getConnection(DB_URL);
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static void close( Connection conn, Statement stmt )
	{
		close(stmt);
		
		close(conn);
	}
	
	public static void close( Connection conn )
	{
		try 
		{
			if( conn != null )
				conn.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void close( Statement stmt )
	{
		try 
		{
			if( stmt != null )
				stmt.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
