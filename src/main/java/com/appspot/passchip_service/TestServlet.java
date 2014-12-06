package com.appspot.passchip_service;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
	  
	System.out.println("new Servlet1");
    System.out.println(d.createCommunity("testcommunityname","testuname", "testpswd", "testsheetsid"));
    List<Entity> resultset=d.getCommunities();
    System.out.println("printing from servlet");
    for (int i=0;i<resultset.size();i++)
	{
    	System.out.println(i);
		System.out.println(resultset.get(i).getProperty("communityName"));
	}
    Long communityID=new Long("6122080743456768");
    System.out.println(d.createUser("0XC792",communityID,"testsheetsID" , 1));
    System.out.println(d.verifyUser("0XC790"));
    System.out.println(d.getUser("0XC790"));
    resp.sendRedirect("/webapp/test.jsp);
  }
}