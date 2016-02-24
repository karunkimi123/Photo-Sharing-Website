package org.waikato.cloud.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.waikato.cloud.user.User;
import org.waikato.cloud.user.Users;

public class LoginServlet extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException
	{
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException 
	{		
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		if( email == null )
		{
			req.getRequestDispatcher("/login.jsp").forward(req, res);
			return;
		}
		
		User user = Users.getUser(email,password);
		if (user == null)
		{
			req.setAttribute("errorMsg", "Invalid UserName/Password");
			req.getRequestDispatcher("/login.jsp").forward(req, res);		  
		}
		else
		{
			req.getSession(true).setAttribute("user", user);
			res.sendRedirect("/show");
			//req.getRequestDispatcher("/show").forward(req, res);
		}
	}
}
