<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="nixyteam.UserBean"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
<link href="css/dashboard.css" rel="stylesheet" type="text/css">
<!-------------------------Library Ends Here------------------------------>
<style>
.alert{margin-bottom: 0}
</style>
<title>User Management</title>
</head>
<body>

<% if (session == null || session.getAttribute("user") == null || !((UserBean)session.getAttribute("user")).isAdministrator()) { %>
	<font color="red">Only administrators can access this page!</font><br>
	<jsp:include page="index.jsp"></jsp:include>
<% return; } %>
<% UserBean user = (UserBean)session.getAttribute("user"); %>
<nav class="navbar navbar-inverse navbar-fixed-top">
   <div class="container-fluid">
     <div class="navbar-header">
       <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
         <span class="sr-only">Toggle navigation</span>
         <span class="icon-bar"></span>
         <span class="icon-bar"></span>
         <span class="icon-bar"></span>
       </button>
       <a class="navbar-brand" href="dashboard.jsp">Butterfly Tracking System | User Management | N.I.X.Y.</a>
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
         <li><a data-toggle="collapse" href="#collapseDiv" aria-expanded="false" aria-controls="collapseDiv">Remove User</a></li>
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
<div class="collapse" id="collapseDiv">
<div class="alert alert-info alert-dismissible" role="alert">
  	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  	<strong>Heads Up!</strong> Use the following form to remove user accounts
</div>
<div class="alert alert-warning alert-dismissible" role="alert">
  	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  	<strong>Warning!</strong> Removing a user account will also permanently remove all butterfly sightings and taggings posted by the user! To keep all records disable the user instead
</div>
<br>
<form action="deleteaccount" method="post" class="form-horizontal col-md-6 col-md-offset-3">
	<div class="form-group">
	<label class="col-sm-3 control-label">User ID</label>
    <div class="col-sm-4">
    <input type="number" id="number1" class="form-control" name="number" min="1" max="999999" placeholder="User ID"  >
    </div>
  	</div>
  	<div class="form-group">
	<label class="col-sm-3 control-label">Reenter ID</label>
    <div class="col-sm-4">
    <input type="number" class="form-control" id="number2" name="number" min="1" max="999999" placeholder="Reenter ID"  >
    </div>
  	</div>
  	<div class="form-group">
    <div class="col-sm-offset-3 col-sm-9">
     <button type="submit" class="btn btn-primary">Remove User</button>
    </div>
  	</div>
</form>
<br><br><br><br><br><br><br><br>
</div>
<div>
<div class="alert alert-info alert-dismissible" role="alert">
  	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  	<strong>Heads Up!</strong> Click Remove User in Navigation to show the Remove User Form 
</div>
<div class="alert alert-warning alert-dismissible" role="alert">
  	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  	<strong>Warning!</strong> Use the following form to update any user account. User ID is required, and only non-empty fields are updated
