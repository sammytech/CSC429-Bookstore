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

/**
 * Created by Sammytech on 2/21/17.
 */
public class NewPatronView extends View {

    protected TextField name;
    protected TextField address;
    protected TextField city;
    protected ComboBox stateCode;
    protected TextField zip;
    protected TextField email;
    protected TextField dateOfBirth;


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
//        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();
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

        stateCode = new ComboBox();
//        status.getItems().addAll("Active", "Inactive");
//        status.setValue("Active");
//        serviceCharge.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent e) {
//                clearErrorMessage();
//                myModel.stateChangeRequest("ServiceCharge", serviceCharge.getText());
//            }
//        });
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
        grid.add(dateOfBirth, 1, 7);


        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        doneButton = new Button("DONE");
        doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton = new Button("SUBMIT");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
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

    private void populateFields() {

    }

    private Node createStatusLog(String s) {
        return null;
    }

    @Override
    public void updateState(String key, Object value) {

    }
}
