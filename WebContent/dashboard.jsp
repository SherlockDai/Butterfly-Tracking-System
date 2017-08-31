<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="nixyteam.ButterflyBean" %> 
<%@ page import="nixyteam.UserBean"  %>
<%@ page import="nixyteam.LocationBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>dashboard</title>

<!------------------------Library Starts Here------------------------------>
<!------------------- Bootstrap Starts Here ------------------------>
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<!------------------- Bootstrap Ends Here ------------------------>
<!-------------------------WEBIX API Starts Here--------------------------->
<script src="webix/codebase/webix.js"></script>   
<link href="webix/codebase/skins/material.css" rel="stylesheet" type="text/css">
<!-------------------------WEBIX API Ends Here--------------------------->
<link href="css/dashboard.css" rel="stylesheet" type="text/css">
<!-------------------------Library Ends Here------------------------------>
<style>
.webix_chart canvas{position:relative}
div#chart {
    height: 20em;
    margin-right: 20;
}
</style>
</head>
<body style="background-color:white" onload="refresh()">
<% if (session == null || session.getAttribute("user") == null) { %>
	<font color="red">You need to be logged in to access the dashboard!</font><br>
	<jsp:include page="index.jsp"></jsp:include>
<% return; }%>
<%! @SuppressWarnings("unchecked") %>


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
       <a class="navbar-brand disabled">Butterfly Tracking System | DashBoard | N.I.X.Y.</a>
     </div>
     <div id="navbar" class="navbar-collapse collapse">
       <ul class="nav navbar-nav navbar-right">
         <li><a class="disabled">Hello <%= user.getFirstName() %></a></li>
         <li><a href="account.jsp">Profile</a></li>
         <li><a href="feedback.jsp">Feedback</a></li>
         <li><a href="logout">Logout</a></li>
       </ul>
     </div>
   </div>
 </nav>

 <div class="container-fluid">
   <div class="row">
     <div class="col-sm-3 col-md-2 sidebar">
       <ul class="nav nav-sidebar">
         <li class="active"><a class="disabled">Dashboard <span class="sr-only">(current)</span></a></li>
         <li><a href="tagbutterfly.jsp">Report Taggings</a></li>
         <li><a href="tags">Report Sightings</a></li>
         <li><a href="customregionsighting.jsp">Custom Region Sighting</a></li>
         <li><a href="sightings">View Sightings</a></li>
         <li><a href="taggings">View Taggings</a></li>
         <li><a href="customsightings.jsp">Search Sightings</a></li>
         <li><a href="customtaggings.jsp">Search Taggings</a></li>
         <li><a href="butterflyroute.jsp">Butterfly Migration Route</a></li>
         <li><a href="detailedsightings">View Detailed Sightings</a></li>
         <li><a href="detailedtaggings">View Detailed Taggings</a></li>
         <li><a href="updatetagging.jsp">Update Tagging</a></li>
         <li><a href="updatesighting.jsp">Update Sighting</a></li>
         <li><a href="account.jsp">My Account</a></li>
         <li><a href="feedback.jsp">Submit Feedback</a></li>
         <li><a href="feedbacks">View User Feedback</a></li>
         <li>
         	<a href="#" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
   			 Download Batch File <span class="caret"></span>
  			</a>
  			<ul class="dropdown-menu">
    		<li><a href="downloadbytag.jsp">Download By Tag</a></li>
    		<li><a href="downloadbylocation.jsp">Download By Location </a></li>
    		<li role="separator" class="divider"></li>
    		<li><a href="downloadall.jsp">Download All Tracking Data</a></li>
  			</ul>
  		</li>
  		<li><a href="uploadbatch.jsp">Upload Batch File</a></li>
       </ul>
       <% if (user.isAdministrator()) { %>
       <ul class="nav nav-sidebar">
         <li><a class="disabled">Administration</a></li>
         <li><a href="updatetagging.jsp">Update or Remove Butterfly Taggings</a></li>
         <li><a href="updatesighting.jsp">Update or Remove Butterfly Sightings</a></li>
         <li><a href="userlist">View All Users</a></li>
         <li><a href="usermanagement.jsp">Edit or Remove User Accounts</a></li>
       </ul>
       <% } %>
     </div>
     <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
       <h1 class="page-header">Dashboard</h1>
		<div class="row placeholders">
			<div id="chart" class="col-xs-12 col-sm-12 placeholder"></div>
		</div>
       <h2 class="sub-header">Relevant Info</h2>
		<div class="row placeholders">
			<div id="calendar" class="col-xs-6 col-sm-4 placeholder"></div>
			<div id="video" class="col-xs-6 col-sm-4 placeholder">
				<iframe width="560" height="315" src="https://www.youtube.com/embed/BlhM8xQmCXk" frameborder="0" allowfullscreen></iframe>
			</div>
			<div class="col-xs-6 col-sm-4 placeholder"></div>
		</div>
     </div>
   </div>
 </div>

<!------------------- Calendar Starts Here ------------------->
<script>
	var calendar = webix.ui({
		view: "calendar",
		container: "calendar",
		height: 315
	})
</script>
<!------------------- Calendar Ends Here --------------------->

<!------------------- Chart Starts Here --------------------->
<script>
	function initiateChart(){
	var taggingStates = new Array();
	var sightingStates = new Array();
	<%
	for (LocationBean locations: (ArrayList<LocationBean>)session.getAttribute("tagginglocations")) {
	%>
	taggingStates.push("<%= locations.getState()%>");
	
	
	<%}%>
	var taggings = new Array();
	var length = 0;
	for(var i = 0; i <taggingStates.length; i++ ){
		if(!taggings.length){
			taggings.push({name: taggingStates[i], value: 1})
		}
		else{
			length = taggings.length;
			for(var j = 0; j < length; j++){
				if(taggings[j].name == taggingStates[i]){
					taggings[j].value ++;
					break;
				}
				if(j == taggings.length-1 && taggings[j].name != taggingStates[i]){
					taggings.push({"name": taggingStates[i], "value": 1})
				}
			}
		}
	}
	var max = 0;
	for(i=0; i<taggings.length; i++){
		if(taggings[i].value >= max){
			max = taggings[i].value
		}
	}
	var steps = max/15;
	var chart = webix.ui({
		view: "chart",
		border: false,
		container: "chart",
		type: "bar",
		borderless: true,
		value: "#value#",
		xAxis:{
			title: "States",
			template: "#name#"
		},
		yAxis:{
			title:"Amount of Taggings",
			start:0,
			end:max,
			step: steps
		},
		label: "#value#",
		tooltip:{
			template:"#value#"
		},
		data: taggings
	})
	chart.adjust();
	}
	function refresh(){
		initiateChart();
	}
</script>
<!------------------- Chart Ends Here ----------------------->

</body>
</html>