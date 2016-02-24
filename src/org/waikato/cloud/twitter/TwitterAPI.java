package org.waikato.cloud.twitter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.waikato.cloud.conn.DBConnection;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterAPI
{
	private static Twitter twitter = null;
	
	public static Twitter getTwitterInstance() 
	{
		if( twitter != null )
			return twitter;
		
		twitter = new TwitterFactory().getInstance();
 
		// Set access
		twitter.setOAuthConsumer("fWbsLD2A8m4ToyL1Zeppx6fZ8", "LB3Hu2n274Et3XqOgfuzaWutR0pMkpOp9AnPrwnLiPquGonTHI");
		twitter.setOAuthAccessToken(new AccessToken("43055130-jAndpa8Ct0lNQ4GEV95DNr2YlL5i6HXo5Zq0ppkt2", "R5g5azEqRrmIq8GeB60LnXPNi89NtRE9UPdmGWEYLUNAR"));
 
		return twitter;
	}
	
	public static void StartTimerTask()
	{

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);



        Timer time = new Timer(); // Instantiate Timer Object

        // Start running the task on Monday at 15:40:00, period is set to 8 hours
        // if you want to run the task immediately, set the 2nd parameter to 0
        time.schedule(new TweetTask(), 0, 1000 * 60 * 30);
	}
	
	public static long getTweets( String searchQuery, String startTime, String endTime ) 
	{
		long totalCount = 0;
		
		try 
		{
		    Query query = new Query(searchQuery);
		    query.setSince(startTime);
		    query.setUntil(endTime);
		    
		    QueryResult result;
		    do
		    {
		    	result = getTwitterInstance().search(query);
		    	totalCount += result.getTweets().size();
		    }
		    while((query = result.nextQuery()) != null);		    
		    
		}
		catch (TwitterException e)
		{            
			e.printStackTrace();
		}
		return totalCount;
	}
	
	public static Map<String, Long> getAllTweets(String startTime, String endTime)
	{
		List<String> parties = new ArrayList<String>();
		parties.add("New Zealand Election 2014");
		parties.add("Green Party");
		parties.add("Labour Party");
		parties.add("National Party");
		parties.add("New Zealand First Party");
		parties.add("Mana Party");
		parties.add("ACT Party");
		parties.add("Māori Party");
		parties.add("United Future Party");
		
		Map<String, Long> resultMap = new LinkedHashMap<String, Long>();
		
		long totalCount = 0;
		Iterator<String> iterator = parties.iterator();
		while( iterator.hasNext() )
		{
			String partyName = iterator.next();
			long count = new Random().nextInt(100);
			//long count = getTweets(partyName, startTime, endTime);
			if( count > 0 )
			{
				totalCount += count;
				resultMap.put(partyName, count);
			}
		}
		resultMap.put("New Zealand Election 2014", totalCount);
		
		return sortByValues(resultMap);
	}
	
	private static Map sortByValues(Map map)
	{ 
	       List<Long> list = new LinkedList<Long>(map.entrySet());
	       // Defined Custom Comparator here
	       Collections.sort(list, new Comparator() 
	       {
	            public int compare(Object o1, Object o2) {
	               return ((Comparable) ((Map.Entry) (o2)).getValue())
	                  .compareTo(((Map.Entry) (o1)).getValue());
	            }
	       });

	       Map sortedHashMap = new LinkedHashMap();
	       for (Iterator it = list.iterator(); it.hasNext();) {
	              Map.Entry entry = (Map.Entry) it.next();
	              sortedHashMap.put(entry.getKey(), entry.getValue());
	       } 
	       return sortedHashMap;
	 }
	
	public static class TweetTask extends TimerTask  
	{
		static Date date = new Date();
	   
		public TweetTask(){}

	   public void run()
	   {
		   try 
		   {
			   date.setDate(date.getDate()-1);
			   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			   String endTime = format.format(date);					
			   String startTime = endTime;
			   Map<String, Long> result = getAllTweets(startTime, endTime);
	       } 
		   catch (Exception ex) 
		   {
	    	   ex.printStackTrace();
		   }
	   }
	   
	   public void getTweets(String startTime, String endTime)
	   {
			List<String> parties = new ArrayList<String>();
			parties.add("New Zealand Election 2014");
			parties.add("Green Party");
			parties.add("Labour Party");
			parties.add("National Party");
			parties.add("New Zealand First Party");
			parties.add("Mana Party");
			parties.add("ACT Party");
			parties.add("Māori Party");
			parties.add("United Future Party");
			
			
			Connection conn = null;
			PreparedStatement stmt = null;	
			
			try
			{
				String addTweetQuery = "insert into Tweet values(?,?,?)";				
				conn = DBConnection.getConnection();
				stmt = conn.prepareStatement(addTweetQuery);
				
				int totalCount = 0;
				Iterator<String> iterator = parties.iterator();
				while( iterator.hasNext() )
				{
					String partyName = iterator.next();
					int count = new Random().nextInt(100);
					//long count = getTweets(partyName, startTime, endTime);
					if( count > 0 )
					{
						totalCount += count;
						if( partyName.equalsIgnoreCase("New Zealand Election 2014") )
						{
							stmt.setString(1, startTime);
							stmt.setString(2, partyName);
							stmt.setInt(3, count);
							
							stmt.addBatch();
						}
					}
				}
				stmt.setString(1, startTime);
				stmt.setString(2, "New Zealand Election 2014");
				stmt.setInt(3, totalCount);
				
				stmt.executeUpdate();
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
			finally
			{
				DBConnection.close(conn, stmt);
			}
		}
	}
}
