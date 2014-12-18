package com.appspot.passchip_service;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;

public class TestCreateUserSheet {

	public static void main(String[] args) throws IOException, ServiceException {
		// TODO Auto-generated method stub

		String USERNAME = "passchip514@gmail.com";
		String PASSWORD = "gocyclone";
		String bookID = "1FnDRWz4CjUJwatYG6gn7P_5hQH__pVqEDpvLub4gJ6M";
		String chipID = "SivaCat";
		String usersheetID = "";

		// code for sheets creation
		SpreadsheetService spreadsheetService = new SpreadsheetService(
				"passchip-service");
		spreadsheetService.setUserCredentials(USERNAME, PASSWORD);

		SpreadsheetEntry spreadsheet = getBookWithID(bookID, spreadsheetService);
		WorksheetEntry newWorksheet = new WorksheetEntry();
		newWorksheet.setTitle(new PlainTextConstruct(chipID));
		newWorksheet.setColCount(3);
		newWorksheet.setRowCount(20);
		System.out.println("h1");

		URL worksheetFeedUrl = spreadsheet.getWorksheetFeedUrl();
		spreadsheetService.insert(worksheetFeedUrl, newWorksheet);

		WorksheetFeed worksheetFeed = spreadsheetService.getFeed(
				spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
		List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
		for (WorksheetEntry worksheet : worksheets) {

			String sm = worksheet.getTitle().getPlainText();
			if (sm.toString().equals(chipID)) {
				System.out.println("ID: " + worksheet.getId());
				usersheetID = worksheet.getId();
			}
		}

		WorksheetEntry newUserSheet = getSheetWithID(usersheetID, worksheetFeed);
		// Write header line into Spreadsheet
		URL cellFeedUrl = newUserSheet.getCellFeedUrl();
		CellFeed cellFeed = spreadsheetService.getFeed(cellFeedUrl,
				CellFeed.class);

		CellEntry cellEntry1 = new CellEntry(1, 1, "headline1");
		cellFeed.insert(cellEntry1);
		CellEntry cellEntry2 = new CellEntry(1, 2, "headline2");
		cellFeed.insert(cellEntry2);
		CellEntry cellEntry3 = new CellEntry(1, 3, "headline3");
		cellFeed.insert(cellEntry3);
		System.out.println("added cell headers for sheet with title: " + chipID);
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
