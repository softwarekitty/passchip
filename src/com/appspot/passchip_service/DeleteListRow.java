package com.appspot.passchip_service;




//import com.google.gdata.client.spreadsheet.*;
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

public class DeleteListRow {
	
	public void delete(String website, String usr, String password, int index) throws IOException, ServiceException{
		 SpreadsheetService service =new SpreadsheetService("MySpreadsheetIntegration-v1");

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
	    for(int i = 0; i < spreadsheets.size(); i++){
	    	if(spreadsheets.get(i).getTitle().getPlainText().equals("test1")) {
	    		spreadsheet = spreadsheets.get(i);
	    		break;
	    	}
	    }
	    //System.out.println(spreadsheet.getTitle().getPlainText());

	    // Get the first worksheet of the first spreadsheet.
	    // TODO: Choose a worksheet more intelligently based on your
	    // app's needs.
	    WorksheetFeed worksheetFeed = service.getFeed(
	        spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
	    List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
	   // worksheetFeed.
	    WorksheetEntry worksheet = worksheets.get(0);

	    // Fetch the list feed of the worksheet.
	    URL listFeedUrl = worksheet.getListFeedUrl();
	    ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);

	    // TODO: Choose a row more intelligently based on your app's needs.
	    ListEntry row = listFeed.getEntries().get(index);

	    // Delete the row using the API.
	    row.delete();
	}
  public static void main(String[] args)
      throws AuthenticationException, MalformedURLException, IOException, ServiceException {

	  SpreadsheetService service =new SpreadsheetService("MySpreadsheetIntegration-v1");

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

    // TODO: Choose a row more intelligently based on your app's needs.
    ListEntry row = listFeed.getEntries().get(0);

    // Delete the row using the API.
    row.delete();
  }
}