</div>
<br>
<form action="updateuser" method="post" class="form-horizontal col-md-10 col-md-offset-1">
  	<div class="form-group">
	<label class="col-sm-2 control-label">User ID</label>
    <div class="col-sm-4">
      <input type="number" class="form-control" id="ID1" name="number" min="1" max="999999"  placeholder="User ID"  required>
    </div>
    <label class="col-sm-2 control-label">Reenter ID</label>
    <div class="col-sm-4">
    <input type="number" class="form-control" id="ID2" name="number" min="1" max="999999"  placeholder="Reenter ID"  required>
    </div>
  	</div>
  	<div class="form-group">
	<label class="col-sm-2 control-label">Email</label>
    <div class="col-sm-4">
      <input type="email" class="form-control" id="email1" name="email" autocomplete="off" placeholder="Email"  maxlength="30" >
    </div>
    <label class="col-sm-2 control-label">Reenter Email</label>
    <div class="col-sm-4">
    <input type="email" class="form-control" id="email2" name="email" autocomplete="off" placeholder="Email"  maxlength="30">
    </div>
  	</div>
  	<div class="form-group">
	<label class="col-sm-2 control-label">Password</label>
    <div class="col-sm-4">
      <input type="password" class="form-control" id="pass1" name="password" autocomplete="off" pattern=".{0}|.{8,40}" title="minimum 8 characters" placeholder="Password"  >
    </div>
    <label class="col-sm-2 control-label">ReenterPassword</label>
    <div class="col-sm-4">
      <input type="password" class="form-control" id="pass2" name="password2" autocomplete="off" pattern=".{0}|.{8,40}" title="minimum 8 characters" placeholder="Password"  >
    </div>
  	</div>
  	<div class="form-group">
	<label class="col-sm-2 control-label">First Name</label>
    <div class="col-sm-4">
    <input type="text" class="form-control" name="firstName" autocomplete="off"  placeholder="FirstName"  maxlength="30">
    </div>
    <label class="col-sm-2 control-label">Last Name</label>
    <div class="col-sm-4">
    <input type="text" class="form-control" name="lastName" autocomplete="off"  placeholder="LasttName"  maxlength="30">
    </div>
    </div>
  	<div class="form-group">
	<label class="col-sm-2 control-label">Street Address</label>
    <div class="col-sm-4">
    <input type="text" class="form-control" name="streetAddress" autocomplete="off"  placeholder="Street Address"  maxlength="30">
    </div>
    <label class="col-sm-2 control-label">City</label>
    <div class="col-sm-4">
    <input type="text" class="form-control" name="city" autocomplete="off"  placeholder="City"  maxlength="30">
    </div>
  	</div>
  	<div class="form-group">
	<label class="col-sm-2 control-label">Country</label>
    <div class="col-sm-4">
    <select class="form-control" name="country" autocomplete="off"  placeholder="Country" onchange="onSelectCountry(this)" required>
    <option value="">Select Your Country</option>
    <option value="Afghanistan">Afghanistan</option>
    <option value="Albania">Albania</option>
    <option value="Algeria">Algeria</option>
    <option value="American Samoa">American Samoa</option>
    <option value="Andorra">Andorra</option>
    <option value="Angola">Angola</option>
    <option value="Anguilla">Anguilla</option>
    <option value="Antartica">Antarctica</option>
    <option value="Antigua and Barbuda">Antigua and Barbuda</option>
    <option value="Argentina">Argentina</option>
    <option value="Armenia">Armenia</option>
    <option value="Aruba">Aruba</option>
    <option value="Australia">Australia</option>
    <option value="Austria">Austria</option>
    <option value="Azerbaijan">Azerbaijan</option>
    <option value="Bahamas">Bahamas</option>
    <option value="Bahrain">Bahrain</option>
    <option value="Bangladesh">Bangladesh</option>
    <option value="Barbados">Barbados</option>
    <option value="Belarus">Belarus</option>
    <option value="Belgium">Belgium</option>
    <option value="Belize">Belize</option>
    <option value="Benin">Benin</option>
    <option value="Bermuda">Bermuda</option>
    <option value="Bhutan">Bhutan</option>
    <option value="Bolivia">Bolivia</option>
    <option value="Bosnia and Herzegowina">Bosnia and Herzegowina</option>
    <option value="Botswana">Botswana</option>
    <option value="Bouvet Island">Bouvet Island</option>
    <option value="Brazil">Brazil</option>
    <option value="British Indian Ocean Territory">British Indian Ocean Territory</option>
    <option value="Brunei Darussalam">Brunei Darussalam</option>
    <option value="Bulgaria">Bulgaria</option>
    <option value="Burkina Faso">Burkina Faso</option>
    <option value="Burundi">Burundi</option>
    <option value="Cambodia">Cambodia</option>
    <option value="Cameroon">Cameroon</option>
    <option value="Canada">Canada</option>
    <option value="Cape Verde">Cape Verde</option>
    <option value="Cayman Islands">Cayman Islands</option>
    <option value="Central African Republic">Central African Republic</option>
    <option value="Chad">Chad</option>
    <option value="Chile">Chile</option>
    <option value="China">China</option>
    <option value="Christmas Island">Christmas Island</option>
    <option value="Cocos Islands">Cocos (Keeling) Islands</option>
    <option value="Colombia">Colombia</option>
    <option value="Comoros">Comoros</option>
    <option value="Congo">Congo</option>
    <option value="Congo">Congo, the Democratic Republic of the</option>
    <option value="Cook Islands">Cook Islands</option>
    <option value="Costa Rica">Costa Rica</option>
    <option value="Cota D'Ivoire">Cote d'Ivoire</option>
    <option value="Croatia">Croatia (Hrvatska)</option>
    <option value="Cuba">Cuba</option>
    <option value="Cyprus">Cyprus</option>
    <option value="Czech Republic">Czech Republic</option>
    <option value="Denmark">Denmark</option>
    <option value="Djibouti">Djibouti</option>
    <option value="Dominica">Dominica</option>
    <option value="Dominican Republic">Dominican Republic</option>
    <option value="East Timor">East Timor</option>
    <option value="Ecuador">Ecuador</option>
    <option value="Egypt">Egypt</option>
    <option value="El Salvador">El Salvador</option>
    <option value="Equatorial Guinea">Equatorial Guinea</option>
    <option value="Eritrea">Eritrea</option>
    <option value="Estonia">Estonia</option>
    <option value="Ethiopia">Ethiopia</option>
    <option value="Falkland Islands">Falkland Islands (Malvinas)</option>
    <option value="Faroe Islands">Faroe Islands</option>
    <option value="Fiji">Fiji</option>
    <option value="Finland">Finland</option>
    <option value="France">France</option>
    <option value="France Metropolitan">France, Metropolitan</option>
    <option value="French Guiana">French Guiana</option>
    <option value="French Polynesia">French Polynesia</option>
    <option value="French Southern Territories">French Southern Territories</option>
    <option value="Gabon">Gabon</option>
    <option value="Gambia">Gambia</option>
    <option value="Georgia">Georgia</option>
    <option value="Germany">Germany</option>
    <option value="Ghana">Ghana</option>
    <option value="Gibraltar">Gibraltar</option>
    <option value="Greece">Greece</option>
    <option value="Greenland">Greenland</option>
    <option value="Grenada">Grenada</option>
    <option value="Guadeloupe">Guadeloupe</option>
    <option value="Guam">Guam</option>
    <option value="Guatemala">Guatemala</option>
    <option value="Guinea">Guinea</option>
    <option value="Guinea-Bissau">Guinea-Bissau</option>
    <option value="Guyana">Guyana</option>
    <option value="Haiti">Haiti</option>
    <option value="Heard and McDonald Islands">Heard and Mc Donald Islands</option>
    <option value="Holy See">Holy See (Vatican City State)</option>
    <option value="Honduras">Honduras</option>
    <option value="Hong Kong">Hong Kong</option>
    <option value="Hungary">Hungary</option>
    <option value="Iceland">Iceland</option>
    <option value="India">India</option>
    <option value="Indonesia">Indonesia</option>
    <option value="Iran">Iran (Islamic Republic of)</option>
    <option value="Iraq">Iraq</option>
    <option value="Ireland">Ireland</option>
    <option value="Israel">Israel</option>
    <option value="Italy">Italy</option>
    <option value="Jamaica">Jamaica</option>
    <option value="Japan">Japan</option>
    <option value="Jordan">Jordan</option>
    <option value="Kazakhstan">Kazakhstan</option>
    <option value="Kenya">Kenya</option>
    <option value="Kiribati">Kiribati</option>
    <option value="Democratic People's Republic of Korea">Korea, Democratic People's Republic of</option>
    <option value="Korea">Korea, Republic of</option>
    <option value="Kuwait">Kuwait</option>
    <option value="Kyrgyzstan">Kyrgyzstan</option>
    <option value="Lao">Lao People's Democratic Republic</option>
    <option value="Latvia">Latvia</option>
    <option value="Lebanon">Lebanon</option>
    <option value="Lesotho">Lesotho</option>
    <option value="Liberia">Liberia</option>
    <option value="Libyan Arab Jamahiriya">Libyan Arab Jamahiriya</option>
    <option value="Liechtenstein">Liechtenstein</option>
    <option value="Lithuania">Lithuania</option>
    <option value="Luxembourg">Luxembourg</option>
    <option value="Macau">Macau</option>
    <option value="Macedonia">Macedonia, The Former Yugoslav Republic of</option>
    <option value="Madagascar">Madagascar</option>
    <option value="Malawi">Malawi</option>
    <option value="Malaysia">Malaysia</option>
    <option value="Maldives">Maldives</option>
    <option value="Mali">Mali</option>
    <option value="Malta">Malta</option>
    <option value="Marshall Islands">Marshall Islands</option>
    <option value="Martinique">Martinique</option>
    <option value="Mauritania">Mauritania</option>
    <option value="Mauritius">Mauritius</option>
    <option value="Mayotte">Mayotte</option>
    <option value="Mexico">Mexico</option>
    <option value="Micronesia">Micronesia, Federated States of</option>
    <option value="Moldova">Moldova, Republic of</option>
    <option value="Monaco">Monaco</option>
    <option value="Mongolia">Mongolia</option>
    <option value="Montserrat">Montserrat</option>
    <option value="Morocco">Morocco</option>
    <option value="Mozambique">Mozambique</option>
    <option value="Myanmar">Myanmar</option>
    <option value="Namibia">Namibia</option>
    <option value="Nauru">Nauru</option>
    <option value="Nepal">Nepal</option>
    <option value="Netherlands">Netherlands</option>
    <option value="Netherlands Antilles">Netherlands Antilles</option>
    <option value="New Caledonia">New Caledonia</option>
    <option value="New Zealand">New Zealand</option>
    <option value="Nicaragua">Nicaragua</option>
    <option value="Niger">Niger</option>
    <option value="Nigeria">Nigeria</option>
    <option value="Niue">Niue</option>
    <option value="Norfolk Island">Norfolk Island</option>
    <option value="Northern Mariana Islands">Northern Mariana Islands</option>
    <option value="Norway">Norway</option>
    <option value="Oman">Oman</option>
    <option value="Pakistan">Pakistan</option>
    <option value="Palau">Palau</option>
    <option value="Panama">Panama</option>
    <option value="Papua New Guinea">Papua New Guinea</option>
    <option value="Paraguay">Paraguay</option>
    <option value="Peru">Peru</option>
    <option value="Philippines">Philippines</option>
    <option value="Pitcairn">Pitcairn</option>
    <option value="Poland">Poland</option>
    <option value="Portugal">Portugal</option>
    <option value="Puerto Rico">Puerto Rico</option>
    <option value="Qatar">Qatar</option>
    <option value="Reunion">Reunion</option>
    <option value="Romania">Romania</option>
    <option value="Russia">Russian Federation</option>
    <option value="Rwanda">Rwanda</option>
    <option value="Saint Kitts and Nevis">Saint Kitts and Nevis</option> 
    <option value="Saint LUCIA">Saint LUCIA</option>
    <option value="Saint Vincent">Saint Vincent and the Grenadines</option>
    <option value="Samoa">Samoa</option>
    <option value="San Marino">San Marino</option>
    <option value="Sao Tome and Principe">Sao Tome and Principe</option> 
    <option value="Saudi Arabia">Saudi Arabia</option>
    <option value="Senegal">Senegal</option>
    <option value="Seychelles">Seychelles</option>
    <option value="Sierra">Sierra Leone</option>
    <option value="Singapore">Singapore</option>
    <option value="Slovakia">Slovakia (Slovak Republic)</option>
    <option value="Slovenia">Slovenia</option>
    <option value="Solomon Islands">Solomon Islands</option>
    <option value="Somalia">Somalia</option>
    <option value="South Africa">South Africa</option>
    <option value="South Georgia">South Georgia and the South Sandwich Islands</option>
    <option value="Span">Spain</option>
    <option value="SriLanka">Sri Lanka</option>
    <option value="St. Helena">St. Helena</option>
    <option value="St. Pierre and Miguelon">St. Pierre and Miquelon</option>
    <option value="Sudan">Sudan</option>
    <option value="Suriname">Suriname</option>
    <option value="Svalbard">Svalbard and Jan Mayen Islands</option>
    <option value="Swaziland">Swaziland</option>
    <option value="Sweden">Sweden</option>
    <option value="Switzerland">Switzerland</option>
    <option value="Syria">Syrian Arab Republic</option>
    <option value="Taiwan">Taiwan, Province of China</option>
    <option value="Tajikistan">Tajikistan</option>
    <option value="Tanzania">Tanzania, United Republic of</option>
    <option value="Thailand">Thailand</option>
    <option value="Togo">Togo</option>
    <option value="Tokelau">Tokelau</option>
    <option value="Tonga">Tonga</option>
    <option value="Trinidad and Tobago">Trinidad and Tobago</option>
    <option value="Tunisia">Tunisia</option>
    <option value="Turkey">Turkey</option>
    <option value="Turkmenistan">Turkmenistan</option>
    <option value="Turks and Caicos">Turks and Caicos Islands</option>
    <option value="Tuvalu">Tuvalu</option>
    <option value="Uganda">Uganda</option>
    <option value="Ukraine">Ukraine</option>
    <option value="United Arab Emirates">United Arab Emirates</option>
    <option value="United Kingdom">United Kingdom</option>
    <option value="United States">United States</option>
    <option value="United States Minor Outlying Islands">United States Minor Outlying Islands</option>
    <option value="Uruguay">Uruguay</option>
    <option value="Uzbekistan">Uzbekistan</option>
    <option value="Vanuatu">Vanuatu</option>
    <option value="Venezuela">Venezuela</option>
    <option value="Vietnam">Viet Nam</option>
    <option value="Virgin Islands (British)">Virgin Islands (British)</option>
    <option value="Virgin Islands (U.S)">Virgin Islands (U.S.)</option>
    <option value="Wallis and Futana Islands">Wallis and Futuna Islands</option>
    <option value="Western Sahara">Western Sahara</option>
    <option value="Yemen">Yemen</option>
    <option value="Yugoslavia">Yugoslavia</option>
    <option value="Zambia">Zambia</option>
    <option value="Zimbabwe">Zimbabwe</option>
    </select>
    </div>
	<label class="col-sm-2 control-label">State</label>
    <div class="col-sm-4">
    <input type="text" id="state1" class="form-control" name="state" autocomplete="off"  placeholder="State"  required maxlength="30">
    <select style="display: none;" id="state2" class="form-control" autocomplete="off" onchange="onSelectState(this)">
	<option value="">Select Your State</option>
	<option value="AL">Alabama</option>
	<option value="AK">Alaska</option>
	<option value="AZ">Arizona</option>
	<option value="AR">Arkansas</option>
	<option value="CA">California</option>
	<option value="CO">Colorado</option>
	<option value="CT">Connecticut</option>
	<option value="DE">Delaware</option>
	<option value="DC">District Of Columbia</option>
	<option value="FL">Florida</option>
	<option value="GA">Georgia</option>
	<option value="HI">Hawaii</option>
	<option value="ID">Idaho</option>
	<option value="IL">Illinois</option>
	<option value="IN">Indiana</option>
	<option value="IA">Iowa</option>
	<option value="KS">Kansas</option>
	<option value="KY">Kentucky</option>
	<option value="LA">Louisiana</option>
	<option value="ME">Maine</option>
	<option value="MD">Maryland</option>
	<option value="MA">Massachusetts</option>
	<option value="MI">Michigan</option>
	<option value="MN">Minnesota</option>
	<option value="MS">Mississippi</option>
	<option value="MO">Missouri</option>
	<option value="MT">Montana</option>
	<option value="NE">Nebraska</option>
	<option value="NV">Nevada</option>
	<option value="NH">New Hampshire</option>
	<option value="NJ">New Jersey</option>
	<option value="NM">New Mexico</option>
	<option value="NY">New York</option>
	<option value="NC">North Carolina</option>
	<option value="ND">North Dakota</option>
	<option value="OH">Ohio</option>
	<option value="OK">Oklahoma</option>
	<option value="OR">Oregon</option>
	<option value="PA">Pennsylvania</option>
	<option value="RI">Rhode Island</option>
	<option value="SC">South Carolina</option>
	<option value="SD">South Dakota</option>
	<option value="TN">Tennessee</option>
	<option value="TX">Texas</option>
	<option value="UT">Utah</option>
	<option value="VT">Vermont</option>
	<option value="VA">Virginia</option>
	<option value="WA">Washington</option>
	<option value="WV">West Virginia</option>
	<option value="WI">Wisconsin</option>
	<option value="WY">Wyoming</option>
