package com.appspot.passchip_service;





import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.*;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class WorksheetTitleSize {
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
    
    String y = worksheet.getId();
    System.out.println(worksheet.getId());

    // Update the local representation of the worksheet.
    worksheet.setTitle(new PlainTextConstruct("Updated Worksheet"));
    worksheet.setColCount(5);
    worksheet.setRowCount(15);

    // Send the local representation of the worksheet to the API for
    // modification.
    worksheet.update();
  }
}