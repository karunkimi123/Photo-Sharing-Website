package org.waikato.cloud.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException
	{
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException 
	{					
		HttpSession session = req.getSession(false);
		if( session != null )
		{
			session.invalidate();
		}
		req.setAttribute("errorMsg", "Logged out successfully");
		req.getRequestDispatcher("/login.jsp").forward(req, res);
		//res.sendRedirect("/login.jsp");
	}
}
