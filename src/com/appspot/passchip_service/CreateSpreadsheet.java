package com.appspot.passchip_service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gdata.client.docs.DocsService;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class CreateSpreadsheet {
	 public static void main(String[] args)
     		throws AuthenticationException, MalformedURLException, IOException, ServiceException 
     {

         SpreadsheetService service =   new SpreadsheetService("gBuddy");

         // TODO: Authorize the service object for a specific user (see other sections)
         String USERNAME = "passchip514@gmail.com";
         String PASSWORD = "gocyclone";

         service.setUserCredentials(USERNAME, PASSWORD);


         // Define the URL to request.  This should never change.
         URL SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");

         // Make a request to the API and get all spreadsheets.
         SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
         List<SpreadsheetEntry> spreadsheets = feed.getEntries();

         // Iterate through all of the spreadsheets returned
 //        for (SpreadsheetEntry spreadsheet : spreadsheets) 
 //        {
         //Print the title of this spreadsheet to the screen;
 //            System.out.println(spreadsheet.getTitle().getPlainText());
 //        }

         
 //        SpreadsheetEntry spreadsheet = spreadsheets.get(1);
         //System.out.println(spreadsheet.getTitle().getPlainText());

         // Create a local representation of the new worksheet.
   //       WorksheetEntry worksheet = new WorksheetEntry();
   //       worksheet.setTitle(new PlainTextConstruct("New Worksheet4"));
   //       worksheet.setColCount(10);
   //       worksheet.setRowCount(20);

         // Send the local representation of the worksheet to the API for
         // creation.  The URL to use here is the worksheet feed URL of our
         // spreadsheet.
  //       URL worksheetFeedUrl = spreadsheet.getWorksheetFeedUrl();
  //       WorksheetEntry insert = service.insert(worksheetFeedUrl, worksheet);
         
         
         
         DocsService docsService = new DocsService("MySampleApplication-v3");
         docsService.setUserCredentials(USERNAME, PASSWORD);
         URL GOOGLE_DRIVE_FEED_URL = new URL("https://docs.google.com/feeds/default/private/full/");
         DocumentListEntry documentListEntry = new com.google.gdata.data.docs.SpreadsheetEntry();
         documentListEntry.setTitle(new PlainTextConstruct("Test5"));
         documentListEntry = docsService.insert(GOOGLE_DRIVE_FEED_URL, documentListEntry);
         System.out.println("Create a new Spreadsheet successfully!");
         String idPattern = "(.*)%(.*)";
         Pattern p = Pattern.compile(idPattern);
         System.out.println(p.toString());
         Matcher m = p.matcher(documentListEntry.getId());
         m.find();
         System.out.println(m.toString());

         System.out.println(documentListEntry.getId());
         String id = m.group(2);
         if(id.startsWith("3A")){
        	 id = id.substring(2);
         }
         System.out.println(id);

         
     }
 }