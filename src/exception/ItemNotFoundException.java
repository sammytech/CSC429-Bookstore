package exception;

public class ItemNotFoundException extends Exception
{	
	/**
	 * Constructor with message
	 *
	 * @param mesg The message associated with the exception
	 */
	//--------------------------------------------------------
	public ItemNotFoundException(String message)
	{
		super(message);
	}
}

