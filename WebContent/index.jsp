<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="nixyteam.UserBean"  %>
 <!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>NIXY Team Homepage</title>

<!------------------- Bootstrap Starts Here ------------------------>
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<!------------------- Bootstrap Ends Here -------------------------->

<style>
body {
  	padding-top: 50px;
  	padding-bottom: 20px;
	background: url(img/index_background.jpg);
	background-repeat: no-repeat;
	background-size: cover;
}
div.container_main{
	margin-top: 30%;
}
div.row {
	background-color: #FFFFFF;
	opacity: 0.80;
	margin-bottom: 10px;
}
p.headP {margin: 0 0 0px;}
P.rootP {
	color: #FFFFFF;
}
</style>

</head>

<body>

	<nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand disabled">Butterfly Tracking System | N.I.X.Y.</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
           <ul class="nav navbar-nav navbar-right">
         	<li><a class="disabled">Hello Guest</a></li>
         	<li>
         	<a href="#" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
   			 Download <span class="caret"></span>
  			</a>
  			<ul class="dropdown-menu">
    		<li><a href="downloadbytag.jsp">Download By Tag</a></li>
    		<li><a href="downloadbylocation.jsp">Download By Location </a></li>
    		<li role="separator" class="divider"></li>
    		<li><a href="downloadall.jsp">Download All Tracking Data</a></li>
  			</ul>
  			</li>
         	<li>
         	<a href="feedback.jsp" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
   			 Feedback <span class="caret"></span>
  			</a>
  			<ul class="dropdown-menu">
    		<li><a href="feedback.jsp">Report Feedback</a></li>
    		<li><a href="viewfeedback.jsp">View Feedbacks</a></li>
  			</ul>
  			</li>
         	<li><a href="login.jsp">Login</a></li>
       	   </ul>
    	</div>
      </div>
    </nav>
    <div class="container container_main">
      <!-- Example row of columns -->
      <div class="row">
        <div class="col-md-4">
          <h2>Become a Member</h2>
          <p>Create an account now and take advantage of member only functionalities. Members can report butterfly taggings, sightings, and much more!</p>
          <p><a class="btn btn-default" href="register.jsp" role="button">Register &raquo;</a></p>
        </div>
        <div class="col-md-4">
          <h2>View Butterfly Taggings</h2>
          <p>Butterflies are tagged with a unique ID that can be tracked later. Click on the button below to view all tagged butterfly information! </p>
          <p>
          	<a class="btn btn-default" href="butterflies.jsp" role="button">View details &raquo;</a>
          	<a class="btn btn-default" href="customtaggings.jsp" role="button">Search Tagging <span class="glyphicon glyphicon-search" aria-hidden="true"></span></a>
          	
          </p>
       </div>
        <div class="col-md-4">
          <h2>View Butterfly Sightings</h2>
          <p>When an already tagged butterflies are seen, sightings are reported. Click on the button below to view all sighted butterflies!</p>
          <p>
          	<a class="btn btn-default" href="sightings.jsp" role="button">View details &raquo;</a>
          	<a class="btn btn-default" href="customsightings.jsp" role="button">Search Sighting <span class="glyphicon glyphicon-search" aria-hidden="true"></span></a>
          </p>
        </div>
      </div>

      <hr>

      <footer>
        <p class="rootP">&copy; 2015 N.I.X.Y. TEAM.</p>
      </footer>
    </div>

</body>
</html>