</select>
    </div>
  	</div>
  	<div class="form-group">
	<label class="col-sm-2 control-label">Zip Code</label>
    <div class="col-sm-4">
    <input type="text" class="form-control" name="zipCode" autocomplete="off" pattern=".{0}|.{5}" title="5 digit zip code"  placeholder="Zip Code"  required>
    </div>
  	<label class="col-sm-2 control-label">Phone Number</label>
    <div class="col-sm-4">
    <input type="number" class="form-control" name="phone" autocomplete="off" pattern=".{0}|.{10}" title="10 digit phone number" placeholder="Phone Number"  maxlength="30">
    </div>
  	</div>
  	<div class="form-group">
	<label class="col-sm-2 control-label">Administrator</label>
    <div class="col-sm-4">
    <div class="col-sm-12">
    <label class="col-sm-2 control-label">True</label>
    <input class="col-sm-2" type="radio"  name="administrator" value="true">
    </div>
    <div class="col-sm-12">
    <label class="col-sm-2 control-label">False</label>
    <input class="col-sm-2" type="radio"  name="administrator" value="false">
    </div>
    </div>
  	</div>
  	<div class="form-group">
	<label class="col-sm-2 control-label">Disabled</label>
    <div class="col-sm-4">
    <div class="col-sm-12">
    <label class="col-sm-2 control-label">True</label>
    <input class="col-sm-2" type="radio"  name="disabled" value="true">
    </div>
    <div class="col-sm-12">
    <label class="col-sm-2 control-label">False</label>
    <input class="col-sm-2" type="radio"  name="disabled" value="false">
    </div>
    </div>
  	</div>
  	<div class="form-group">
    <div class="col-sm-offset-2 col-sm-5">
      <button type="submit" class="btn btn-primary">Submit</button>
    </div>
  	</div>
