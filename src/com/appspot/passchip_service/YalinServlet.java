package com.appspot.passchip_service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gdata.util.ServiceException;

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
		
		String s = req.getParameter("operation");
		if(s== null || s == ""){
			resp.setStatus(100);
		}
		else {
			String website = req.getParameter("website");
			String userName = req.getParameter("usr");
			String password = req.getParameter("password");
			int  index = Integer.valueOf(req.getParameter("index")) - 1;
			System.out.println(index);
			if(s.equals("add")){
				AddListRow addlist = new AddListRow();
				try {
					addlist.addRow(website, userName, password);
				} catch (ServiceException e) {
					resp.setStatus(100);
					e.printStackTrace();
					return;
				}
			}
			else if(s.equals("delete")){
				DeleteListRow deleter = new DeleteListRow();
				try {
					deleter.delete(website, userName, password, index);
				} catch (ServiceException e) {
					resp.setStatus(100);
					e.printStackTrace();
					return;
				}
				System.out.println("delete");
			}
			else if(s.equals("update")){
			
				UpdateListRow update = new UpdateListRow();
				try {
					update.update(website, userName, password, index);
				} catch (ServiceException e) {
					resp.setStatus(100);
					e.printStackTrace();
					return;
				}
				System.out.println("update");
			}
				
		}
		resp.setStatus(300);
	}
	
}
