<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <title>TwitMap_v1</title>
    <style type="text/css">
      html { height: 100% }
      body { height: 100%; margin: 0; padding: 0 }
      #map { height: 90%;  }
      #panel {width: 100%%;}
 
    </style>
    
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    
    <script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?libraries=sensor=false">
    </script> 

    <script>
    var map;
  
    function initialize() {
    var Options = {
        zoom: 8,
        center: new google.maps.LatLng(-34.397, 150.644)
      };
    
    map = new google.maps.Map(document.getElementById('map'),
      Options);
    }

    google.maps.event.addDomListener(window, 'load', initialize);
  
    function TWonClick() {
    if($("#keyword").val() == 0){
      return false;
    }
      var lat;
      var lng;
      $.getJSON("Twts", {keyword:$('#keyword').val()}, function(data) {
    	  if(data.success && data.loc.length > 0){
          var bounds = new google.maps.LatLngBounds ();
          for(var i = 0; i < data.loc.length; i++){
        	var marker = data.loc[i].split(",");
        	lat = marker[1];
            lng = marker[2];
            var location = new google.maps.LatLng(lat, lng);
            var marker = new google.maps.Marker({
              position : location,
              map : map
            });
            bounds.extend(location);
            marker.setMap(map);
          }
          map.fitBounds(bounds);
        }

      });
      return false; // prevents the page from refreshing before JSON is read from server response
  }

  </script>
  </head>
 
  <body>
    
     <div id="panel">
      <p>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        Choose  a keyword:
        <form name="Form1" action='db_Tweets' method='get' target="map">
        <select class="selectpicker" id="keyword">
          <option value="isis">Isis</option>
          <option value="NFL">NFL</option>
          <option value="ebola">Ebola</option>
          <option value="Interstellar">Hollywood</option>
          <option value="Thanksgiving">Thanksgiving</option>
          <option value="halloween">Halloween</option>
          <option value="winter">Winter</option>
          <option value="NYC">NYC</option>

          <option value="obama">Obama</option>
  
        </select>
        <button type="submit" class="btn" id="go" onclick="return TWonClick();">Plot!</button>
 
      </form>
      </p>
    </div> 
 <div id="map">
  <input type="hidden" name="markers" value="someValue">
 </div>
 
 </body>

</html>