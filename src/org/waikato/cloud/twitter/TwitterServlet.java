package org.waikato.cloud.twitter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TwitterServlet extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException
	{
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException 
	{
		//String searchString = req.getParameter("searchString");
		String startTime = req.getParameter("start");
		String endTime = req.getParameter("end");
		
		if( startTime == null )
		{
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			endTime = format.format(date);
			
			date.setDate(date.getDate()-1);
			
			startTime = format.format(date);
		}		
		
		Map<String, Long> result = TwitterAPI.getAllTweets(startTime, endTime);
		Long electionCount = result.remove("New Zealand Election 2014");
		req.setAttribute("overallElectionKey", "New Zealand Election 2014");
		req.setAttribute("overallElectionCount", electionCount);
		req.setAttribute("result", result);
		req.setAttribute("startTime", startTime);
		req.setAttribute("endTime", endTime);
		
		req.getRequestDispatcher("/showResult").forward(req, res);
	}
}
