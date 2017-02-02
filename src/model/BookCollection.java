package model;

import java.util.Properties;
import java.util.Vector;

import exception.InvalidPrimaryKeyException;

public class BookCollection extends EntityBase {

	private static final String myTableName = "Book";

	private Vector<Patron> books;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public BookCollection( Patron patron) throws
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

		books = new Vector<Patron>();
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
	
	private void retrieveHelper(String query) throws InvalidPrimaryKeyException{
		Vector allDataRetrieved = getSelectQueryResult(query);
		
		if (allDataRetrieved != null)
		{
			books = new Vector<Patron>();
	
			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
			{
				Properties nextPatronData = (Properties)allDataRetrieved.elementAt(cnt);
	
				Patron patron = new Patron(nextPatronData);
	
				if (patron != null)
				{
					books.add(patron);
				}
			}
	
		}
		else
		{
			books = new Vector<Patron>();
			throw new InvalidPrimaryKeyException("No item found");
			
		}
	}
	

	public void findBooksOlderThanDate(String year) throws InvalidPrimaryKeyException{
		String query = "SELECT * FROM " + myTableName + " WHERE (pubYear <= " + year + ")";
		retrieveHelper(query);
		
	}
	
	public void findBooksNewerThanDate(String year) throws InvalidPrimaryKeyException{
		String query = "SELECT * FROM " + myTableName + " WHERE (pubYear >= " + year + ")";
		retrieveHelper(query);
		
	}
	
	public void findBooksWithTitleLike(String title) throws InvalidPrimaryKeyException{
		String query = "SELECT * FROM " + myTableName + " WHERE (title LIKE " + title + ")";
		retrieveHelper(query);
		
	}
	
	public void findBooksWithAuthorLike(String author) throws InvalidPrimaryKeyException{
		String query = "SELECT * FROM " + myTableName + " WHERE (author LIKE " + author + ")";
		retrieveHelper(query);
		
	}



	/**
	 *
	 */
	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("Books"))
			return books;
		else
		if (key.equals("BookList"))
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
		for (int cnt = 0; cnt < books.size(); cnt++)
		{
			Patron nextAcct = books.elementAt(cnt);
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
