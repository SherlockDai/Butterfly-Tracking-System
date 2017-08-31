<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="nixyteam.UserBean"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
<!-------------------------Library Ends Here------------------------------>
<style>
.rating {
    float:left;
}

/* :not(:checked) is a filter, so that browsers that don’t support :checked don’t 
   follow these rules. Every browser that supports :checked also supports :not(), so
   it doesn’t make the test unnecessarily selective */
.rating:not(:checked) > input {
    position:absolute;
    top:-9999px;
    clip:rect(0,0,0,0);
}

.rating:not(:checked) > label {
    float:right;
    width:1em;
    padding:0 .1em;
    overflow:hidden;
    white-space:nowrap;
    cursor:pointer;
    font-size:200%;
    line-height:1.2;
    color:#ddd;
    text-shadow:1px 1px #bbb, 2px 2px #666, .1em .1em .2em rgba(0,0,0,.5);
}


.rating > input:checked ~ label {
    color: #f70;
    text-shadow:1px 1px #c60, 2px 2px #940, .1em .1em .2em rgba(0,0,0,.5);
}

.rating:not(:checked) > label:hover,
.rating:not(:checked) > label:hover ~ label {
    color: gold;
    text-shadow:1px 1px goldenrod, 2px 2px #B57340, .1em .1em .2em rgba(0,0,0,.5);
}

.rating > input:checked + label:hover,
.rating > input:checked + label:hover ~ label,
.rating > input:checked ~ label:hover,
.rating > input:checked ~ label:hover ~ label,
.rating > label:hover ~ input:checked ~ label {
    color: #ea0;
    text-shadow:1px 1px goldenrod, 2px 2px #B57340, .1em .1em .2em rgba(0,0,0,.5);
}

.rating > label:active {
    position:relative;
    top:2px;
    left:2px;
}
</style>
<title>Submit Feedback</title>
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
       <a class="navbar-brand disabled">Butterfly Tracking System | Feedback | N.I.X.Y.</a>
     </div>
     <div id="navbar" class="navbar-collapse collapse">
       <ul class="nav navbar-nav navbar-right">
         <li><% if (session.getAttribute("user") != null) {%>
         	 <a href="">Hello <%= user.getFirstName() %></a>
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
 <br><br><br><br>
<form action="submitfeedback" method="post" class="form-horizontal col-md-6 col-md-offset-3">
	<div class="form-group">
	<label class="col-sm-3 control-label">Title</label>
    <div class="col-sm-4">
    <input type="text" class="form-control" name="title" placeholder="Title" required>
    </div>
  	</div>
  	<div class="form-group">
	<label class="col-sm-3 control-label">Comments</label>
    <div class="col-sm-7">
    <textarea type="text" rows="4" class="form-control" maxlength="400" name="comment" placeholder="Enter your comments here" required maxlength="100"></textarea>
    </div>
  	</div>
  	<div class="form-group">
	<label class="col-sm-3 control-label">Rating</label>
	<div class="col-sm-7 rating">
	<fieldset class="rating">
	<input type="radio" id="star5" name="rating" value="5" /><label for="star5" title="Rocks!">&hearts;</label>
    <input type="radio" id="star4" name="rating" value="4" /><label for="star4" title="Pretty good">&hearts;</label>
    <input type="radio" id="star3" name="rating" value="3" /><label for="star3" title="Meh">&hearts;</label>
    <input type="radio" id="star2" name="rating" value="2" /><label for="star2" title="Kinda bad">&hearts;</label>
    <input type="radio" id="star1" name="rating" value="1" /><label for="star1" title="Sucks big time">&hearts;</label>
	</fieldset>
	</div>
  	</div>
  	<div class="form-group">
    <div class="col-sm-offset-3 col-sm-9">
    <button type="submit" class="btn btn-primary">Submit</button>
    </div>
  	</div>
</form>

</body>
</html>