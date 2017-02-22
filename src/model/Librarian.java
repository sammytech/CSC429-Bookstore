package model;

import java.util.Hashtable;
import java.util.Properties;

import event.Event;
import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

public class Librarian implements IView, IModel
//This class implements all these interfaces (and does NOT extend 'EntityBase')
//because it does NOT play the role of accessing the back-end database tables.
//It only plays a front-end role. 'EntityBase' objects play both roles.
{
	// For Impresario
	private Properties dependencies;
	private ModelRegistry myRegistry;

//	private AccountHolder myAccountHolder;

	// GUI Components
	private Hashtable<String, Scene> myViews;
	private Stage	  	myStage;

//	private String loginErrorMessage = "";
//	private String transactionErrorMessage = "";

    private TextField titleToSearch;
    private TextField zipToSearch;

	// constructor for this class
	//----------------------------------------------------------
	public Librarian()
	{
		myStage = MainStageContainer.getInstance();
		myViews = new Hashtable<String, Scene>();

		// STEP 3.1: Create the Registry object - if you inherit from
		// EntityBase, this is done for you. Otherwise, you do it yourself
		myRegistry = new ModelRegistry("Librarian");
		if(myRegistry == null)
		{
			new Event(Event.getLeafLevelClassName(this), "Librarian",
				"Could not instantiate Registry", Event.ERROR);
		}

		// STEP 3.2: Be sure to set the dependencies correctly
		setDependencies();

		// Set up the initial view
		createAndShowLibrarianView();
	}

	//-----------------------------------------------------------------------------------
	private void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("NewBook", "NewBookError");
		dependencies.setProperty("NewPatron", "NewPatronError");
		dependencies.setProperty("SearchBook", "SearchBookError");
		dependencies.setProperty("SearchPatron", "SearchPatronError");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * Method called from client to get the value of a particular field
	 * held by the objects encapsulated by this object.
	 *
	 * @param	key	Name of database column (field) for which the client wants the value
	 *
	 * @return	Value associated with the field
	 */
	//----------------------------------------------------------
	public Object getState(String key)
	{
		return "";
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		// STEP 4: Write the sCR method component for the key you
		// just set up dependencies for
		// DEBUG System.out.println("Teller.sCR: key = " + key);

        if (key.equals("NewBook"))
        {
            createNewBook();
        } else if (key.equals("NewPatron"))
        {
            createNewPatron();
        } else if (key.equals("SearchBooks"))
        {
            createAndShowSearchBookView();
        } else if (key.equals("SearchPatrons"))
        {
            createAndShowSearchPatronView();
        }else if (key.equals("ViewCancelled"))
        {
            createAndShowLibrarianView();
        }

		myRegistry.updateSubscribers(key, this);
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		// DEBUG System.out.println("Teller.updateState: key: " + key);

		stateChangeRequest(key, value);
	}


	

	//------------------------------------------------------------
	private void createAndShowLibrarianView()
	{
		Scene currentScene = (Scene)myViews.get("LibrarianView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("LibrarianView", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("LibrarianView", currentScene);
		}
				
		swapToView(currentScene);
		
	}

    private void createAndShowSearchBookView()
    {

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(30));

        titleToSearch = new TextField();
        titleToSearch.setPrefWidth(300);
        HBox buttonBox = new HBox();
        buttonBox.setPadding(new Insets(20));
        buttonBox.setAlignment(Pos.CENTER);

        Button back = new Button("Back");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createAndShowLibrarianView();
            }
        });

        Button search = new Button("Search");
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchBooks();
            }
        });
        buttonBox.getChildren().addAll(back, search);

        vBox.getChildren().addAll(titleToSearch, buttonBox);

        Scene scene = new Scene(vBox);
        swapToView(scene);

    }

    private void searchBooks() {
	    BookCollection bookCollection = new BookCollection();
	    String title = titleToSearch.getText();
        try {
            bookCollection.findBooksWithTitleLike(title);
        } catch (InvalidPrimaryKeyException e) {
            e.printStackTrace();
        }
        bookCollection.subscribe("ViewCancelled", this);
	    bookCollection.createAndShowView();



    }

    private void createAndShowSearchPatronView()
    {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(30));

        zipToSearch = new TextField();
        zipToSearch.setPrefWidth(300);
        HBox buttonBox = new HBox();
        buttonBox.setPadding(new Insets(20));
        buttonBox.setAlignment(Pos.CENTER);

        Button back = new Button("Back");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createAndShowLibrarianView();
            }
        });

        Button search = new Button("Search");
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchPatrons();
            }
        });
        buttonBox.getChildren().addAll(back, search);

        vBox.getChildren().addAll(zipToSearch, buttonBox);

        Scene scene = new Scene(vBox);
        swapToView(scene);

    }

    private void searchPatrons() {
        PatronCollection patronCollection = new PatronCollection();
        String zip = zipToSearch.getText();

        patronCollection.findPatronsAtZipCode(zip);

        patronCollection.subscribe("ViewCancelled", this);
        patronCollection.createAndShowView();

    }
	private void createNewBook(){
        Book book = new Book(new Properties());
        book.subscribe("ViewCancelled", this);
        Scene currentScene = book.createView();

        swapToView(currentScene);
	}

    private void createNewPatron(){
        Patron patron = new Patron(new Properties());
        patron.subscribe("ViewCancelled", this);
        Scene currentScene = patron.createView();

        swapToView(currentScene);
    }

	/** Register objects to receive state updates. */
	//----------------------------------------------------------
	public void subscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
		// forward to our registry
		myRegistry.subscribe(key, subscriber);
	}

	/** Unregister previously registered objects. */
	//----------------------------------------------------------
	public void unSubscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager.unSubscribe");
		// forward to our registry
		myRegistry.unSubscribe(key, subscriber);
	}



	//-----------------------------------------------------------------------------
	public void swapToView(Scene newScene)
	{

		
		if (newScene == null)
		{
			System.out.println("Librarian.swapToView(): Missing view for display");
			new Event(Event.getLeafLevelClassName(this), "swapToView",
				"Missing view for display ", Event.ERROR);
			return;
		}

		myStage.setScene(newScene);
		myStage.sizeToScene();
		
			
		//Place in center
		WindowPosition.placeCenter(myStage);

	}

}


