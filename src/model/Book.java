package model;

import java.util.Properties;

public class Book extends EntityBase {

	private static final String myTableName = "Book";

	protected Properties dependencies;

	// GUI Components

	private String updateStatusMessage = "";
	
	public Book(String tablename) {
		super(tablename);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getState(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initializeSchema(String tableName) {
		// TODO Auto-generated method stub

	}

}
