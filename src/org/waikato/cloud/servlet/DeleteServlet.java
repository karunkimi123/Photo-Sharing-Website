package org.waikato.cloud.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.waikato.cloud.user.User;
import org.waikato.cloud.user.UserUtil;
import org.waikato.cloud.util.S3BucketHandler;

public class DeleteServlet extends HttpServlet 
{

	public void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException
	{
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException 
	{
		User user = (User)req.getSession().getAttribute("user");
		if( user == null )
		{
			throw new ServletException("Invalid User");
		}
		
		String bucketName = user.getBucketName();
		String fileName = req.getParameter("fileName");
		S3BucketHandler.deleteFile(bucketName, fileName);
		
		UserUtil.deleteFile(user.getUserId(), fileName);
		
		req.getRequestDispatcher("/show").forward(req, res);
	}
}

