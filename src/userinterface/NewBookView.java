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
//        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();
    }

    private void populateFields() {

    }

    private Node createStatusLog(String s) {
        return null;
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
        grid.add(pubYear, 1, 3);

        Text statusLabel = new Text(" Status : ");
        statusLabel.setFont(myFont);
        statusLabel.setWrappingWidth(150);
        statusLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(statusLabel, 0, 4);

        status = new ComboBox();
        status.getItems().addAll("Active", "Inactive");
        status.setValue("Active");
//        serviceCharge.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent e) {
//                clearErrorMessage();
//                myModel.stateChangeRequest("ServiceCharge", serviceCharge.getText());
//            }
//        });
        grid.add(status, 1, 4);

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
                myModel.stateChangeRequest("NewBookCancelled", null);
            }
        });
        doneCont.getChildren().add(doneButton);
        doneCont.getChildren().add(submitButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);

        return vbox;
    }

    @Override
    public void updateState(String key, Object value) {

    }
}
