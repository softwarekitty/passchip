package com.appspot.passchip_service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class YalinServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Creating new todo ");
		resp.setHeader("yalin", "test");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String s = req.getParameter("user");
		System.out.println("g: " + s + req.getAttribute("user"));
		resp.setStatus(300);
	}
	
	
}
