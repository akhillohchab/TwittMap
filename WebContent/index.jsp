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
	var markers = [];
  
    function initialize() {
    var Options = {
        zoom: 4,
        center: new google.maps.LatLng(-34.397, 150.644)
      };
    
    map = new google.maps.Map(document.getElementById('map'), Options);
    //var location = new google.maps.LatLng(131.044922, -25.363882);
    //var marker = new google.maps.Marker({
    //    position: location,
    //    map: map,
    //    title:"Hello World!"
    //});
    
    }

    google.maps.event.addDomListener(window, 'load', initialize);
  
    function TWonClick() {
    if($("#keyword").val() == 0){
      return false;
    }
      var lat;
      var lng;
	  var item = document.getElementById("loading");
	  item.style.display="block"
      $.getJSON("Twts", {keyword:$('#keyword').val()}, function(data) {
    	  if(data.success && data.loc.length > 0){
		  removeMarkers();
          var bounds = new google.maps.LatLngBounds ();
			  for(var i = 0; i < data.loc.length; i++){
				var coords = data.loc[i];
				lat = coords.lat;
				lng = coords.lng;
				if (lng == undefined) {
					lng = 0.0;
				}
				if (lat == undefined) {
					lat = 0.0;
				}
				if (lat == 0.0 && lng == 0.0) {
					continue;
				}
				var location = new google.maps.LatLng(lat, lng);
				var marker = new google.maps.Marker({
				  position : location,
				  map : map
				});
				markers.push(marker);
				bounds.extend(location);
			}
        }

      });
	  item.style.display="none"
      return false; // prevents the page from refreshing before JSON is read from server response
  }
  
  function removeMarkers() {
		for (var i = 0; i < markers.length; i++) {
			markers[i].setMap(null);
		}
		markers = []
  }

  </script>
  </head>
 
  <body>
    
     <div id="panel">
      <p>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        Choose  a keyword:
        <form name="Form1" action='Twts' method='get' target="map">
        <select class="selectpicker" id="keyword">
          <option value="isis">Isis</option>
          <option value="NFL">NFL</option>
          <option value="ebola">Ebola</option>
          <option value="Interstellar">Interstellar</option>
          <option value="Thanksgiving">Thanksgiving</option>
          <option value="halloween">Halloween</option>
          <option value="winter">Winter</option>
          <option value="NYC">NYC</option>
          <option value="obama">Obama</option>
  
        </select>
        <button type="submit" class="btn" id="go" onclick="return TWonClick();">Plot!</button>
		&nbsp;<div id="loading" style="display:none; ">Loading...</div>
      </form>
      </p>
    </div> 
 <div id="map">
  <input type="hidden" name="markers" value="someValue">
 </div>
 
 </body>

</html>