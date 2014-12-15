package com.appspot.passchip_service;



import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.*;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class UpdateListRow {
	
	public void update (String website, String usr, String password, int index) throws IOException, ServiceException{
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
	    //worksheet.get
	    

	    // Fetch the list feed of the worksheet.
	    URL listFeedUrl = worksheet.getListFeedUrl();
	    ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);

	    // TODO: Choose a row more intelligently based on your app's needs.
	    ListEntry row = listFeed.getEntries().get(index);
	    

	    // Update the row's data.
	    row.getCustomElements().setValueLocal("SiteID", website);
	    row.getCustomElements().setValueLocal("Username", usr);
	    row.getCustomElements().setValueLocal("Password", password);

	    // Save the row using the API.
	    row.update();
	}
  public static void main(String[] args)
      throws AuthenticationException, MalformedURLException, IOException, ServiceException {

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

    // TODO: Choose a row more intelligently based on your app's needs.
    ListEntry row = listFeed.getEntries().get(0);
    

    // Update the row's data.
    row.getCustomElements().setValueLocal("firstname", "Sarah");
    row.getCustomElements().setValueLocal("lastname", "Hunt");
    row.getCustomElements().setValueLocal("age", "32");
    row.getCustomElements().setValueLocal("height", "154");

    // Save the row using the API.
    row.update();
  }
}