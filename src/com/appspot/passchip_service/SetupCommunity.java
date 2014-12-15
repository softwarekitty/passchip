package com.appspot.passchip_service;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SetupCommunity
 */
public class SetupCommunity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetupCommunity() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DatastoreInteraction d = new DatastoreInteraction();
		String communityname=request.getParameter("communityname");
		String username=request.getParameter("username");
		String pswd=request.getParameter("pswd");
		System.out.println(d.createCommunity(communityname,username, pswd, "testSheetsID"));
		RequestDispatcher rd = request.getRequestDispatcher("/setupuser.jsp");
    	
    	rd.forward(request,response);
	}

}
