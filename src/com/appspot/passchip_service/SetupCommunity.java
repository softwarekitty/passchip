package com.appspot.passchip_service;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

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
        
		//create workbook code
		DocsService docsService = new DocsService("passchip-service");
		System.out.println(username);
		System.out.println(pswd);
        try {
			docsService.setUserCredentials(username, pswd);
		
        URL GOOGLE_DRIVE_FEED_URL = new URL("https://docs.google.com/feeds/default/private/full/");
        DocumentListEntry documentListEntry = new com.google.gdata.data.docs.SpreadsheetEntry();
        documentListEntry.setTitle(new PlainTextConstruct(communityname));
        documentListEntry = docsService.insert(GOOGLE_DRIVE_FEED_URL, documentListEntry);
        System.out.println("Create a new Spreadsheet successfully!");
        String idPattern = "(.*)%(.*)";
        Pattern p = Pattern.compile(idPattern);
        System.out.println(p.toString());
        Matcher m = p.matcher(documentListEntry.getId());
        m.find();
        System.out.println(m.toString());

        System.out.println(documentListEntry.getId());
        String id = m.group(2);
        if(id.startsWith("3A")){
       	 id = id.substring(2);
        }
        System.out.println(id);
		System.out.println(d.createCommunity(communityname,username, pswd, id));
		RequestDispatcher rd = request.getRequestDispatcher("/setupuser.jsp");
    	
    	rd.forward(request,response);
        } catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
