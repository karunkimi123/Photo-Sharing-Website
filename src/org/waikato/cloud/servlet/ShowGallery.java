package org.waikato.cloud.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.waikato.cloud.user.User;
import org.waikato.cloud.user.UserUtil;

public class ShowGallery extends HttpServlet 
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
		
		/*String bucketName = user.getBucketName();
		
		AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());		
		ObjectListing listing = s3Client.listObjects( bucketName );
		List<S3ObjectSummary> summaries = listing.getObjectSummaries();

		while (listing.isTruncated())
		{
	   		listing = s3Client.listNextBatchOfObjects(listing);
	   		summaries.addAll (listing.getObjectSummaries());
		}
		
		List<String> imageList = new ArrayList<String>();
		
		Iterator<S3ObjectSummary> iter = summaries.iterator();
		while(iter.hasNext())
		{
			S3ObjectSummary summary = iter.next();
			S3Object obj = s3Client.getObject(bucketName, summary.getKey());
        	String imgUrl = AmazonConstants.S3_BASE_URL + bucketName + "/" + obj.getKey();
        	imageList.add(imgUrl);
		}
		*/
		
		Map<String, String> imageList = UserUtil.getUserFiles(user.getUserId());
		
		req.setAttribute("images", imageList);
		
		req.getRequestDispatcher("/showGallery.jsp").forward(req, res);
	}
}
