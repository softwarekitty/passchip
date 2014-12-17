<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.appspot.passchip_service.DatastoreInteraction" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Setup user</title>
</head>
<body>
<%
String chipID;
if(request.getParameter("ID")==null)
chipID=(String)request.getAttribute("ID");
else
chipID=request.getParameter("ID"); 
DatastoreInteraction d = new DatastoreInteraction();
List<Entity> result=d.getCommunities();
ArrayList<String> name=new ArrayList<String>(); 
for(int i=0;i<result.size();i++)
{
	name.add(result.get(i).getProperty("communityName").toString());
	System.out.println(result.get(i).getProperty("communityName").toString());
}
request.setAttribute("name",name );
//request.setAttribute("ID",chipID);
%>
<p> Setting up user for the Chip ID <%=chipID %></p>

<p> Select a community from the list or setup a new community using </p>
<form action="/SetupCommunity.jsp" method="post"> 
<input type="hidden" name="ID" value="<%=chipID%>">
 <input type="submit" value="Setup new Community">
</form>  
<form action="/SetupUser" method="post">
 <select name="community">
    <c:forEach var="line" items="${name}">
        <option><c:out value="${line}"/></option>
    </c:forEach>
 </select>
 <input type="hidden" name="ID" value="<%=chipID%>">
 <input type="submit" value="Submit">
</form>


</body>
</html>