package com.appspot.passchip_service;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;

public class KeyTester {
	
	private static final String APP_NAME = "passchip-service";

	public static void main(String[] args) throws IOException, ServiceException {
		
        String USERNAME = "passchip514@gmail.com";
        String PASSWORD = "gocyclone";
	    SpreadsheetService spreadsheetService = new SpreadsheetService(APP_NAME);
	    spreadsheetService.setUserCredentials(USERNAME, PASSWORD);
	    // Workaround for Google Data APIs Java client issue #103
	    // (http://code.google.com/p/gdata-java-client/issues/detail?id=103)
	    //spreadsheetService.setProtocolVersion(SpreadsheetService.Versions.V1);
		
		
		String key = "1l5c0gLWhitOTTw5nF8P_aRUfFwB9hVpYIBwbmUd-IH4";
		SpreadsheetEntry spreadsheet = getSpreadsheetWithKey(key, spreadsheetService);
		
	    WorksheetFeed worksheetFeed = spreadsheetService.getFeed(
	            spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
	    		System.out.println("success getting spreadsheet with key: " + key);

	        List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
	        WorksheetEntry worksheet = worksheets.get(0);
	        System.out.println("worksheet cell feed: " + worksheet.getCellFeedUrl());
	        // Fetch the cell feed of the worksheet.
	        URL cellFeedUrl = worksheet.getCellFeedUrl();
	        CellFeed cellFeed = spreadsheetService.getFeed(cellFeedUrl, CellFeed.class);
	        System.out.println("cell feed nEntries: " + cellFeed.getEntries().size());


	        // Iterate through each cell, printing its value.
	        for (CellEntry cell : cellFeed.getEntries()) {
	          // Print the cell's address in A1 notation
	          System.out.print(cell.getTitle().getPlainText() + "\t");
	          // Print the cell's address in R1C1 notation
	          System.out.print(cell.getId().substring(cell.getId().lastIndexOf('/') + 1) + "\t");
	          // Print the cell's formula or text value
	          System.out.print(cell.getCell().getInputValue() + "\t");
	          // Print the cell's calculated value if the cell's value is numeric
	          // Prints empty string if cell's value is not numeric
	          System.out.print(cell.getCell().getNumericValue() + "\t");
	          // Print the cell's displayed value (useful if the cell has a formula)
	          System.out.println(cell.getCell().getValue() + "\t");
	        }

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
	
}


