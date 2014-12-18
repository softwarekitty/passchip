package com.appspot.passchip_service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gdata.client.spreadsheet.CellQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;

public class WorkSheetContent {
	private static final String APP_NAME = "passchip-service";

	public static List<UserEntry> getSheetContent(String USERNAME, String PASSWORD, String bookID, String sheetID) throws IOException,
			ServiceException {

		System.out.println("getting Sheet content with username: " + USERNAME + " password: " + PASSWORD + " bookID: " + bookID + " sheetID: " + sheetID);
		SpreadsheetService spreadsheetService = new SpreadsheetService(APP_NAME);
		spreadsheetService.setUserCredentials(USERNAME, PASSWORD);
		SpreadsheetEntry spreadsheet = getBookWithID(bookID, spreadsheetService);

		WorksheetFeed worksheetFeed = null;
		while(worksheetFeed==null){
			try{
			worksheetFeed = spreadsheetService.getFeed(
					spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
			}catch(Exception e){
				System.out.println("trouble getting worksheetFeed: "+ e.getMessage());
			}
		}
				

		WorksheetEntry worksheet = getSheetWithID(sheetID, worksheetFeed);

//		URL cellFeedUrl = worksheet.getCellFeedUrl();
//		CellFeed cellFeed = spreadsheetService.getFeed(cellFeedUrl,
//				CellFeed.class);
//		cellFeed = spreadsheetService.getFeed(cellFeedUrl, CellFeed.class);
//		cellFeed.insert(new CellEntry(1, 1, "abc"));
		
		
		



		 // Create a local representation of the new row.
		URL listFeedUrl = worksheet.getListFeedUrl();
	    ListFeed listFeed = spreadsheetService.getFeed(listFeedUrl, ListFeed.class);


		 List<UserEntry> lists = new ArrayList<UserEntry>();
		 for(int i = 0; i < listFeed.getEntries().size(); i++){
			 ListEntry entry = listFeed.getEntries().get(i);
			 UserEntry d = new UserEntry();
			 //entry.get
			 d.website = entry.getCustomElements().getValue("SiteId");
			 d.usr = entry.getCustomElements().getValue("Username");
			 d.password = entry.getCustomElements().getValue("Password");
			 lists.add(d);
		 }
		 return lists;
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

	public static void main(String[] args) throws IOException, ServiceException {
		String USERNAME = "passchip514@gmail.com";
		String PASSWORD = "gocyclone";
		String bookID = "1FnDRWz4CjUJwatYG6gn7P_5hQH__pVqEDpvLub4gJ6M";
		String sheetID = "https://spreadsheets.google.com/feeds/worksheets/1FnDRWz4CjUJwatYG6gn7P_5hQH__pVqEDpvLub4gJ6M/od0sh5t";
		List<UserEntry> y = getSheetContent(USERNAME, PASSWORD, bookID, sheetID);
		for (UserEntry b : y){
			System.out.println(b.website);
		}
	}
}
