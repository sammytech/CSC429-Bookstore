package userinterface;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Book;

import java.util.Calendar;
import java.util.Properties;

/**
 * Created by Sammytech on 2/21/17.
 */
public class NewBookView extends View {

    // GUI components
    protected TextField author;
    protected TextField title;
    protected TextField pubYear;
    protected ComboBox status;

    protected Button submitButton;
    protected Button doneButton;

    // For showing error message
    protected MessageView statusLog;

    public NewBookView(IModel book) {
        super(book, "NewBookView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(TitleView.createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());
//
        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        myModel.subscribe("UpdateStatusMessage", this);
    }

    //--------------------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }


    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }


    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

    private VBox createFormContent() {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("NEW BOOK");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text authorLabel = new Text(" Author : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        authorLabel.setFont(myFont);
        authorLabel.setWrappingWidth(150);
        authorLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(authorLabel, 0, 1);

        author = new TextField();
        grid.add(author, 1, 1);

        Text titleLabel = new Text(" Title : ");
        titleLabel.setFont(myFont);
        titleLabel.setWrappingWidth(150);
        titleLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(titleLabel, 0, 2);

        title = new TextField();
        grid.add(title, 1, 2);

        Text pubYearLabel = new Text(" Publication Year : ");
        pubYearLabel.setFont(myFont);
        pubYearLabel.setWrappingWidth(150);
        pubYearLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(pubYearLabel, 0, 3);

        pubYear = new TextField();
        pubYear.setPromptText("YYYY");
        grid.add(pubYear, 1, 3);

        Text statusLabel = new Text(" Status : ");
        statusLabel.setFont(myFont);
        statusLabel.setWrappingWidth(150);
        statusLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(statusLabel, 0, 4);

        status = new ComboBox();
        status.getItems().addAll("Active", "Inactive");
        status.setValue("Active");

        grid.add(status, 1, 4);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        doneButton = new Button("DONE");
        doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton = new Button("SUBMIT");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createBook();
            }
        });
        doneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("NewBookCancelled", null);
            }
        });
        doneCont.getChildren().add(doneButton);
        doneCont.getChildren().add(submitButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);

        return vbox;
    }

    private void disableFields(){
        author.setEditable(false);
        title.setEditable(false);
        pubYear.setEditable(false);
        status.setEditable(false);
        submitButton.setDisable(true);
    }

    private void createBook() {
        Properties newBook = new Properties();
        String authorValue = author.getText();
        String titleValue = title.getText();
        String pubYearValue = pubYear.getText();
        String statusValue = status.getSelectionModel().getSelectedItem().toString();
        if(authorValue.isEmpty()){
            String message = "Author cannot be empty";
            displayErrorMessage(message);
            return;
        } else if(titleValue.isEmpty()){
            String message = "Title cannot be empty";
            displayErrorMessage(message);
            return;
        } else {
            try {
                int year = Integer.parseInt(pubYearValue);
                int curYear = Calendar.getInstance().get(Calendar.YEAR);
                if(year < 1800 || year > curYear) {
                    String message = "Publication year must be between 1800 and "+curYear+" inclusively";
                    displayErrorMessage(message);
                    return;
                }
            } catch (NumberFormatException ex){
                String message = "Publication year must be a number";
                displayErrorMessage(message);
                return;
            }
        }
        newBook.setProperty("author", authorValue);
        newBook.setProperty("title", titleValue);
        newBook.setProperty("pubYear", pubYearValue);
        newBook.setProperty("status", statusValue);
        myModel.stateChangeRequest("ProcessNewBook", newBook);

    }

    @Override
    public void updateState(String key, Object value) {
        if(key.equals("UpdateStatusMessage")){
            String val = (String) value;
            if((boolean)myModel.getState("SuccessFlag")){
                displayMessage(val);
                disableFields();
            } else {
                displayErrorMessage(val);
            }


        }
    }
}
