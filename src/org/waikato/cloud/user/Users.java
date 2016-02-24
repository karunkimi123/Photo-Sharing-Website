package org.waikato.cloud.user;

import org.waikato.cloud.conn.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Users
{
	public static User getUser( String email )
	{
		return getUser(email, null);
	}
	
	public static User getUser( String email, String password  )
	{
		User user = null;
		Connection conn = null;
		Statement stmt = null;
		try 
		{
			String selectUserQuery = "select * from Users where email_id='" + email + "'";
			if( password != null )
			{
				selectUserQuery += " and password='" + password + "'";
			}
				
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(selectUserQuery);
			if( rs.next() )
			{
				user = new User();
				user.setUserId(rs.getInt(1));
				user.setUserName(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setPassword(rs.getString(4));
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
				
		return user;
	}
	
	public static boolean isUserExists( String email )
	{
		boolean exists = false;
		Connection conn = null;
		Statement stmt = null;	
		try 
		{
			String existsUserQuery = "select * from Users where email_id='" + email + "'";
			
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(existsUserQuery);
			if( rs.next() )
			{
				exists = true;
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
				
		return exists;
	}
	
	public static boolean addUser( String userName, String email, String password )
	{
		boolean added = false;
		Connection conn = null;
		PreparedStatement stmt = null;	
		try
		{
			String addUserQuery = "insert into Users(user_name, email_id, password) values(?,?,?)";
			
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(addUserQuery);
			
			stmt.setString(1, userName);
			stmt.setString(2, email);
			stmt.setString(3, password);
			
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
	
	public static boolean deleteUser( int userId )
	{
		boolean deleted = false;
		Connection conn = null;
		Statement stmt = null;	
		try 
		{
			String deleteUserQuery = "delete from Users where user_id=" + userId;
			
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(deleteUserQuery);
			
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
	
	public static boolean updateUser( int userId, String password )
	{
		boolean updated = false;
		Connection conn = null;
		Statement stmt = null;	
		try 
		{
			String updateUserQuery = "update Users set password='" + password + "' where user_id=" + userId;
			
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(updateUserQuery);
			
			updated = true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			DBConnection.close(conn, stmt);
		}
				
		return updated;
	}
}
