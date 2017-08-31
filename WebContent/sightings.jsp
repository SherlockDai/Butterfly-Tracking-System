<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="nixyteam.ButterflyBean" %> 
<%@ page import="nixyteam.UserBean"  %>
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
<style>
    html,body { height:100%; width:100%; overflow:hidden; margin:0px; padding:0px;}
    div#MapContainer { height:100%; width:100%; position:absolute }
	p {margin: 0 0 0px;}
	 .jumbotron {background-color: #FFFFFF;
    			padding-bottom: 0}
</style>

<title>Sighting Information</title>
</head>
<body>

<% if (session == null || session.getAttribute("sightings") == null) { %>
	<jsp:forward page="sightings"></jsp:forward>
<% return; } %>

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
<div class="jumbotron">
	<div id="sightingTableContainer" class="container">
		<table id="sightingsTable">
		<tr>
			<th>Sighting_ID</th>
			<th>Tag_ID</th>
			<th>Sighter_ID</th>
			<th>Date</th>
			<th>Location</th>
			<th>State</th>
			<th>Country</th>
			<th>Latitude</th>
			<th>Longitude</th>
		</TR>
		<%! @SuppressWarnings("unchecked") %>
		<%
			for (ButterflyBean sighting: (ArrayList<ButterflyBean>)session.getAttribute("sightings")) {
		%>
		<tr>
			<td align="center"><%=sighting.getSightID()%></td>
			<td align="center"><%=sighting.getTagID()%></td>
			<td align="center"><%=sighting.getUserID()%></td>
			<td align="center"><%=sighting.getDate()%></td>
			<td align="center"><%=sighting.getButterflyLocation()%></td>
			<td align="center"><%=sighting.getButterflyState()%></td>
			<td align="center"><%=sighting.getButterflyCountry()%></td>
			<td align="center"><%=sighting.getLatitude()%></td>
			<td align="center"><%=sighting.getLongitude()%></td>	
		</tr>
		<% } %>
		</table>
	 </div>
	 <div id="sightingsTablePager" class="container"></div>
</div>
 <div id="MapContainer"></div>
 
<!------------------ Table UI Starts Here ------------------>
<script>
	var datatable = new webix.ui({
		view: "datatable",
		container: "sightingTableContainer",
		autoheight: true,
		autowidth: true,
		minWidth: 1000,
		pager:{
	        template:"{common.prev()} {common.pages()} {common.next()}",
	        container:"sightingsTablePager",
	        size:6,
	        group:5
	    }
	});
	datatable.parse("sightingsTable", "htmltable");
	var i = 0;
	while(datatable.getColumnConfig("data"+i)){
		datatable.adjustColumn("data"+i,"all");
		i++;
	}
</script>
<!------------------ Table UI Ends Here -------------------->

<!------------------ Visualization Starts Here ------------------>
<script> 	
var sightings = new Array();
<%
for (ButterflyBean sighting: (ArrayList<ButterflyBean>)session.getAttribute("sightings")) {
%>
sightings.push([<%=sighting.getLatitude()%>,<%=sighting.getLongitude()%>,<%=sighting.getSightID()%>,<%=sighting.getTagID()%>]);
<%}%>
var platform = new H.service.Platform({
    'app_id': 'M3dZEgEAPQJqXonN1m3e',
    'app_code': 'keqdbv92h0m9rZeA0J0HwQ'
});
var maptypes = platform.createDefaultLayers();
var zoom = 3;
var centerPoint = new H.geo.Point(20, -101)
   // Instantiate (and display) a map object:
var map = new H.Map(           
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
	var marker = new H.map.Marker(new H.geo.Point(sightings[i][0],sightings[i][1]),{zIndex : sightings[i][2]});
	marker.addEventListener("tap", onTapMarker);
	group.addObject(marker);
	
	//listner is here
function onTapMarker(Event){
     var lat = Event.target.getPosition().lat;
     var lng = Event.target.getPosition().lng;
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
             alert("Sighting ID is :" + Event.target.getZIndex());
             alert("Weather is :" + data.observations.location[0].observation[0].description);
             alert("Temperature is :"+ data.observations.location[0].observation[0].temperature + " °C");
         }
     });

    }
   }
</script>
 <!------------------ Visualization Ends Here ------------------>

</body>
</html>