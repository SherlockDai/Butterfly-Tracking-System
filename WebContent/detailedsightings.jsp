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
<!-------------------------WEBIX API Starts Here--------------------------->
<script src="webix/codebase/webix.js"></script>   
<link href="webix/codebase/skins/material.css" rel="stylesheet" type="text/css">
<!-------------------------WEBIX API Ends Here--------------------------->
<title>Detailed Sighting Information</title>
<style>
    html,body { height:100%; width:100%; margin:0px; padding:0px;}
    div#MapContainer { height:100%; width:100%; position:absolute }
	p {margin: 0 0 0px;}
	 .jumbotron {background-color: #FFFFFF;}
</style>
</head>
<body>

<% if (session == null || session.getAttribute("user") == null) { %>
	<font color="red">You need to be logged in to see detailed butterfly sightings!</font><br>
	<jsp:include page="index.jsp"></jsp:include>
<% 	return; 
	}
	else if (session.getAttribute("detailedsightings") == null) { %>
		<jsp:forward page="detailedsightings"></jsp:forward>
<% } %>
<% UserBean user = (UserBean)session.getAttribute("user");%>
	<nav class="navbar navbar-inverse navbar-fixed-top">
	    <div class="container">
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
	        <span class="sr-only">Toggle navigation</span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	      </button>
	      <a class="navbar-brand disabled">Butterfly Tracking System | Detailed Sightings | N.I.X.Y.</a>
	    </div>
	    <div id="navbar" class="navbar-collapse collapse">
	          <ul class="nav navbar-nav navbar-right">
         <li><% if (session.getAttribute("user") != null) {%>
         	 <a class="disabled">Hello <%= user.getFirstName() %></a>
         	 <% }%>
         	 <% if (session.getAttribute("user") == null){%>
         	 <a class="disabled">Hello Guest</a>
         	 <% } %>
         </li>
         <li><a href="account.jsp">Profile</a></li>
         <li><a href="feedback.jsp">Feedback</a></li>
         <li>
         <% if (session.getAttribute("user") == null) { %>	           
		 <a href="index.jsp">HomePage</a>
		 <% } else { %>
		 <a href="dashboard.jsp">DashBoard</a>
		 <% } %>
         </li>
       </ul>
	    </div>
	    </div>
	</nav>
	<div class="jumbotron">
		<div id="detailSightingTableContainer">
			<table id="detailSightingsTable">
			<tr>
				<th>Sighting_ID</th>
				<th>Butterfly_Tag_ID</th>
				<th>Sighter_ID</th>
				<th>Date_Sighted</th>
				<th>Location</th>
				<th>State</th>
				<th>Country</th>
				<th>Latitude</th>
				<th>Longitude</th>
				<th>Sigther_Email</th>
				<th>First_Name</th>	
				<th>Last_Name</th>
				<th>Address</th>
				<th>City</th>
				<th>State</th>
				<th>Zip_Code</th>
				<th>Country</th>
				<th>Phone</th>
			</tr>
			<%! @SuppressWarnings("unchecked") %>
			<%
				for (ButterflyBean detailedSighting: (ArrayList<ButterflyBean>)session.getAttribute("detailedsightings")) {
			%>
			<tr>
				<td align="center"><%=detailedSighting.getSightID()%></td>
				<td align="center"><%=detailedSighting.getTagID()%></td>
				<td align="center"><%=detailedSighting.getUserID()%></td>
				<td align="center"><%=detailedSighting.getDate()%></td>
				<td align="center"><%=detailedSighting.getButterflyLocation()%></td>
				<td align="center"><%=detailedSighting.getButterflyState()%></td>
				<td align="center"><%=detailedSighting.getButterflyCountry()%></td>
				<td align="center"><%=detailedSighting.getLatitude()%></td>
				<td align="center"><%=detailedSighting.getLongitude()%></td>	
				<td align="center"><%=detailedSighting.getEmail()%></td>	
				<td align="center"><%=detailedSighting.getFirstName()%></td>
				<td align="center"><%=detailedSighting.getLastName()%></td>
				<td align="center"><%=detailedSighting.getAddress()%></td>
				<td align="center"><%=detailedSighting.getUserCity()%></td>
				<td align="center"><%=detailedSighting.getUserState()%></td>	
				<td align="center"><%=detailedSighting.getZipCode()%></td>
				<td align="center"><%=detailedSighting.getUserCountry()%></td>
				<td align="center"><%=detailedSighting.getPhone()%></td>
			</tr>
			<% } %>
			 </table>
		 </div>
		 <div id="detailSightingsTablePager"></div>
	</div>
<!------------------ Table UI Starts Here ------------------>
<script>
	var datatable = new webix.ui({
		view: "datatable",
		container: "detailSightingTableContainer",
		autoheight: true,
		autowidth: true,
        drag:true,
        dragColumn: true,
		pager:{
	        template:"{common.prev()} {common.pages()} {common.next()}",
	        container:"detailSightingsTablePager",
	        fixedRowHeight: false,
	        select: "row",
	        size:20,
	        group:7,
	        dragColumn: true
	    }
	});
	datatable.parse("detailSightingsTable", "htmltable");
	var i = 0;
	while(datatable.getColumnConfig("data"+i)){
		datatable.adjustColumn("data"+i,"all");
		i++;
	}
	
</script>
<!------------------ Table UI Ends Here -------------------->

</body>
</html>