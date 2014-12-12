package com.appspot.passchip_service;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


public class WorksheetIDGettingServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws java.io.IOException, javax.servlet.ServletException {
			resp.getWriter().println("Hello, let's get some ID");
	}
}