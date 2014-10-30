<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>

<%! @SuppressWarnings("unchecked") %>
<%

String key;
String id;
String coordinates;

Double lng;
Double lat;

System.out.println("Test pt1: Running");

key = request.getParameter("keyword");

System.out.println("Test 2:" + key);

Class.forName("com.mysql.jdbc.Driver").newInstance();

// Open new connection.
java.sql.Connection connect;

connect = DriverManager.getConnection("jdbc:mysql://aag5obk3j1kr5y.cbfmpecmwali.us-east-1.rds.amazonaws.com:3306/ebdb?user=cloudcomputing&password=Assignment1");
Statement sqlStatement = conn.createStatement();

// Generate the SQL query.
String tweetquery = "SELECT * FROM "+ key;

// Get the query results and display them.
ResultSet Result = sqlStatement.executeQuery(tweetquery);

JSONArray TweetsData = new JSONArray();

while(Result.next()) {
	id = Result.getString("tweet_id");
	coordinates = Result.getString("coordinates");
	lng = Double.valueOf(coordinates.split(",")[0]);
	lat = Double.valueOf(coordinates.split(",")[1]);

	JSONObject Tweet = new JSONObject();

	Tweet.put("id", id);
	Tweet.put("lng", lng);
	Tweet.put("lat", lat);

	TweetsData.add(Tweet);
}

//Sent the data back to the client
out.print(TweetsData);
out.flush();

// Close the connection.
Result.close();
sqlStatement.close();
connect.close();
%>