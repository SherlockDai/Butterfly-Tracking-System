<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="nixyteam.UserBean" %>
<!DOCTYPE html>
<html>
<head>
<!------------------------Library Starts Here------------------------------>
<!------------------- Bootstrap Starts Here ------------------------------->
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<!------------------- Bootstrap Ends Here --------------------------------->
<!-------------------------WEBIX API Starts Here--------------------------->
<script src="webix/codebase/webix.js"></script>   
<link href="webix/codebase/skins/material.css" rel="stylesheet" type="text/css">
<!-------------------------WEBIX API Ends Here----------------------------->
<link href="css/dashboard.css" rel="stylesheet" type="text/css">
<!-------------------------Library Ends Here------------------------------->
<title>Custom Sightings</title>
</head>
<body>

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
 <div class="alert alert-info alert-dismissible" role="alert">
  	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  	<strong>Heads Up!</strong> Enter Butterfly Tag ID to view Tagging and all Sighting locations.
</div>
<br>
<form action="butterflyroute" method="post" class="form-horizontal col-md-6 col-md-offset-3">
	
  <div class="form-group">
    <label class="col-sm-3 control-label">Tag Number</label>
    <div class="col-sm-4">
      <input type="number" class="form-control" name="number" min="1" max="999999" placeholder="Tag Number" required>
    </div>
  </div>
  <div class="form-group">
    <div class="col-sm-offset-3 col-sm-9">
      <button type="submit" class="btn btn-primary" onclick="checkDate()">Submit</button>
    </div>
  </div>
</form> <br>

</body>
</html>