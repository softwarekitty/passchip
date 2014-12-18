package com.appspot.passchip_service;

import java.io.IOException;
import java.net.MalformedURLException;
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
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

/**
 * Servlet implementation class SetupUser
 */
public class SetupUser extends HttpServlet {

	private static final long serialVersionUID = 123456765L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String communityname = request.getParameter("community");
		String chipID = request.getParameter("ID");
		System.out.println("SetupUser - community name: " + communityname);
		System.out.println("SetupUser - chipID: " + chipID);

		// get the community's information from the datastore

		String bookID = null;
		String username = null;
		String pswd = null;
		try {
			Entity community = new DatastoreInteraction()
					.getCommunity(communityname);
			bookID = (String) community.getProperty("sheetsID");
			username = (String) community.getProperty("username");
			pswd = (String) community.getProperty("password");

		} catch (EntityNotFoundException e) {
			// TODO - react to this problem
			e.printStackTrace();
		}

		SpreadsheetService spreadsheetService = null;
		try {
			spreadsheetService = getService(username, pswd);
		} catch (AuthenticationException e) {
			// TODO react to this problem
			e.printStackTrace();
		}
		
		
		
		String usersheetID = null;
		try {
			usersheetID = createUserWorksheet(spreadsheetService, bookID, chipID);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//create the user entry in the datastore
		DatastoreInteraction d = new DatastoreInteraction();
		d.createUser(chipID,communityname, usersheetID, 1);

		request.setAttribute("username", username);
		request.setAttribute("password", pswd);
		request.setAttribute("bookID", bookID);
		request.setAttribute("chipID", chipID);
		request.setAttribute("sheetID", usersheetID);
		
		RequestDispatcher rd = request.getRequestDispatcher("/ui.jsp");
		rd.forward(request, response);
	}

	/**
	 * Creates a worksheet for the user's credential storage
	 * 
	 * @return the ID for the created worksheet
	 */
	private static String createUserWorksheet(
			SpreadsheetService spreadsheetService, String bookID, String chipID)
			throws IOException, ServiceException {

		//get the book
		SpreadsheetEntry communityBook = getBookWithID(bookID,
				spreadsheetService);

		//construct model of empty worksheet
		WorksheetEntry newWorksheet = new WorksheetEntry();
		newWorksheet.setTitle(new PlainTextConstruct(chipID));
		newWorksheet.setColCount(3);
		newWorksheet.setRowCount(20);

		//insert new worksheet via service
		URL worksheetFeedUrl = communityBook.getWorksheetFeedUrl();
		spreadsheetService.insert(worksheetFeedUrl, newWorksheet);

		//get feed of worksheets
		WorksheetFeed worksheetFeed = spreadsheetService.getFeed(
				worksheetFeedUrl, WorksheetFeed.class);
		String worksheetID = getWorksheetIDFromTitle(worksheetFeed, chipID);
		
		addWorksheetHeaders(spreadsheetService,worksheetFeed, worksheetID);
		return worksheetID;

	}
	
	private static void addWorksheetHeaders(SpreadsheetService spreadsheetService, WorksheetFeed worksheetFeed,
			String worksheetID) throws IOException, ServiceException {

		WorksheetEntry newUserSheet = getSheetWithID(worksheetID, worksheetFeed);
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
	}

	private static String getWorksheetIDFromTitle(WorksheetFeed worksheetFeed, String title){
		//find id of first sheet with matching title
		List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
		for (WorksheetEntry worksheet : worksheets) {

			String worksheetTitle = worksheet.getTitle().getPlainText();
			if (worksheetTitle.toString().equals(title)) {
				return worksheet.getId();
			}
		}
		throw new IllegalStateException(
				"You don't have access to a sheet with title " + title);
	}

	private static SpreadsheetService getService(String username, String pswd)
			throws AuthenticationException {
		SpreadsheetService spreadsheetService = new SpreadsheetService(
				"passchip-service");
		spreadsheetService.setUserCredentials(username, pswd);
		return spreadsheetService;
	}

	/**
	 * Returns the Book with the given key.
	 * 
	 * @throws IOException
	 *             If a network error occurs while trying to communicate with
	 *             Spreadsheets
	 * @throws ServiceException
	 *             If an application-level protocol error occurs while trying to
	 *             communicate with Spreadsheets
	 */
	private static SpreadsheetEntry getBookWithID(String bookID,
			SpreadsheetService spreadsheetService) throws IOException,
			ServiceException {
		URL metafeedUrl = new URL(
				"http://spreadsheets.google.com/feeds/spreadsheets/private/full");
		SpreadsheetFeed spreadsheetFeed = spreadsheetService.getFeed(
				metafeedUrl, SpreadsheetFeed.class);

		List<SpreadsheetEntry> spreadsheets = spreadsheetFeed.getEntries();
		for (SpreadsheetEntry spreadsheet : spreadsheets) {
			if (spreadsheet.getKey().equals(bookID)) {
				return spreadsheet;
			}
		}
		throw new IllegalStateException(
				"You don't have access to a spreadsheet with key " + bookID);
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
