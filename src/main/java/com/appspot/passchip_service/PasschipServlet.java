package com.appspot.passchip_service;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//TODO - security.  see example: http://stackoverflow.com/questions/8717626/whos-calling-my-httpservletrequest
//also: http://tomcat.apache.org/tomcat-7.0-doc/config/filter.html#CSRF_Prevention_Filter

public class PasschipServlet extends HttpServlet {
	// @formatter:off
	/*
	 * steps: 
	 * 1. look up user id 
	 * 2. identify community
	 * 3. get community credentials
	 * 4. use credentials to get user profile 
	 * 	4.E(1st time validation: user must allow the webapp to use their credentials.  an error page should say this!)
	 * 5. launch user profile
	 * 6. ...user profile calls services, etc. 
	 */
	// @formatter:on

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws java.io.IOException, javax.servlet.ServletException {
		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();

		if (currentUser != null) {
			resp.setContentType("text/plain");
			resp.getWriter().println("Hello, " + currentUser.getNickname());
		} else {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
		}
	}
}