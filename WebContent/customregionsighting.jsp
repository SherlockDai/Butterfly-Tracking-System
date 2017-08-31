<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="nixyteam.ButterflyBean" %> 
<%@ page import="nixyteam.UserBean"  %>
<%@ page import="nixyteam.LocationBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!------------------- Bootstrap Starts Here ------------------------>
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<!------------------- Bootstrap Ends Here -------------------------->
<!------------------------HERE API Starts Here------------------------------>
<script src="http://js.api.here.com/v3/3.0/mapsjs-core.js"
        type="text/javascript" charset="utf-8"></script>
<script src="http://js.api.here.com/v3/3.0/mapsjs-service.js"
        type="text/javascript" charset="utf-8"></script>
<script src="http://js.api.here.com/v3/3.0/mapsjs-places.js"
        type="text/javascript" charset="utf-8"></script>
<script src="http://js.api.here.com/v3/3.0/mapsjs-ui.js"
        type="text/javascript" charset="utf-8"></script>
<script src="http://js.api.here.com/v3/3.0/mapsjs-mapevents.js"
        type="text/javascript" charset="utf-8"></script>
<script src="js/jquery-1.11.3.min.js"></script>
<link rel="stylesheet" type="text/css"
      href="http://js.api.here.com/v3/3.0/mapsjs-ui.css" />
<!-------------------------HERE API Ends Here------------------------------>
<!-------------------------WEBIX API Starts Here--------------------------->
<script src="webix/codebase/webix.js"></script>   
<link href="webix/codebase/skins/material.css" rel="stylesheet" type="text/css">
<!-------------------------WEBIX API Ends Here--------------------------->
<link href="css/dashboard.css" rel="stylesheet" type="text/css">
<style>
    html,body { height:100%; width:100%; overflow:hidden; margin:0px; padding:0px;}
    div#MapContainer { height:100%; width:100%; }
	p {margin: 0 0 0px;}
	 .jumbotron {background-color: #FFFFFF;
    			padding-bottom: 0}
</style>

<title>Custom Region Sighting</title>
</head>
<body onload="initalMap()">

<% if (session == null || session.getAttribute("dashboard") == null) { %>
	<font color="red">Your session is outdated! Or may be you just need to login. </font><br>
	<jsp:include page="index.jsp"></jsp:include>
<% return; }%>
<%! @SuppressWarnings("unchecked") %>
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand disabled">Butterfly Tracking System | Sightings | N.I.X.Y.</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <% if (session == null || session.getAttribute("user") == null) { %>	           
             	<li><a href="login.jsp">Login</a></li>
            	<li><a href="index.jsp">HomePage</a></li>
            <% } else { %>
                <li><a href="dashboard.jsp">DashBoard</a></li>
            <% } %>
		  </ul>
    </div>
  </div>
</nav>
<br>
<div class="jumbotron">
	<div class="form-horizontal col-md-6 col-md-offset-3">
  	<div class="form-group">
    <label  class="col-sm-3 control-label">Coordinate</label>
    <div class="col-sm-4">
      <input id="latitude" type="number" class="form-control"  min="-90" max="90" step="0.0000001" placeholder="Latitude" required>
    </div>
    <div class="col-sm-4">
      <input id="longitude" type="number" class="form-control" min="-180" max="180" step="0.0000001" placeholder="Longitude" required>
    </div>
  	</div>
  	<div class="form-group">
	<label class="col-sm-3 control-label">Radius</label>
    <div class="col-sm-4">
      <input id="radius" type="number" class="form-control" placeholder="Radius(mile)"  required>
    </div>
  	</div>
  	<div class="form-group">
    <div class="col-sm-offset-3 col-sm-9">
      <button class="btn btn-primary" onclick="onSubmit()">Submit</button>
    </div>
  	</div>
</div>
</div>
 <div id="MapContainer"></div>

<!------------------ Visualization Starts Here ------------------>
<script> 	
var sightings = new Array();
<%
for (LocationBean sighting: (ArrayList<LocationBean>)session.getAttribute("dashboard")) {
%>
sightings.push([<%=sighting.getLatitude()%>,<%=sighting.getLongitude()%>,"<%= sighting.getLocation()%>","<%= sighting.getState()%>","<%= sighting.getCountry()%>"]);
<%}%>
function initalMap(){
var platform = new H.service.Platform({
    'app_id': 'M3dZEgEAPQJqXonN1m3e',
    'app_code': 'keqdbv92h0m9rZeA0J0HwQ'
});
var maptypes = platform.createDefaultLayers();
var zoom = 3;
var centerPoint = new H.geo.Point(20, -101)
   // Instantiate (and display) a map object:
map = new H.Map(           
	document.getElementById('MapContainer'),
       maptypes.normal.traffic);
map.setZoom(zoom);
map.setCenter(centerPoint);
var behavior;
var mapEvents = new H.mapevents.MapEvents(map);
behavior = new H.mapevents.Behavior(mapEvents);
 //mark the coordinates
var group = new H.map.Group();
var centerLat;
var centerLng;
map.addObject(group);
for(var i = 0; i < sightings.length; i++){
	var marker = new H.map.Marker(new H.geo.Point(sightings[i][0],sightings[i][1]),
				{data: "The location is "+sightings[i][2]+", the state is "
					+sightings[i][3]+", and the country is "+sightings[i][4]});
	marker.addEventListener("tap", onTapMarker);
	group.addObject(marker);
	
	//listner is here
function onTapMarker(ClickEvent){
     var lat = ClickEvent.target.getPosition().lat;
     var lng = ClickEvent.target.getPosition().lng;
     var xml = ''+''
     $.ajax({
         url: 'https://weather.cit.api.here.com/weather/1.0/report.json',
         type: 'GET',
         dataType: 'jsonp',
         jsonp: 'jsonpcallback',
         data: {
             product: 'observation',
             latitude: lat,
             longitude: lng,
             oneobservation: 'true',
             app_id: 'DemoAppId01082013GAL',
             app_code: 'AJKnXv84fjrb0KIHawS0Tg'
         },
         success: function (data) {
             alert(ClickEvent.target.getData());
         }
     });

    }
   }
}
   function onSubmit(){
	var longitude = document.getElementById("longitude").value;
	var latitude = document.getElementById("latitude").value;
	var radius = document.getElementById("radius").value;
	if(longitude == "" || latitude == "" || radius == ""){
		alert("Please input all the things");
		return false;
	}
	if(longitude > 180 || longitude < -180  ){
		alert("Please make sure the longitude is amoung -180 and 180 ");
		return false;
	}
	if(latitude > 90 || latitude < -90  ){
		alert("Please make sure the latitude is amoung -90 and 90 ");
		return false;
	}
	if(radius > 1000 || radius < 1  ){
		alert("Please make sure the latitude is amoung 1 and 1000 ");
		return false;
	}
	centerPoint = new H.geo.Point(latitude,longitude );
	map.setCenter(centerPoint);
	if(radius <= 10)
		map.setZoom(10);
	if(radius > 10 && radius <=100)
		map.setZoom(8);
	if(radius > 100)
		map.setZoom(5);
	radius = radius *1609;
	var circle = new H.map.Circle({lat: latitude, lng: longitude,},radius,
			{style:{fillColor:'rgba('+Math.floor((Math.random()*255))+
                ','+Math.floor((Math.random()*255))+
                ','+Math.floor((Math.random()*255))+
                ',0.3)'}
                });
	map.addObject(circle);
	
   }

  
</script>
 <!------------------ Visualization Ends Here ------------------>

</body>
</html>