</form>
</div>
<script type="text/javascript">
window.onload = function () {
	document.getElementById("pass1").onchange = validatePassword;
	document.getElementById("pass2").onchange = validatePassword;
	document.getElementById("email1").onchange = validateEmail;
	document.getElementById("email2").onchange = validateEmail;
	document.getElementById("number1").onchange = validateUser;
	document.getElementById("number2").onchange = validateUser;
	document.getElementById("ID1").onchange = validateID;
	document.getElementById("ID2").onchange = validateID;
}
function validatePassword(){
var pass2=document.getElementById("pass1").value;
var pass1=document.getElementById("pass2").value;
if(pass1!=pass2)
	document.getElementById("pass2").setCustomValidity("Passwords Don't Match");
else
	document.getElementById("pass2").setCustomValidity('');	 
}
function validateEmail(){
	var email1=document.getElementById("email1").value;
	var email2=document.getElementById("email2").value;
	if(email1!=email2)
		document.getElementById("email2").setCustomValidity("Emails Don't Match");
	else
		document.getElementById("email2").setCustomValidity('');	 
}
function validateUser(){
	var number1=document.getElementById("number1").value;
	var number2=document.getElementById("number2").value;
	if(number1!=number2)
		document.getElementById("number2").setCustomValidity("User ID's Must Match!");
	else
		document.getElementById("number2").setCustomValidity('');	 
}
function validateID(){
	var ID1=document.getElementById("ID1").value;
	var ID2=document.getElementById("ID2").value;
	if(ID1!=ID2)
		document.getElementById("ID2").setCustomValidity("User ID's Must Match!");
	else
		document.getElementById("ID2").setCustomValidity('');	 
}
function onSelectCountry(event){
	var country = event.value;
	var input = document.getElementById("state1");
	var select = document.getElementById("state2");
	if(country == "United States"){
		input.style.display = 'none';
		select.style.display = 'inline';
	}
	else{
		select.style.display = 'none';
		input.style.display = 'inline';
	}
	
}
function onSelectState(event){
	var value = event.value;
	var input = document.getElementById("state1");
	input.value = value;
}
</script>
	
</body>
</html>