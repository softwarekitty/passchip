package com.appspot.passchip_service;




//import com.google.gdata.client.spreadsheet.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class DeleteListRow {
	private static final String APP_NAME = "passchip-service";
	
	public void delete(String BOOKID, String SHEETID, String USERNAME, String PASSWORD, String website, String usr, String password, int index) throws IOException, ServiceException{
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

	    // TODO: Choose a row more intelligently based on your app's needs.
	    ListEntry row = listFeed.getEntries().get(index);

	    // Delete the row using the API.
	    row.delete();
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