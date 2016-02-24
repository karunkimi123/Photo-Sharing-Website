package org.waikato.cloud.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import org.waikato.cloud.conn.DBConnection;

public class UserUtil 
{

	public static Map<String, String> getUserFiles( int userId )
	{
		Map<String, String> imageList = new LinkedHashMap<String, String>();
		
		Connection conn = null;
		Statement stmt = null;
		try 
		{
			String selectQuery = "select * from UserFileLocation where user_id=" + userId;
				
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(selectQuery);
			while( rs.next() )
			{
				imageList.put(rs.getString("file_Name"), rs.getString("file_location"));
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DBConnection.close(conn, stmt);
		}
				
		return imageList;
	}
	
	public static boolean addFileLocation( int userId, String keyName, String fileLocation )
	{
		boolean added = false;
		Connection conn = null;
		PreparedStatement stmt = null;	
		try
		{
			String addUserFileQuery = "insert into UserFileLocation values(?,?,?)";
			
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(addUserFileQuery);
			
			stmt.setInt(1, userId);
			stmt.setString(2, keyName);
			stmt.setString(3, fileLocation);
			
			stmt.execute();
			
			added = true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			DBConnection.close(conn, stmt);
		}
				
		return added;
	}
	
	public static boolean deleteFile( int userId, String fileName )
	{
		boolean deleted = false;
		Connection conn = null;
		PreparedStatement stmt = null;	
		try
		{
			String deleteFileQuery = "delete from UserFileLocation where user_id=? and file_name=?";
			
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(deleteFileQuery);
			
			stmt.setInt(1, userId);
			stmt.setString(2, fileName);
			
			stmt.execute();
			
			deleted = true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			DBConnection.close(conn, stmt);
		}
				
		return deleted;
	}
	
	
}
