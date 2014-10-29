import twitter4j.GeoLocation;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * <p>This is a code example of Twitter4J Streaming API - sample method support.<br>
 * Usage: java twitter4j.examples.PrintSampleStream<br>
 * </p>
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class TweetGet {

	private static String oAuthConsumerKey = "oQ7aCFiY7cjfmLUutJiouvzw5";
	private static String oAuthConsumerSecret = "mturYGKTi7CXhRlK9gkSJWF8XKyV1pTRLX7n2OBBydYKBTL9e6";
	private static String oAuthAccessToken = "1952751391-ISOlpkQMwv79EOtUQRwdOhlmY5eZMCWM3TePX50";
	private static String oAuthAccessTokenSecret = "lKbVowBHd7xogIsmdiuHB6WBGO0GyR8RoddDmS0XW0TGl";

    /**
     * Main entry of this application.
     *
     * @param args
     */
    public static void main(String[] args) throws TwitterException {
    	final TwitterDAO dao = new TwitterDAO();
    	
    	 ConfigurationBuilder cb = new ConfigurationBuilder();
         cb.setDebugEnabled(true)
           .setOAuthConsumerKey(oAuthConsumerKey)
           .setOAuthConsumerSecret(oAuthConsumerSecret)
           .setOAuthAccessToken(oAuthAccessToken)
           .setOAuthAccessTokenSecret(oAuthAccessTokenSecret);
         
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
                long statusId = status.getId();
                String screenName = status.getUser().getScreenName();
                String text = status.getText();
                GeoLocation location = status.getGeoLocation();
                if (location != null) {
	                double latitude = location.getLatitude();
	                double longitude = location.getLongitude();
                }
                String userProfileLocation = status.getUser().getLocation();  
                
                System.out.println(location + ", " + userProfileLocation);
                //TODO parse for keywords
                //TODO if location == null, use userProfileLocation, and query google maps 
                //dao.insertStatus(statusId, screenName, text, latitude, longitude, null);
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
                long statusId = statusDeletionNotice.getStatusId(); //same ID as the status ID above - just delete from DB
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };
        twitterStream.addListener(listener);
        twitterStream.sample();
    }
}