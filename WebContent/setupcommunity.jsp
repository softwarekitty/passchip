<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Set up community</title>
</head>
<body>
<h1>Setup Community</h1>
<form name="setup" action="/SetupCommunity" method="post" >
Community Name <input type="text"  name="communityname">
User Name<input type="text" name="username">
Password<input type="password" name="pswd">
<input type="submit" value="Submit">
</form>
</body>
</html>