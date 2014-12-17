package com.appspot.passchip_service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gdata.util.ServiceException;


public class YalinServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2489251434031281467L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		pw.write("abc");
//		System.out.print("todo");
//		List<Data> data = new ArrayList<Data>();
//		try {
//			List<List<String>> lists = WorkSheetContent.getSheetContent("a");
//			for(List<String> list : lists){
//				Data d = new Data();
//				d.website = list.get(0);
//				d.usr = list.get(1);
//				d.password = list.get(2);
//			}
//		} catch (ServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		req.setAttribute("data", data);
//		//resp.se
		
		resp.setStatus(300);
		//resp.set
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String s = req.getParameter("operation");
		if(s== null || s == ""){
			resp.setStatus(100);
		}
		else {
			String SHEETID = req.getParameter("SHEETID");
			String BOOKID = req.getParameter("BOOKID");
			String sheetUserName  = req.getParameter("USERNAME");
			String sheetPassword = req.getParameter("PASSWORD");
			System.out.println(SHEETID + BOOKID + sheetUserName + sheetPassword);
			
			String website = req.getParameter("website");
			String userName = req.getParameter("usr");
			String password = req.getParameter("password");
			int  index = Integer.valueOf(req.getParameter("index")) - 1;
			System.out.println(index);
			if(s.equals("add")){
				AddListRow addlist = new AddListRow();
					try {
						addlist.addRow(BOOKID, SHEETID, sheetUserName, sheetPassword, website, userName, password,index);
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			else if(s.equals("delete")){
				DeleteListRow deleter = new DeleteListRow();
				try {
					deleter.delete(BOOKID, SHEETID, sheetUserName, sheetPassword, website, userName, password, index);
				} catch ( ServiceException e) {
					resp.setStatus(100);
					e.printStackTrace();
					return;
				}
				System.out.println("delete");
			}
			else if(s.equals("update")){
			
				UpdateListRow update = new UpdateListRow();
				try {
					update.update(BOOKID, SHEETID, sheetUserName, sheetPassword, website, userName, password, index);
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
