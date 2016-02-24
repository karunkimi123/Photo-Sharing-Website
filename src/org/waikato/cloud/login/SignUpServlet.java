package org.waikato.cloud.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.waikato.cloud.user.User;
import org.waikato.cloud.user.Users;
import org.waikato.cloud.util.S3BucketHandler;

public class SignUpServlet extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException
	{
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException 
	{
		
		String userName = req.getParameter("userName");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String passwordConfirm = req.getParameter("passwordConfirm");
		
		if( !password.equals(passwordConfirm) )
		{
			req.setAttribute("errorMsg", "Password is not matching");
		}
		else
		{
			boolean exists = Users.isUserExists(email);
			if (exists)
			{
				req.setAttribute("errorMsg", "User already exists");
							  
			}
			else
			{
				if( Users.addUser(userName, email, password) )
				{
					User user = Users.getUser(email);
					S3BucketHandler.createBucket(user.getBucketName());
					
					req.setAttribute("errorMsg", "User added successfully. Login here.");
				}
				else
				{
					req.setAttribute("errorMsg", "Unable to add user");
				}
			}
		}
		
		req.getRequestDispatcher("/login.jsp").forward(req, res);
	}
}
