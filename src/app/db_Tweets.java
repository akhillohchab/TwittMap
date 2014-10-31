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

	List<Tweet> results = new ArrayList<Tweet>();
	results = dbobject.getFilteredTweets(request.getParameter("keyword"));


	    //Populate with latitude and longitude of tweets
//	JSONObject result = new JSONObject();

	//System.out.println("Test 2:" + kbselect);
	int i;
	List<String> loc = new ArrayList<String>();
	for (i=0; i< results.size();i++)
	{
	//String row = results(i).toString();	
	//String[] iid = results.get(i).toString().split(",");
	id = results.get(i).getStatusId();
	lng = results.get(i).getLongitude();
	lat = results.get(i).getLatitude();
	loc.add(i, id+","+lat.toString()+","+lng.toString());
	}
	JSONArray TweetsData = new JSONArray(loc);
	JSONObject tweets = new JSONObject();

	tweets.put("success", true);
	tweets.put("loc", TweetsData);
	//tweets.put("lat", lat);

	String jsonResult = tweets.toString();
	out.println(jsonResult);

	

 //try end

} catch (Exception ex) {
	        out.println("There was an error.");
	    } finally {
	        out.flush();
	        out.close();
	    }
}
	  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }

}
