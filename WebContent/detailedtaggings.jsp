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
<title>Detailed Tagging Information</title>
<style>
    html,body { height:100%; width:100%; margin:0px; padding:0px;}
    div#MapContainer { height:100%; width:100%; position:absolute }
	p {margin: 0 0 0px;}
	 .jumbotron {background-color: #FFFFFF;}
</style>
</head>
<body>

<% if (session == null || session.getAttribute("user") == null) { %>
	<font color="red">You need to be logged in to see detailed butterfly taggings!</font><br>
	<jsp:include page="index.jsp"></jsp:include>
<% 	return; 
	}
	else if (session.getAttribute("detailedtaggings") == null) { %>
		<jsp:forward page="detailedtaggings"></jsp:forward>
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
		      <a class="navbar-brand disabled">Butterfly Tracking System | Detailed Taggings | N.I.X.Y.</a>
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
		<div id="detailTaggingTableContainer">
			<table id="detailTaggingsTable">
				<tr>
					<th>Butterfly_Tag_ID</th>
					<th>Tagger_ID</th>
					<th>Date_Tagged</th>
					<th>Location</th>
					<th>State</th>
					<th>Country</th>
					<th>Latitude</th>
					<th>Longitude</th>
					<th>Species</th>
					<th>Tagger_Email</th>
					<th>First_Name</th>	
					<th>Last_Name</th>
					<th>Address</th>
					<th>City</th>
					<th>State</th>
					<th>ZipCode</th>
					<th>Country</th>
					<th>Phone</th>
				</tr>
				<%! @SuppressWarnings("unchecked") %>
				<%
					for (ButterflyBean detailedTagging: (ArrayList<ButterflyBean>)session.getAttribute("detailedtaggings")) {
				%>
				<tr>
					<td align="center"><%=detailedTagging.getTagID()%></td>
					<td align="center"><%=detailedTagging.getUserID()%></td>
					<td align="center"><%=detailedTagging.getDate()%></td>
					<td align="center"><%=detailedTagging.getButterflyLocation()%></td>
					<td align="center"><%=detailedTagging.getButterflyState()%></td>
					<td align="center"><%=detailedTagging.getButterflyCountry()%></td>
					<td align="center"><%=detailedTagging.getLatitude()%></td>
					<td align="center"><%=detailedTagging.getLongitude()%></td>	
					<td align="center"><%=detailedTagging.getSpecies()%></td>	
					<td align="center"><%=detailedTagging.getEmail()%></td>	
					<td align="center"><%=detailedTagging.getFirstName()%></td>
					<td align="center"><%=detailedTagging.getLastName()%></td>
					<td align="center"><%=detailedTagging.getAddress()%></td>
					<td align="center"><%=detailedTagging.getUserCity()%></td>
					<td align="center"><%=detailedTagging.getUserState()%></td>	
					<td align="center"><%=detailedTagging.getZipCode()%></td>
					<td align="center"><%=detailedTagging.getUserCountry()%></td>
					<td align="center"><%=detailedTagging.getPhone()%></td>
				</tr>
				<% } %>
		 </table> 
	 </div>
	 <div id="detailTaggingsTablePager"></div>
 </div>
 
<!------------------ Table UI Starts Here ------------------>
<script>
	var datatable = new webix.ui({
		view: "datatable",
		container: "detailTaggingTableContainer",
		autoheight: true,
		autowidth: true,
        drag:true,
        dragColumn: true,
		pager:{
	        template:"{common.prev()} {common.pages()} {common.next()}",
	        container:"detailTaggingsTablePager",
	        fixedRowHeight: false,
	        select: "row",
	        size:20,
	        group:7
	    }
	});
	datatable.parse("detailTaggingsTable", "htmltable");
	var i = 0;
	while(datatable.getColumnConfig("data"+i)){
		datatable.adjustColumn("data"+i,"all");
		i++;
	}
	
</script>
<!------------------ Table UI Ends Here -------------------->

</body>
</html>