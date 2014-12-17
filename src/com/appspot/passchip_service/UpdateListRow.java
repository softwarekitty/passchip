package com.appspot.passchip_service;



import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.*;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class UpdateListRow {
	private static final String APP_NAME = "passchip-service";
	public void update (String BOOKID, String SHEETID, String USERNAME, String PASSWORD,String website, String usr, String password, int index) throws IOException, ServiceException{

		SpreadsheetService spreadsheetService = new SpreadsheetService(APP_NAME);
		spreadsheetService.setUserCredentials(USERNAME, PASSWORD);
		SpreadsheetEntry spreadsheet = getBookWithID(BOOKID, spreadsheetService);

		WorksheetFeed worksheetFeed = spreadsheetService.getFeed(
				spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);

		WorksheetEntry worksheet = getSheetWithID(SHEETID, worksheetFeed);

		URL cellFeedUrl = worksheet.getCellFeedUrl();
		CellFeed cellFeed = spreadsheetService.getFeed(cellFeedUrl,
				CellFeed.class);

		 // Create a local representation of the new row.
		URL listFeedUrl = worksheet.getListFeedUrl();
	    ListFeed listFeed = spreadsheetService.getFeed(listFeedUrl, ListFeed.class);
	    ListEntry row = listFeed.getEntries().get(index);
	    

	    // Update the row's data.
	    row.getCustomElements().setValueLocal("SiteID", website);
	    row.getCustomElements().setValueLocal("Username", usr);
	    row.getCustomElements().setValueLocal("Password", password);

	    // Save the row using the API.
	    row.update();
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