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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Sammytech on 2/21/17.
 */
public class NewPatronView extends View {

    protected TextField name;
    protected TextField address;
    protected TextField city;
    protected TextField stateCode;
    protected TextField zip;
    protected TextField email;
    protected TextField dateOfBirth;
    protected ComboBox status;

    protected Button submitButton;
    protected Button doneButton;

    // For showing error message
    protected MessageView statusLog;

    public NewPatronView(IModel patron) {
        super(patron, "NewPatronView");

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

    private VBox createFormContent() {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("NEW PATRON");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text nameLabel = new Text(" Name : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        nameLabel.setFont(myFont);
        nameLabel.setWrappingWidth(150);
        nameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(nameLabel, 0, 1);

        name = new TextField();
        grid.add(name, 1, 1);

        Text addressLabel = new Text(" Address : ");
        addressLabel.setFont(myFont);
        addressLabel.setWrappingWidth(150);
        addressLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(addressLabel, 0, 2);

        address = new TextField();
        grid.add(address, 1, 2);

        Text cityLabel = new Text(" City : ");
        cityLabel.setFont(myFont);
        cityLabel.setWrappingWidth(150);
        cityLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(cityLabel, 0, 3);

        city = new TextField();
        grid.add(city, 1, 3);

        Text stateCodeLabel = new Text(" State Code : ");
        stateCodeLabel.setFont(myFont);
        stateCodeLabel.setWrappingWidth(150);
        stateCodeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(stateCodeLabel, 0, 4);

//        stateCode = new ComboBox();
//        stateCode.getItems().addAll("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA",
//                 "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO",
//                "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI",
//                "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");
//        stateCode.setValue("NY");
        stateCode = new TextField();


        grid.add(stateCode, 1, 4);

        Text zipLabel = new Text(" Zip : ");
        zipLabel.setFont(myFont);
        zipLabel.setWrappingWidth(150);
        zipLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(zipLabel, 0, 5);

        zip = new TextField();
        grid.add(zip, 1, 5);

        Text emailLabel = new Text(" Email : ");
        emailLabel.setFont(myFont);
        emailLabel.setWrappingWidth(150);
        emailLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(emailLabel, 0, 6);

        email = new TextField();
        grid.add(email, 1, 6);

        Text dateOfBirthLabel = new Text(" Date of Birth : ");
        dateOfBirthLabel.setFont(myFont);
        dateOfBirthLabel.setWrappingWidth(150);
        dateOfBirthLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(dateOfBirthLabel, 0, 7);

        dateOfBirth = new TextField();
        dateOfBirth.setPromptText("yyyy-MM-dd");
        grid.add(dateOfBirth, 1, 7);


        Text statusLabel = new Text(" Status : ");
        statusLabel.setFont(myFont);
        statusLabel.setWrappingWidth(150);
        statusLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(statusLabel, 0, 8);

        status = new ComboBox();
        status.getItems().addAll("Active", "Inactive");
        status.setValue("Active");
        grid.add(status, 1, 8);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        doneButton = new Button("DONE");
        doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton = new Button("SUBMIT");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createPatron();
            }
        });
        doneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
//                clearErrorMessage();
                myModel.stateChangeRequest("NewPatronCancelled", null);
            }
        });
        doneCont.getChildren().add(doneButton);
        doneCont.getChildren().add(submitButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);

        return vbox;
    }

    private void createPatron() {
        Properties newPatron = new Properties();

        String nameText = name.getText();
        String addressText = address.getText();
        String cityText = city.getText();
        String stateCodeText = stateCode.getText(); //stateCode.getSelectionModel().getSelectedItem().toString();
        String zipText = zip.getText();
        String emailText = email.getText();
        String dateOfBirthText = dateOfBirth.getText();
        String statusText = status.getSelectionModel().getSelectedItem().toString();

        if(nameText.isEmpty()){
            String message = "Name cannot be empty";
            displayErrorMessage(message);
            return;
        } else if(addressText.isEmpty()){
            String message = "Address cannot be empty";
            displayErrorMessage(message);
            return;
        } else if(cityText.isEmpty()){
            String message = "City cannot be empty";
            displayErrorMessage(message);
            return;
        } else if(stateCodeText.isEmpty()){
            String message = "State Code cannot be empty";
            displayErrorMessage(message);
            return;
        } else if(zipText.isEmpty()){
            String message = "Zip cannot be empty";
            displayErrorMessage(message);
            return;
        } else if(emailText.isEmpty()){
            String message = "Email cannot be empty";
            displayErrorMessage(message);
            return;
        } else {
            SimpleDateFormat formatter2 =
                    new SimpleDateFormat("yyyy-MM-dd");
            formatter2.setLenient(false);
            try
            {
                Date theDate = formatter2.parse(dateOfBirthText);
                Date firstDate = formatter2.parse("1917-01-01");
                Date secondDate = formatter2.parse("1999-01-01");
                if(theDate.compareTo(firstDate) < 0 || theDate.compareTo(secondDate) > 0){
                    String message = "Patron must be born between 1917-01-01 and 1999-01-01 inclusively";
                    displayErrorMessage(message);
                    return;
                }
            }
            catch (ParseException ex2)
            {
                String message = "Date of Birth must be yyyy-MM-dd";
                displayErrorMessage(message);
                return;
            }
        }

        newPatron.setProperty("name", nameText);
        newPatron.setProperty("address", addressText);
        newPatron.setProperty("city", cityText);
        newPatron.setProperty("stateCode", stateCodeText);
        newPatron.setProperty("zip", zipText);
        newPatron.setProperty("email", emailText);
        newPatron.setProperty("dateOfBirth", dateOfBirthText);
        newPatron.setProperty("status", statusText);
        myModel.stateChangeRequest("ProcessNewPatron", newPatron);

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

    private void disableFields(){
        name.setEditable(false);
        address.setEditable(false);
        city.setEditable(false);
        stateCode.setEditable(false);
        zip.setEditable(false);
        email.setEditable(false);
        status.setEditable(false);
        dateOfBirth.setEditable(false);
        submitButton.setDisable(true);
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
