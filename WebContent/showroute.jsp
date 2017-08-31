<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="nixyteam.LocationBean"  %>
<%@ page import="nixyteam.UserBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!------------------- Bootstrap Starts Here ------------------------>
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<!------------------- Bootstrap Ends Here -------------------------->
<!-------------------------WEBIX API Starts Here--------------------------->
<script src="webix/codebase/webix.js"></script>   
<link href="webix/codebase/skins/material.css" rel="stylesheet" type="text/css">
<link href="css/dashboard.css" rel="stylesheet" type="text/css">
<!-------------------------WEBIX API Ends Here--------------------------->
<title>Detailed Sighting Information</title>
<style>
    html,body { height:100%; width:100%; overflow:hidden; margin:0px; padding:0px;}
    div#MapContainer { height:100%; width:100%; position:absolute }
	p {margin: 0 0 0px;}
	 .jumbotron {background-color: #FFFFFF;}
</style>
</head>
<body>

<% if (session == null || session.getAttribute("locations") == null) { %>
	<jsp:forward page="butterflyroute.jsp"></jsp:forward>
<% return; } %>
<% UserBean user = (UserBean)session.getAttribute("user");%>
<nav class="navbar navbar-inverse navbar-fixed-top">
   <div class="container-fluid">
     <div class="navbar-header">
       <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
         <span class="sr-only">Toggle navigation</span>
         <span class="icon-bar"></span>
         <span class="icon-bar"></span>
         <span class="icon-bar"></span>
       </button>
       <a class="navbar-brand disabled">Butterfly Tracking System | Butterfly Route | N.I.X.Y.</a>
     </div>
     <div class="navbar-collapse collapse">
       <ul class="nav navbar-nav navbar-right">
         <li><a class="disabled">Hello <%= user.getFirstName() %></a></li>
         <li><a href="account.jsp">Profile</a></li>
         <li><a href="feedback.jsp">Feedback</a></li>
         <li><a href="butterflyroute.jsp">ButterflyRoute</a></li>
         <li><a href="dashboard.jsp">DashBoard</a></li>
       </ul>
     </div>
   </div>
 </nav>
<div class="jumbotron">
	<div id="routeTableContainer" class="container">
		<table id="routeTable">
		<tr>
			<th>Date</th>
			<th>Location</th>
			<th>State</th>
			<th>Country</th>
			<th>Latitude</th>
			<th>Longitude</th>
		</tr>
		<%! @SuppressWarnings("unchecked") %>
		<%
			for (LocationBean location: (ArrayList<LocationBean>)session.getAttribute("locations")) {
		%>
		<tr>
			<td align="center"><%=location.getDate()%></td>
			<td align="center"><%=location.getLocation()%></td>
			<td align="center"><%=location.getState()%></td>
			<td align="center"><%=location.getCountry()%></td>
			<td align="center"><%=location.getLatitude()%></td>
			<td align="center"><%=location.getLongitude()%></td>
		</tr>
		<% } %>
		 </table> 
		</div>
 	<div id="routeTablePager" class="container"></div>
  </div>
 <!------------------ Table UI Starts Here ------------------>
	<script>
		var datatable = new webix.ui({
			view: "datatable",
			container: "routeTableContainer",
			autoheight: true,
			autowidth: true,
			minWidth: 1000,
			pager:{
		        template:"{common.prev()} {common.pages()} {common.next()}",
		        container:"routeTablePager",
		        size:4,
		        group:5
		    }
		});
		datatable.parse("routeTable", "htmltable");
	</script>
<!------------------ Table UI Ends Here -------------------->
</body>
</html>