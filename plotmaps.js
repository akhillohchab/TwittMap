
  function main() {
  var keyword = $("#kbselect").val();
  
  var map = initialize();
  myTweets(map, keyword);
  
});
  $("#kbselect" ).change(function () {
    

    keyword = $("#kbselect").val();

    
    myTweets(map, keyword);
  });
  
}

function initialize() {
  var myLatlng = new google.maps.LatLng(-25.363882,131.044922);
  var Options = {
    center: new google.maps.LatLng(40.7127, -74.0059),
    zoom: 8,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  };
  var map = new google.maps.Map(document.getElementById('map'), Options);

   return map;
}
 
function myTweets(map, keyword) {
  
  var serverUrl = "http://twittmap-env.elasticbeanstalk.com/?keyword="+keyword;
  
  $.ajax({
      url : serverUrl,
      data : '',
      dataType: 'json',
      success : function(jsonarray) {
          
          
          
          $.each(jsonarray, function(i, tweet) {
            var lng = tweet.lng;
            var lat = tweet.lat;
            var TweetLatLng = google.maps.LatLng(lat,lng);
            //var point = new google.maps.LatLng(lat, lng);
            var marker = new google.maps.Marker({
            position: TweetLatLng,
            map: map,

          });
          
      
      }
  });
  
}

$(document).ready(main);

