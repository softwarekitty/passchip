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
	

	
	//can be used to setup a new community
	//requires you to create a workbook in google sheets and supply the ID
	//community name is some user supplied name eg:northcrest
	//community ID auto generated-16 digits long
	public long createCommunity(String communityName, String uname, String pswd, String sheetsID)
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
	public List<Entity> getCommunities()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("Community");
		List<Entity> communities = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
		return communities;
		
	}
	
	//setup a new user
	public String createUser(String chipID, String communityname, String sheetID, int flag)
	{
		long communityID = getcommunityID(communityname);
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
	public int verifyUser(String chipID)
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
	
	public Entity getCommunity(String communityname) throws EntityNotFoundException{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Long communityID = new Long(getcommunityID(communityname));
		Key createdKey = KeyFactory.createKey("Community", communityID);
		return datastore.get(createdKey);
	}
	
	
	//if entity exists returns entity else returns null
	public Entity getUser(String chipID)
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
	

	public long getcommunityID(String communityname)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    //Filter keyFilter = new FilterPredicate("chipID",FilterOperator.EQUAL,chipID);
		//Query query =  new Query("Person").setFilter(keyFilter);
		//Entity result = datastore.prepare(query).asSingleEntity();
		Filter propertyFilter =
				  new FilterPredicate("communityName",
						  FilterOperator.EQUAL,
				                      communityname);
				Query q = new Query("Community").setFilter(propertyFilter);
				try{
				Entity result = datastore.prepare(q).asSingleEntity();
				if(result!=null)
				{
					return result.getKey().getId();
				}
				else
				{
					return -1;
				}
				}
				catch(Exception E)
				{
					return -1;
				}
	}
	
	//to change user parameters
	//just getthe user modify the requiredparameters and put it back in datastore.
}
