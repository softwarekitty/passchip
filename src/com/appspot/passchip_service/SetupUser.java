package com.appspot.passchip_service;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * Servlet implementation class SetupUser
 */
public class SetupUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetupUser() {
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
		DatastoreInteraction d= new DatastoreInteraction();
		System.out.println(request.getParameter("ID"));
		String communityname=request.getParameter("community");
		System.out.println(request.getParameter("community"));
		System.out.println(d.getcommunityID(communityname));
		
		
		d.createUser(request.getParameter("ID"),d.getcommunityID(communityname) , "testsheetid" , 1);
		request.setAttribute("sheetID",d.getUser(request.getParameter("ID")).getProperty("sheetID"));
		Long l = new Long(d.getUser(request.getParameter("ID")).getProperty("communityID").toString());
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key k = KeyFactory.createKey("Community",l);
		Entity result;
		try {
			result = datastore.get(k);
			request.setAttribute("bookID",result.getProperty("sheetsID"));
			request.setAttribute("username",result.getProperty("username"));
			request.setAttribute("password",result.getProperty("password"));
			
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("chipID",request.getParameter("ID"));
		RequestDispatcher rd = request.getRequestDispatcher("/ui.jsp");
    	rd.forward(request,response);
	}

}
