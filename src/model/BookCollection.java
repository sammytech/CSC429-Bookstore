package model;

import java.util.Properties;
import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import impresario.IView;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

public class BookCollection extends EntityBase implements IView {

	private static final String myTableName = "Book";

	private Vector<Book> books;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public BookCollection()
	{
		super(myTableName);

		books = new Vector<Book>();


	}

	private void retrieveHelper(String query) throws InvalidPrimaryKeyException{
		Vector allDataRetrieved = getSelectQueryResult(query);

		if (allDataRetrieved != null)
		{
			books = new Vector<Book>();

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
			{
				Properties nextPatronData = (Properties)allDataRetrieved.elementAt(cnt);

				Book book = new Book(nextPatronData);
				if (book != null)
				{
					books.add(book);
				}
			}

		}
		else
		{
			books = new Vector<Book>();
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
		String query = "SELECT * FROM " + myTableName + " WHERE (title LIKE '%" + title + "%')";
		retrieveHelper(query);

	}

	public void findBooksWithAuthorLike(String author) throws InvalidPrimaryKeyException{
		String query = "SELECT * FROM " + myTableName + " WHERE (author LIKE %" + author + "%)";
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
	public Book retrieve(String bookId)
	{
		Book retValue = null;
		for (int cnt = 0; cnt < books.size(); cnt++)
		{
			Book nextAcct = books.elementAt(cnt);
			String nextAccNum = (String)nextAcct.getState("bookId");
			if (nextAccNum.equals(bookId) == true)
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

	protected void createAndShowView()
	{
		Scene currentScene = myViews.get("BookCollectionView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("BookCollectionView", this);
			currentScene = new Scene(newView);
			myViews.put("BookCollectionView", currentScene);
		}
		swapToView(currentScene);
	}


	@Override
	public void updateState(String key, Object value) {
		stateChangeRequest(key, value);
	}
}
