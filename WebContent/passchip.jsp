<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedList" %>


<%-- for demo: northcrestdemo@gmail.com - passchip --%>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
    <script>
    function launch(launch_id){
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
    }else{
    	System.out.println(id);
    	RequestDispatcher rd = request.getRequestDispatcher("VerifyServlet");
    	System.out.println("success creating request dispatcher");
    	rd.forward(request,response);
    	}
%>


</body>
</html>