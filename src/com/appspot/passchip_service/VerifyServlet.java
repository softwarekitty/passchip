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
 * Servlet implementation class VerifyServlet
 */
public class VerifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyServlet() {
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
		String chipID=request.getParameter("ID");
		DatastoreInteraction d = new DatastoreInteraction();
		if(d.verifyUser(chipID)==1)
		{
			System.out.println("ID present");
			request.setAttribute("sheetID",d.getUser(chipID).getProperty("sheetID"));
			Long l = new Long(d.getUser(chipID).getProperty("communityID").toString());
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
			request.setAttribute("chipID",chipID);
			RequestDispatcher rd = request.getRequestDispatcher("/ui.jsp");
	    	rd.forward(request,response);
			
		}
		else
		{
			System.out.println("ID not present");
			RequestDispatcher rd = request.getRequestDispatcher("/setupuser.jsp");
	    	System.out.println("success creating request dispatcher");
	    	rd.forward(request,response);
		}
		System.out.println(request.getParameter("ID"));
		
	}

}
