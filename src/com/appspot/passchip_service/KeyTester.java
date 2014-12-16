package com.appspot.passchip_service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.PlainTextConstruct;
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
		// spreadsheetService.setProtocolVersion(SpreadsheetService.Versions.V1);
		String chipID = "1436789787788";
		String key = "1FnDRWz4CjUJwatYG6gn7P_5hQH__pVqEDpvLub4gJ6M";
		SpreadsheetEntry spreadsheet = getSpreadsheetWithKey(key,
				spreadsheetService);
//		WorksheetEntry newWorksheet = new WorksheetEntry();
//		newWorksheet.setTitle(new PlainTextConstruct(chipID));
//		newWorksheet.setColCount(3);
//		newWorksheet.setRowCount(20);
//
//		URL worksheetFeedUrl = spreadsheet.getWorksheetFeedUrl();
//		spreadsheetService.insert(worksheetFeedUrl, newWorksheet);

//		System.out.println("new worksheet id: " + newWorksheet.getId());
//		WorksheetFeed worksheetFeed = spreadsheetService.getFeed(
//				spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
//		System.out.println("success getting spreadsheet with key: " + key);
//
//		List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
//		for (WorksheetEntry worksheet : worksheets) {
//			
////			String sm = worksheet.getTitle().getPlainText();
////			if (sm.toString().endsWith(chipID))
////				System.out.println("ID: " + worksheet.getId());
//		}
//		System.out.println("done");

		 WorksheetFeed worksheetFeed = spreadsheetService.getFeed(
		 spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
		 System.out.println("success getting spreadsheet with key: " + key);
		
		 List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
		 for (WorksheetEntry worksheet : worksheets){
		// String id = worksheet.getId();
		// System.out.println("ID: " + id);
		//
		// String idPattern = "(.*)%(.*)";
		// Pattern p = Pattern.compile(idPattern);
		// System.out.println(p.toString());
		// Matcher m = p.matcher(documentListEntry.getId());
		// m.find();
		// System.out.println(m.toString());
		//
		// System.out.println(documentListEntry.getId());
		// String id = m.group(2);
		// if(id.startsWith("3A")){
		// id = id.substring(2);
		// }
		// System.out.println(id);
		//
			 String target = "https://spreadsheets.google.com/feeds/worksheets/1FnDRWz4CjUJwatYG6gn7P_5hQH__pVqEDpvLub4gJ6M/od0sh5t";
			 if(worksheet.getId().equals(target)){
				 URL cellFeedUrl = worksheet.getCellFeedUrl();
				 CellFeed cellFeed = spreadsheetService.getFeed(cellFeedUrl,
				 CellFeed.class);
				 System.out.println("cell feed nEntries: " +
				 cellFeed.getEntries().size());
				
				
				 // Iterate through each cell, printing its value.
				 for (CellEntry cell : cellFeed.getEntries()) {
				 // Print the cell's address in A1 notation
				 System.out.print(cell.getTitle().getPlainText() + "\t");
				 // Print the cell's address in R1C1 notation
				 System.out.print(cell.getId().substring(cell.getId().lastIndexOf('/')
				 + 1) + "\t");
				 // Print the cell's formula or text value
				 System.out.print(cell.getCell().getInputValue() + "\t");
				 // Print the cell's calculated value if the cell's value is numeric
				 // Prints empty string if cell's value is not numeric
				 System.out.print(cell.getCell().getNumericValue() + "\t");
				 // Print the cell's displayed value (useful if the cell has a
				 //formula)
				 System.out.println(cell.getCell().getValue() + "\t");
				 }
			 }
		
		 }

		// Get the first worksheet of the first spreadsheet.
		// // TODO: Choose a worksheet more intelligently based on your
		// // app's needs.
		// WorksheetFeed worksheetFeed = service.getFeed(
		// spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
		// List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
		// WorksheetEntry worksheet = worksheets.get(0);
		//
		// String y = worksheet.getId();
		// System.out.println(worksheet.getId());
		//
		// // Update the local representation of the worksheet.
		// worksheet.setTitle(new PlainTextConstruct("Updated Worksheet"));
		// worksheet.setColCount(5);
		// worksheet.setRowCount(15);
		//
		// // Send the local representation of the worksheet to the API for
		// // modification.
		// worksheet.update();
	}

	/**
	 * Returns the SpreadsheetEntry for the spreadsheet with the given key.
	 * 
	 * @throws IOException
	 *             If a network error occurs while trying to communicate with
	 *             Spreadsheets
	 * @throws ServiceException
	 *             If an application-level protocol error occurs while trying to
	 *             communicate with Spreadsheets
	 */
	private static SpreadsheetEntry getSpreadsheetWithKey(String key,
			SpreadsheetService spreadsheetService) throws IOException,
			ServiceException {
		URL metafeedUrl = new URL(
				"http://spreadsheets.google.com/feeds/spreadsheets/private/full");
		SpreadsheetFeed spreadsheetFeed = spreadsheetService.getFeed(
				metafeedUrl, SpreadsheetFeed.class);

		List<SpreadsheetEntry> spreadsheets = spreadsheetFeed.getEntries();
		for (SpreadsheetEntry spreadsheet : spreadsheets) {
			System.out.println("found spreadsheet with key: "
					+ spreadsheet.getKey());
			if (spreadsheet.getKey().equals(key)) {
				return spreadsheet;
			}
		}
		throw new IllegalStateException(
				"You don't have access to a spreadsheet with key " + key);
	}

}
