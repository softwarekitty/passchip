package com.appspot.passchip_service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;

public class WorkSheetContent {
	
	public static List<UserEntry> getSheetContent(String sheetId) throws IOException, ServiceException{
		  SpreadsheetService service =   new SpreadsheetService("MySpreadsheetIntegration-v1");

	      // TODO: Authorize the service object for a specific user (see other sections)
	      String USERNAME = "passchip514@gmail.com";
	      String PASSWORD = "gocyclone";

	      service.setUserCredentials(USERNAME, PASSWORD);

	    // TODO: Authorize the service object for a specific user (see other sections)

	    // Define the URL to request.  This should never change.
//	    URL SPREADSHEET_FEED_URL = new URL(
//	        "https://spreadsheets.google.com/feeds/spreadsheets/private/full");
//
//	    // Make a request to the API and get all spreadsheets.
//	    SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL,
//	        SpreadsheetFeed.class);
//	    List<SpreadsheetEntry> spreadsheets = feed.getEntries();
//
//	    if (spreadsheets.size() == 0) {
//	      // TODO: There were no spreadsheets, act accordingly.
//	    }
//
//	    // TODO: Choose a spreadsheet more intelligently based on your
//	    // app's needs.
	    SpreadsheetEntry spreadsheet = spreadsheets.get(0);
	    System.out.println(spreadsheet.getTitle().getPlainText());

	    // Get the first worksheet of the first spreadsheet.
	    // TODO: Choose a worksheet more intelligently based on your
	    // app's needs.
	    WorksheetFeed worksheetFeed = service.getFeed(
	        spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
	    List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
	    WorksheetEntry worksheet = worksheets.get(0);
	    

	    // Fetch the list feed of the worksheet.
	    URL listFeedUrl = worksheet.getListFeedUrl();
	    ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);

	    // Create a local representation of the new row.
	    List<UserEntry> lists = new ArrayList<UserEntry>();
	   for(int i = 0; i < listFeed.getEntries().size(); i++){
		   ListEntry entry = listFeed.getEntries().get(i);
		   UserEntry d = new UserEntry();
			d.website = entry.getCustomElements().getValue("SiteId");
			d.usr = entry.getCustomElements().getValue("Username");
			d.password = entry.getCustomElements().getValue("Password");
		   lists.add(d);
	   }
	return lists;
	    
	   

	}
	
	  /**
	   * Returns the SpreadsheetEntry for the spreadsheet with the given key.
	   *
	   * @throws IOException If a network error occurs while trying to communicate
	   *     with Spreadsheets
	   * @throws ServiceException If an application-level protocol error occurs
	   *     while trying to communicate with Spreadsheets
	   */
	  private static SpreadsheetEntry getSpreadsheetWithKey(String key, SpreadsheetService spreadsheetService) throws IOException, ServiceException {
	    URL metafeedUrl = new URL("http://spreadsheets.google.com/feeds/spreadsheets/private/full");
	    SpreadsheetFeed spreadsheetFeed = spreadsheetService.getFeed(
	        metafeedUrl,
	        SpreadsheetFeed.class);

	    List<SpreadsheetEntry> spreadsheets = spreadsheetFeed.getEntries();
	    for (SpreadsheetEntry spreadsheet : spreadsheets) {
	    	System.out.println("found spreadsheet with key: " + spreadsheet.getKey());
	      if (spreadsheet.getKey().equals(key)) {
	        return spreadsheet;
	      }
	    }
	    throw new IllegalStateException("You don't have access to a spreadsheet with key " + key);    
	  }
	   public static void main(String[] args) throws IOException, ServiceException{
		   getSheetContent("a");
	   }
}
