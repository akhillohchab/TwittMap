Read Me:

TwitMap By Akhil Lohchab (al3372) and Kevin Walters (Kmw2168)

We created a RDS(MySQL) and attached that to the EBS environment.

With the help of TweetGet.java, we populate the the database where we stored (userId, statusId,
 screenName, text, latitude, longitude, createdTime). The default value of keyword in the database for
a particular tweet was kept as "none".


We then take a standard jsp page with a panel for selecting keywords and another div for displaying the map.
The value of the selected keyword is passed to a java servlet that, in turn calls the database to retrieve 
tweet objects which are then passed back as JsonObjects. Location markers are derived from this and plotted on
the map.

To Run:
Download as zip or clone, change the security key (AWS) and install any jars, if missing (Note: Most, if not all, 
have been added to the github repository). 