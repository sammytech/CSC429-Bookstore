package model;

import java.util.Properties;
import java.util.Vector;

import event.Event;
import exception.InvalidPrimaryKeyException;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

public class PatronCollection extends EntityBase {

	private static final String myTableName = "Patron";

	private Vector<Patron> patrons;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public PatronCollection( Patron patron) throws
		Exception
	{
		super(myTableName);

//		if (patron == null)
//		{
//			new Event(Event.getLeafLevelClassName(this), "<init>",
//				"Missing account holder information", Event.FATAL);
//			throw new Exception
//				("UNEXPECTED ERROR: PatronCollection.<init>: patron information is null");
//		}
//
//		String patronId = (String)patron.getState("patronId");
//
//		if (patronId == null)
//		{
//			new Event(Event.getLeafLevelClassName(this), "<init>",
//				"Data corrupted: Patron Holder has no id in database", Event.FATAL);
//			throw new Exception
//			 ("UNEXPECTED ERROR: PatronCollection.<init>: Data corrupted: account holder has no id in repository");
//		}
//
//		String query = "SELECT * FROM " + myTableName + " WHERE (patronId = " + patronId + ")";
//
//		Vector allDataRetrieved = getSelectQueryResult(query);

		patrons = new Vector<Patron>();
//		if (allDataRetrieved != null)
//		{
//			patrons = new Vector<Patron>();
//
//			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
//			{
//				Properties nextPatronData = (Properties)allDataRetrieved.elementAt(cnt);
//
//				Patron account = new Patron(nextPatronData);
//
//				if (account != null)
//				{
//					add(account);
//				}
//			}
//
//		}
//		else
//		{
//			throw new InvalidPrimaryKeyException("No accounts for customer : "
//				+ patronId + ". Name : " + patron.getState("name"));
//		}

	}
	
	private void retrieveHelper(String query){
		Vector allDataRetrieved = getSelectQueryResult(query);
		
		if (allDataRetrieved != null)
		{
			patrons = new Vector<Patron>();
	
			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
			{
				Properties nextPatronData = (Properties)allDataRetrieved.elementAt(cnt);
	
				Patron patron = new Patron(nextPatronData);
	
				if (patron != null)
				{
					patrons.add(patron);
				}
			}
	
		}
		else
		{
//			throw new InvalidPrimaryKeyException("No accounts for customer : "
//				+ patronId + ". Name : " + patron.getState("name"));
			patrons = new Vector<Patron>();
		}
	}
	
	
	public void findPatronsOlderThan(String date){
		String query = "SELECT * FROM " + myTableName + " WHERE (dateOfBirth >= " + date + ")";
		retrieveHelper(query);
	}
	
	public void findPatronsYoungerThan(String date){
		String query = "SELECT * FROM " + myTableName + " WHERE (dateOfBirth <= " + date + ")";
		retrieveHelper(query);
		
	}
	
	public void findPatronsAtZipCode(String zip){
		String query = "SELECT * FROM " + myTableName + " WHERE (zip = " + zip + ")";
		retrieveHelper(query);
	
	}
	
	public void findPatronsWithNameLike(String name){
		String query = "SELECT * FROM " + myTableName + " WHERE (name LIKE " + name + ")";
		retrieveHelper(query);
	
	}



	/**
	 *
	 */
	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("Patrons"))
			return patrons;
		else
		if (key.equals("PatronList"))
			return this;
		return null;
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		
		myRegistry.updateSubscribers(key, this);
	}

	//----------------------------------------------------------
	public Patron retrieve(String patronId)
	{
		Patron retValue = null;
		for (int cnt = 0; cnt < patrons.size(); cnt++)
		{
			Patron nextAcct = patrons.elementAt(cnt);
			String nextAccNum = (String)nextAcct.getState("patronId");
			if (nextAccNum.equals(patronId) == true)
			{
				retValue = nextAcct;
				return retValue; // we should say 'break;' here
			}
		}

		return retValue;
	}


	//-----------------------------------------------------------------------------------
	protected void initializeSchema(String tableName)
	{
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
		}
	}


}
