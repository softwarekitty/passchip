package com.appspot.passchip_service;

import java.io.IOException;
import java.net.URL;
import java.util.List;

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
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;

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
		String key;
		String username;
		String pswd;
		String usersheetID=new String();
		
		//Long l = new Long(d.getUser(request.getParameter("ID")).getProperty("communityID").toString());
		Long l= new Long(d.getcommunityID(communityname));
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key k = KeyFactory.createKey("Community",l);
		Entity result;
		try {
			result = datastore.get(k);
			request.setAttribute("bookID",result.getProperty("sheetsID"));
			key=(String) result.getProperty("sheetsID");
			request.setAttribute("username",result.getProperty("username"));
			
			
			username=(String) result.getProperty("username");
			request.setAttribute("password",result.getProperty("password"));
			pswd=(String) result.getProperty("password");
			System.out.println(username);
			System.out.println(pswd);
			
		
		//code for sheets creation
		SpreadsheetService spreadsheetService = new SpreadsheetService("passchip-service");
		spreadsheetService.setUserCredentials(username,pswd);
		//String key = "1FnDRWz4CjUJwatYG6gn7P_5hQH__pVqEDpvLub4gJ6M";
		SpreadsheetEntry spreadsheet = getSpreadsheetWithKey(key,
				spreadsheetService);
		request.setAttribute("chipID",request.getParameter("ID"));
		String chipID=request.getParameter("ID");
		 WorksheetEntry newWorksheet = new WorksheetEntry();
			newWorksheet.setTitle(new PlainTextConstruct(chipID));
			newWorksheet.setColCount(3);
			newWorksheet.setRowCount(20);	
			URL worksheetFeedUrl = spreadsheet.getWorksheetFeedUrl();
			spreadsheetService.insert(worksheetFeedUrl, newWorksheet);
		
			WorksheetFeed worksheetFeed = spreadsheetService.getFeed(
				 spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
			List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
			for (WorksheetEntry worksheet : worksheets) {
				
				String sm = worksheet.getTitle().getPlainText();
				if (sm.toString().equals(chipID)){
//					System.out.println("ID: " + worksheet.getId());
//					
//					URL cellFeedUrl = worksheet.getCellFeedUrl();
//					CellFeed cellFeed = spreadsheetService.getFeed(cellFeedUrl,
//							CellFeed.class);
//					cellFeed.insert(new CellEntry(1, 1, "SiteId"));
//					cellFeed.insert(new CellEntry(1, 2, "Username"));
//					cellFeed.insert(new CellEntry(1, 3, "Password"));
					usersheetID=worksheet.getId();
				}
			}	
			System.out.println("usersheetID:" + usersheetID);
			WorksheetEntry newUserSheet = getSheetWithID(usersheetID, worksheetFeed);
			// Write header line into Spreadsheet
			URL cellFeedUrl = newUserSheet.getCellFeedUrl();
			CellFeed cellFeed = spreadsheetService.getFeed(cellFeedUrl,
					CellFeed.class);

			CellEntry cellEntry1 = new CellEntry(1, 1, "SiteId");
			cellFeed.insert(cellEntry1);
			CellEntry cellEntry2 = new CellEntry(1, 2, "Username");
			cellFeed.insert(cellEntry2);
			CellEntry cellEntry3 = new CellEntry(1, 3, "Password");
			cellFeed.insert(cellEntry3);
			System.out.println("added cell headers for sheet with title: " + chipID);

			//


		d.createUser(request.getParameter("ID"),d.getcommunityID(communityname) , usersheetID , 1);
		request.setAttribute("sheetID",d.getUser(request.getParameter("ID")).getProperty("sheetID"));
		System.out.println("printing user sheet id after creation:"+d.getUser(request.getParameter("ID")).getProperty("sheetID"));
		
		RequestDispatcher rd = request.getRequestDispatcher("/ui.jsp");
    	rd.forward(request,response);
		} catch (EntityNotFoundException | ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static SpreadsheetEntry getSpreadsheetWithKey(String key,
			SpreadsheetService spreadsheetService) throws IOException,
			ServiceException {
		URL metafeedUrl = new URL(
				"http://spreadsheets.google.com/feeds/spreadsheets/private/full");
		SpreadsheetFeed spreadsheetFeed = spreadsheetService.getFeed(
				metafeedUrl, SpreadsheetFeed.class);

		List<SpreadsheetEntry> spreadsheets = spreadsheetFeed.getEntries();
		for (SpreadsheetEntry spreadsheet : spreadsheets) {
			System.out.println("found spreadsheet with key: "
					+ spreadsheet.getKey());
			if (spreadsheet.getKey().equals(key)) {
				return spreadsheet;
			}
		}
		throw new IllegalStateException(
				"You don't have access to a spreadsheet with key " + key);
	}
	
	/**
	 * Returns the Sheet with the given key.
	 * 
	 * @throws IOException
	 *             If a network error occurs while trying to communicate with
	 *             Spreadsheets
	 * @throws ServiceException
	 *             If an application-level protocol error occurs while trying to
	 *             communicate with Spreadsheets
	 */
	private static WorksheetEntry getSheetWithID(String sheetID,
			WorksheetFeed worksheetFeed) throws IOException, ServiceException {
		List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
		for (WorksheetEntry worksheet : worksheets) {
			if (worksheet.getId().equals(sheetID)) {
				return worksheet;
			}
		}
		throw new IllegalStateException(
				"You don't have access to a spreadsheet with key " + sheetID);
	}

}
