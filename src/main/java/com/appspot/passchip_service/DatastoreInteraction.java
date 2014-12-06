package com.appspot.passchip_service;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;

import java.util.Date;
import java.util.List;

public class DatastoreInteraction {

	/*
	 * NOTICE: this is just here for reference.  The whole project will only use PasschipServlet.java
	 */
	
	void insertGreeting(String guestbookname,String content)
	{
	
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();

    //String guestbookName = req.getParameter("guestbookName");
    Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookname);
    //String content = req.getParameter("content");
    Date date = new Date();
    Entity greeting = new Entity("Greeting", guestbookKey);
    greeting.setProperty("user", user);
    greeting.setProperty("date", date);
    greeting.setProperty("content", content);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(greeting);
	}
	
	//can be used to setup a new community
	//requires you to create a workbook in google sheets and supply the ID
	//community name is some user supplied name eg:northcrest
	//community ID auto generated-16 digits long
	long createCommunity(String communityName, String uname, String pswd, String sheetsID)
	{
		Entity community=new Entity("Community");
		community.setProperty("communityName", communityName);
		community.setProperty("username",uname);
		community.setProperty("password", pswd);
		community.setProperty("sheetsID",sheetsID);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    datastore.put(community);
	    return community.getKey().getId();
			
	}
	
	//to list all available communities 
	//may be used to display the available communities when we setup a user
	//so user can select which community to be added to	
	List<Entity> getCommunities()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("Community");
		List<Entity> communities = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
		return communities;
		
	}
	
	//setup a new user
	String createUser(String chipID,long communityID, String sheetID, int flag)
	{
		Entity chipUser=new Entity("chipUser",chipID);
		chipUser.setProperty("communityID", communityID);
		chipUser.setProperty("sheetID",sheetID);
		chipUser.setProperty("flag", flag);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    datastore.put(chipUser);
	    return chipUser.getKey().getName();
	}
	
	//verify ID
	//returns 1 if exists
	int verifyUser(String chipID)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key k = KeyFactory.createKey("chipUser", chipID);
		Entity result;
		try {
			result = datastore.get(k);
		} catch (EntityNotFoundException e) {
			return 0;
		}
		return 1;
		
		
	}
	
	
	//if entity exists returns entity else returns null
	Entity getUser(String chipID)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    //Filter keyFilter = new FilterPredicate("chipID",FilterOperator.EQUAL,chipID);
		//Query query =  new Query("Person").setFilter(keyFilter);
		//Entity result = datastore.prepare(query).asSingleEntity();
		Key k = KeyFactory.createKey("chipUser", chipID);
		Entity result;
		try {
			result = datastore.get(k);
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return result;
	}
	
	//to change user parameters
	//just getthe user modify the requiredparameters and put it back in datastore.
}
