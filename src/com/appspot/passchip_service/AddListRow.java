package com.appspot.passchip_service;

import com.google.common.collect.Maps;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class AddListRow {
	
	public  void addRow(String website, String usr, String password, int index) throws IOException, ServiceException{
		  SpreadsheetService service =   new SpreadsheetService("MySpreadsheetIntegration-v1");

	      // TODO: Authorize the service object for a specific user (see other sections)
	      String USERNAME = "passchip514@gmail.com";
	      String PASSWORD = "gocyclone";

	      service.setUserCredentials(USERNAME, PASSWORD);

	    // TODO: Authorize the service object for a specific user (see other sections)

	    // Define the URL to request.  This should never change.
	    URL SPREADSHEET_FEED_URL = new URL(
	        "https://spreadsheets.google.com/feeds/spreadsheets/private/full");

	    // Make a request to the API and get all spreadsheets.
	    SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL,
	        SpreadsheetFeed.class);
	    List<SpreadsheetEntry> spreadsheets = feed.getEntries();

	    if (spreadsheets.size() == 0) {
	      // TODO: There were no spreadsheets, act accordingly.
	    }

	    // TODO: Choose a spreadsheet more intelligently based on your
	    // app's needs.
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
	    ListEntry row = new ListEntry();
	    row.getCustomElements().setValueLocal("SiteID", website);
	    row.getCustomElements().setValueLocal("Username", usr);
	    row.getCustomElements().setValueLocal("Password", password);
	   
	    
	   

	    // Send the new row to the API for insertion.
	    row = service.insert(listFeedUrl, row);
	}
  public static void main(String[] args)
      throws AuthenticationException, MalformedURLException, IOException, ServiceException {
	  
	  //addRow("a", "b", "c");

//	  SpreadsheetService service =   new SpreadsheetService("MySpreadsheetIntegration-v1");
//
//      // TODO: Authorize the service object for a specific user (see other sections)
//      String USERNAME = "passchip514@gmail.com";
//      String PASSWORD = "gocyclone";
//
//      service.setUserCredentials(USERNAME, PASSWORD);
//
//    // TODO: Authorize the service object for a specific user (see other sections)
//
//    // Define the URL to request.  This should never change.
//    URL SPREADSHEET_FEED_URL = new URL(
//        "https://spreadsheets.google.com/feeds/spreadsheets/private/full");
//
//    // Make a request to the API and get all spreadsheets.
//    SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL,
//        SpreadsheetFeed.class);
//    List<SpreadsheetEntry> spreadsheets = feed.getEntries();
//
//    if (spreadsheets.size() == 0) {
//      // TODO: There were no spreadsheets, act accordingly.
//    }
//
//    // TODO: Choose a spreadsheet more intelligently based on your
//    // app's needs.
//    SpreadsheetEntry spreadsheet = spreadsheets.get(0);
//    System.out.println(spreadsheet.getTitle().getPlainText());
//
//    // Get the first worksheet of the first spreadsheet.
//    // TODO: Choose a worksheet more intelligently based on your
//    // app's needs.
//    WorksheetFeed worksheetFeed = service.getFeed(
//        spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
//    List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
//    WorksheetEntry worksheet = worksheets.get(0);
//
//    // Fetch the list feed of the worksheet.
//    URL listFeedUrl = worksheet.getListFeedUrl();
//    ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);
//
//    // Create a local representation of the new row.
//    ListEntry row = new ListEntry();
//    row.getCustomElements().setValueLocal("SiteID", "www.facebook.com");
//    row.getCustomElements().setValueLocal("Username", "Smith");
//    row.getCustomElements().setValueLocal("Password", "27");
//   
//    
//   
//
//    // Send the new row to the API for insertion.
//    row = service.insert(listFeedUrl, row);

  }
}