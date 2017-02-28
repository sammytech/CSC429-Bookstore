package userinterface;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class LibrarianView extends View {


	public LibrarianView(IModel model) {
		super(model, "LibrarianView");
		
		// create a container for showing the contents
		VBox container = new VBox(10);

		container.setPadding(new Insets(15, 5, 5, 5));

		// create a Node (Text) for showing the title
		container.getChildren().add(TitleView.createTitle());

		// create a Node (GridPane) for showing data entry fields
		container.getChildren().add(createNavigationButtons());

		// Error message area
//		container.getChildren().add(createStatusLog("                          "));

		getChildren().add(container);



		// STEP 0: Be sure you tell your model what keys you are interested in
//		myModel.subscribe("LoginError", this);
	}
		// Create the main form contents
		//-------------------------------------------------------------
		private VBox createNavigationButtons()
		{
			VBox nav = new VBox();
            nav.setSpacing(10);
			Button newBook = new Button("INSERT NEW BOOK");
			newBook.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    myModel.stateChangeRequest("NewBook", null);
                }
            });

			Button newPatron = new Button("INSERT NEW PATRON");
            newPatron.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    myModel.stateChangeRequest("NewPatron", null);
                }
            });
			Button searchBook = new Button("SEARCH BOOKS");
			searchBook.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    myModel.stateChangeRequest("SearchBooks", null);
                }
            });
			Button searchPatrons = new Button("SEARCH PATRONS");
            searchPatrons.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    myModel.stateChangeRequest("SearchPatrons", null);
                }
            });

			Button done = new Button("DONE");
			done.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Thanks for using Brockport's Library");
                    System.exit(0);
                }
            });
			nav.getChildren().addAll(newBook, newPatron, searchBook, searchPatrons);
			nav.setAlignment(Pos.CENTER);

			VBox parentNav = new VBox();
            parentNav.setPadding(new Insets(10));
			parentNav.setSpacing(50);
			parentNav.getChildren().add(nav);
			parentNav.getChildren().add(done);
			parentNav.setAlignment(Pos.CENTER);
			return parentNav;
		}

	@Override
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
		
	}
	
	

}
