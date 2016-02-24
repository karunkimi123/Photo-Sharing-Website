package org.waikato.cloud.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.waikato.cloud.user.User;
import org.waikato.cloud.user.UserUtil;
import org.waikato.cloud.util.AmazonConstants;
import org.waikato.cloud.util.UploadFile;

public class UploadServlet extends HttpServlet 
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
		try
		{
			List<FileItem> multipartfiledata = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
			
			String keyName = System.currentTimeMillis() + "-" + multipartfiledata.get(0).getName();
			
			UploadFile.uploadFile(bucketName, keyName, multipartfiledata);
			
			String imgUrl = AmazonConstants.S3_BASE_URL + bucketName + "/" + keyName;
			
			UserUtil.addFileLocation(user.getUserId(), keyName, imgUrl);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new ServletException(e.getMessage());
		}
		
		req.getRequestDispatcher("/show").forward(req, res);
	}
}
