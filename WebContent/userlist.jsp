<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
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
<link href="css/dashboard.css" rel="stylesheet" type="text/css">
<style>
    html,body { height:100%; width:100%; margin:0px; padding:0px;}
    div#MapContainer { height:100%; width:100%; position:absolute }
	p {margin: 0 0 0px;}
	 .jumbotron {background-color: #FFFFFF;}
</style>
<title>Tagger/Sighter Information</title>
</head>
<body>

<% if (session == null || session.getAttribute("user") == null || !((UserBean)session.getAttribute("user")).isAdministrator()) { %>
	<font color="red">Only administrators can see the list of users!</font><br>
	<jsp:include page="index.jsp"></jsp:include>
<% return; } else if (session.getAttribute("users") == null) {%>
		<jsp:forward page="userlist"></jsp:forward>
<% } %>
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
       <a class="navbar-brand disabled">Butterfly Tracking System | User List | N.I.X.Y.</a>
     </div>
     <div id="navbar" class="navbar-collapse collapse">
       <ul class="nav navbar-nav navbar-right">
         <li><a class="disabled">Hello <%= user.getFirstName() %></a></li>
         <li><a href="account.jsp">Profile</a></li>
         <li><a href="feedback.jsp">Feedback</a></li>
         <li><a href="dashboard.jsp">DashBoard</a></li>
       </ul>
     </div>
   </div>
 </nav>
 <div class="jumbotron">
	<div id="userlistTableContainer">
	<table id="userlistTable">
	<tr>
		<th>User ID</th>
		<th>Email</th>
		<th>First Name</th>
		<th>Last Name</th>
		<th>Street Address</th>
		<th>City</th>
		<th>State</th>
		<th>Zip Code</th>
		<th>Country</th>
		<th>Phone Number</th>
		<th>Administrator</th>
		<th>Account Disabled</th>
	</TR>
	<%! @SuppressWarnings("unchecked") %>
	<%for (UserBean users: (ArrayList<UserBean>)session.getAttribute("users")) { %>
	<tr>
		<td align="center"><%=users.getUserID()%></td>
		<td align="center"><%=users.getEmail()%></td>
		<td align="center"><%=users.getFirstName()%></td>
		<td align="center"><%=users.getLastName()%></td>
		<td align="center"><%=users.getAddress()%></td>
		<td align="center"><%=users.getUserCity()%></td>
		<td align="center"><%=users.getUserState()%></td>
		<td align="center"><%=users.getZipCode()%></td>
		<td align="center"><%=users.getUserCountry()%></td>
		<td align="center"><%=users.getPhone()%></td>
		<td align="center"><%=users.isAdministrator()%></td>
		<td align="center"><%=users.isDisabled()%></td>
	</tr>
	<% } %>
 	</table>
 	</div>
 	<div id="userlistTablePager"></div>
 </div>

<!------------------ Table UI Starts Here ------------------>
<script>
	var datatable = new webix.ui({
		view: "datatable",
		container: "userlistTableContainer",
		autoheight: true,
		autowidth: true,
        drag:true,
        dragColumn: true,
		pager:{
	        template:"{common.prev()} {common.pages()} {common.next()}",
	        container:"userlistTablePager",
	        fixedRowHeight: false,
	        select: "row",
	        size:10,
	        group:5
	    }
	});
	datatable.parse("userlistTable", "htmltable");
	var i = 0;
	while(datatable.getColumnConfig("data"+i)){
		datatable.adjustColumn("data"+i,"all");
		i++;
	}
	
</script>
<!------------------ Table UI Ends Here -------------------->
 
</body>
</html>