<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="nixyteam.UserBean"  %>
<%@ page import="nixyteam.FeedbackBean"  %>
<!DOCTYPE html">
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
<link href="css/dashboard.css" rel="stylesheet" type="text/css">
<style>
    html,body { height:100%; width:100%; margin:0px; padding:0px;}
    div#MapContainer { height:100%; width:100%; position:absolute }
	p {margin: 0 0 0px;}
	 .jumbotron {background-color: #FFFFFF;}
</style>
<title>View Feedback</title>
</head>
<body>

<% if (session == null || session.getAttribute("feedbacks") == null) { %>
	<jsp:forward page="feedbacks"></jsp:forward>
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
       <a class="navbar-brand disabled">Butterfly Tracking System | View Feedback | N.I.X.Y.</a>
     </div>
     <div id="navbar" class="navbar-collapse collapse">
       <ul class="nav navbar-nav navbar-right">
         <li><% if (session.getAttribute("user") != null) {%>
         	 <a class="disabled">Hello <%= user.getFirstName() %></a>
         	 <% }%>
         	 <% if (session.getAttribute("user") == null){%>
         	 <a href="">Hello Guest</a>
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
		<div id="feedbackTableContainer">
		<table id="feedbackTable">
	<tr>
		<th>Feedback Date/Time</th>
		<th>User Name</th>
		<th>Title</th>
		<th>Rating</th>
		<th>Comment</th>
	</TR>
	<%! @SuppressWarnings("unchecked") %>
	<%for (FeedbackBean feedback: (ArrayList<FeedbackBean>)session.getAttribute("feedbacks")) { %>
	<tr>
		<td align="center"><%=feedback.getDateTime()%></td>
		<td align="center"><%=feedback.getUserName()%></td>
		<td align="center"><%=feedback.getTitle()%></td>
		<td align="center"><%=feedback.getRating()%></td>
		<td align="center" style="max-width:400"><%=feedback.getComment()%></td>
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
		container: "feedbackTableContainer",
		autoheight: true,
		autowidth: true,
		pager:{
	        template:"{common.prev()} {common.pages()} {common.next()}",
	        container:"detailSightingsTablePager",
	        fixedRowHeight: false,
	        select: "row",
	        size:10,
	        group:5
	    }
	});
	datatable.parse("feedbackTable", "htmltable");
	var i = 0;
	while(datatable.getColumnConfig("data"+i)){
		datatable.adjustColumn("data"+i,"all");
		i++;
	}
	
</script>
<!------------------ Table UI Ends Here -------------------->
</body>
</html>