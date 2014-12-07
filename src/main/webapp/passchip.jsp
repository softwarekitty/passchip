<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedList" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- for demo: northcrestdemo@gmail.com - passchip --%>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
    <script>
    function launch(launch_id){
        alert("launching "+launch_id);
        var win = window.open('/'+launch_id+".jsp",'_blank');
        if(win){
            win.focus();
        }else{
                alert("please allow popups for this website");
        }
    }
</script>
</head>
<body>
<%-- we expect an ID in the post.  If no ID param exists, say so. --%>
<%
    //IDBRANCH1 - null id
    //deploy command: mvn appengine:update
    String existingID1 = "160084DCA9";
    String existingID2 = "1600837A5C";
    List<String> existingIDList = new LinkedList();
    existingIDList.add(existingID1);
    //existingIDList.add(existingID2);
    String id = request.getParameter("ID");
    if (id == null) {
%>
<p>No ID was provided in Post, which we do not allow.</p>
<p>This may have happened if you typed in the url by hand.</p>
<p>Please trigger the passchip hardware device instead.</p>
<p>If you need help, please refer to the <a href="#">documentation</a></p>
<p>Or you can use your paid-support credits to <a href="#">contact us</a></p>
<p>...</p>
<p>For Dev, we can go to a page that uses post. <button type="button" onclick=launch("postgen")>Go to Post Page</button></p>

<%-- we have a valid ID value.  we should do the following things: --%>
<%-- 1. try to find the community associated with the ID --%>
<%
    //IDBRANCH2 - existing id
    }else if(existingIDList.contains(id)){
%>

<%-- 1.1 if we find it, then get the data for that user from that community sheet --%>
<%-- and then offer the main user interface - simulated here by a refresh --%>
<META http-equiv="refresh" content="0;URL=/userpage.jsp">


<%-- 2. if there's no community associated with it, then maybe we are adding a new user or replacing lost chip --%>
<%-- 2 now we certainly want provide community interface, but maybe it's not logged in --%>
<%-- 2 so we branch depending on the user login status--%>
<%-- //note that User, UserService, UserServiceFactory are from appengine.api --%>
<%-- //we want to allow a logged-in admin not to need to login every time --%>
<%
    //IDBRANCH3 - id does not exist: offer community interfaces
    }else {
%>
<%-- 2.1 user is already logged in--%>
<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    //USERBRANCH1 - user is logged in
    if (user != null) {
        pageContext.setAttribute("user", user);
%>
<p>Hello, ${fn:escapeXml(user.nickname)} community.  (You can
    <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
    <p>or you can...<a href="#">Set up new user with id: <%=id%></a></p>
    <p>or maybe you want to manage existing users:</p>
    <table style="width:100%">
      <tr>
        <th>Username</th>
        <th>Replace</th> 
        <th>Delete</th>
      </tr>
      <tr>
        <td>Jill</td>
        <td>x</td> 
        <td>x</td>
      </tr>
      <tr>
        <td>Eve</td>
        <td>x</td> 
        <td>x</td>
      </tr>
    </table>
<%
    //USERBRANCH2 - user is not logged in
    } else {
%>
<%-- 2.2 user is not logged in--%>
<p>Hi there stranger.  Please  
    <a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
    to your community account. or <a href="#">Create New Community</a></p>
<%-- this bracket closes the 'else' for if user!=null--%>
<%
    }
%>
<%-- this last bracket closes the 'else' for if id!=null--%>
<%
    }
%>
</body>
</html>