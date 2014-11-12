package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

import app.TwitterDAO;
import app.Tweet;

//@WebServlet("/Twts")
public class db_Tweets extends HttpServlet {
  private String message;
  protected TweetGet getTweets;


public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
	String key;
	Long id;
	String coordinates;

	Double lng;
	Double lat;

	response.setContentType("application/json");
	
	PrintWriter out = response.getWriter();
	try{

	TwitterDAO dbobject;
	System.out.println("Test pt1: Running");

	key = request.getParameter("kbselect");
	dbobject = new TwitterDAO();
//	//List<Tweet> results = new ArrayList<Tweet>();
//	//results = dbobject.getFilteredTweets(request.getParameter("keyword"));
	List<Tweet> results = dbobject.getFilteredTweets(request.getParameter("keyword"));
	//out.write("\"" + results.get(0).toString() + "\"");
	
	String tweetJson = "\"loc\":[";
	String outputJson = "{\"success\":";
	for(Tweet tweet : results) {
		id = tweet.getStatusId();
		lng = tweet.getLongitude();
		lat = tweet.getLatitude();
		String thisTweetObj = "{\"id\":" + id + ",\"lng\":\"" + lng + "\",\"lat\":\"" + lat + "\"},";
		tweetJson += thisTweetObj;
	}
	tweetJson = tweetJson.substring(0, tweetJson.length() - 1);
	outputJson += "true, " + tweetJson + "]}";
	//out.write("\"Here\""); //THIS WORKS
	//out.write("{\"success\": true, \"loc\":[{\"id\":5069349,\"long\":\"-25.363882\",\"lat\":\"131.044922\"},{\"id\":6043509,\"long\":\"131.044922\",\"lat\":\"-25.363882\"}]}");// TODO THIS DOESN'T WORK - ADD QUOTES?
	out.write(outputJson);

	
//	//String result = (String) results.get(0);
////	List<String> loc = new ArrayList<String>();
////	id = results.get(0).getStatusId();
////	lng = results.get(0).getLongitude();
////	lat = results.get(0).getLatitude();
////	loc.add(0, id+","+lat.toString()+","+lng.toString());
////	JSONArray data = new JSONArray(loc);
////	String dataToSend = data.toString();
////	out.write(dataToSend);
////	out.write("\"" + results.size() + "\"");
//	
//	int i;
//	List<String> loc = new ArrayList<String>();
//	String dataString = ",\"loc:[\"";
//	for (i=0; i< results.size();i++)
//	{
////		JSONArray tweetArray = new JSONArray();
////		tweetArray.put(results.get(i).getStatusId());
////		tweetArray.put(results.get(i).getLatitude());
////		tweetArray.put(results.get(i).getLongitude());
//		
//		
////		id = results.get(i).getStatusId();
////		lng = results.get(i).getLongitude();
////		lat = results.get(i).getLatitude();
////		String tweetJSON ="[" + id + "," + lat + "," + lng + "],";
//		
//		
//		
//		//loc.add(i, id+","+lat.toString()+","+lng.toString());
//		//TweetsData.put(tweetArray);
////		dataString += tweetJSON;
//	}
//	dataString = dataString.substring(0, dataString.length() - 1);
//	dataString += "]";
//	//JSONArray TweetsData = new JSONArray(loc);
//	//JSONObject tweets = new JSONObject();
//	
//	String returnData = "{\"success\":true" + dataString + "}";
	
	//tweets.put("success", true);
	//tweets.put("loc", TweetsData);
	//tweets.put("lat", lat);

	//String jsonResult = tweets.toString();
	//out.println(jsonResult);
	//out.write(jsonResult);
	//out.write(returnData);

} catch (Exception ex) {
	        out.write("\"There was an error.\"");
	        out.write("\"" + ex.getMessage() + "\"");
	    } finally {
	        out.flush();
	        out.close();
	    }
}
	  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }

}
