package userinterface;

import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import model.Patron;
import model.PatronCollection;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Created by Sammytech on 2/21/17.
 */
public class PatronCollectionView extends View{

    protected TableView<PatronTableModel> tableOfPatrons;
    protected Button cancelButton;
    protected TableColumn nameColumn;
    protected MessageView statusLog;

    public PatronCollectionView(IModel model) {
        super(model, "PatronCollectionView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // create our GUI components, add them to this panel
        container.getChildren().add(TitleView.createTitle());
        container.getChildren().add(createFormContent());

        // Error message area
        container.getChildren().add(createStatusLog("                                            "));

        getChildren().add(container);

        populateFields();
    }

    //--------------------------------------------------------------------------
    protected void populateFields()
    {
        getEntryTableModelValues();
    }

    //--------------------------------------------------------------------------
    protected void getEntryTableModelValues()
    {

        ObservableList<PatronTableModel> tableData = FXCollections.observableArrayList();
        try
        {
            PatronCollection patronCollection = (PatronCollection)myModel.getState("PatronList");

            Vector entryList = (Vector)patronCollection.getState("Patrons");
            Enumeration entries = entryList.elements();

            while (entries.hasMoreElements())
            {
                Patron nextPatron = (Patron)entries.nextElement();
                Vector<String> view = nextPatron.getEntryListView();

                // add this list entry to the list
                PatronTableModel nextTableRowData = new PatronTableModel(view);
                tableData.add(nextTableRowData);

            }
//            SortedList<PatronTableModel> sortedData = new SortedList<>(tableData);
//
//            // 4. Bind the SortedList comparator to the TableView comparator.
//            sortedData.comparatorProperty().bind(tableOfPatrons.comparatorProperty());

            tableOfPatrons.setItems(tableData);
            tableOfPatrons.getSortOrder().add(nameColumn);
            nameColumn.setSortable(false);

        }
        catch (Exception e) {//SQLException e) {
            // Need to handle this exception
        }
    }

    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("LIST OF PATRONS");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfPatrons = new TableView<PatronTableModel>();
        tableOfPatrons.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        TableColumn patronIdColumn = new TableColumn("PatronId") ;
        patronIdColumn.setMinWidth(100);
        patronIdColumn.setSortable(false);
        patronIdColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("patronId"));

        nameColumn = new TableColumn("Name") ;
        nameColumn.setMinWidth(100);
//        nameColumn.setSortable(false);
        nameColumn.setSortType(TableColumn.SortType.ASCENDING);
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("name"));

        TableColumn addressColumn = new TableColumn("Address") ;
        addressColumn.setMinWidth(100);
        addressColumn.setSortable(false);
        addressColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("address"));

        TableColumn cityColumn = new TableColumn("City") ;
        cityColumn.setMinWidth(100);
        cityColumn.setSortable(false);
        cityColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("city"));

        TableColumn stateCodeColumn = new TableColumn("State Code") ;
        stateCodeColumn.setMinWidth(100);
        stateCodeColumn.setSortable(false);
        stateCodeColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("stateCode"));

        TableColumn zipColumn = new TableColumn("Zip") ;
        zipColumn.setMinWidth(100);
        zipColumn.setSortable(false);
        zipColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("zip"));

        TableColumn emailColumn = new TableColumn("Email") ;
        emailColumn.setMinWidth(100);
        emailColumn.setSortable(false);
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("email"));

        TableColumn dateOfBirthColumn = new TableColumn("Date of Birth") ;
        dateOfBirthColumn.setMinWidth(100);
        dateOfBirthColumn.setSortable(false);
        dateOfBirthColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("dateOfBirth"));

        TableColumn statusColumn = new TableColumn("Status") ;
        statusColumn.setMinWidth(100);
        statusColumn.setSortable(false);
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("status"));
        tableOfPatrons.getColumns().addAll(patronIdColumn,
                nameColumn, addressColumn, cityColumn, stateCodeColumn, zipColumn,
                emailColumn, dateOfBirthColumn, statusColumn);


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(200, 150);
        scrollPane.setContent(tableOfPatrons);



        cancelButton = new Button("Back");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                /**
                 * Process the Cancel button.
                 * The ultimate result of this action is that the transaction will tell the teller to
                 * to switch to the transaction choice view. BUT THAT IS NOT THIS VIEW'S CONCERN.
                 * It simply tells its model (controller) that the transaction was canceled, and leaves it
                 * to the model to decide to tell the teller to do the switch back.
                 */
                //----------------------------------------------------------
                clearErrorMessage();
                myModel.stateChangeRequest("CancelPatronList", null);
            }
        });

        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);
//        btnContainer.getChildren().add(submitButton);
        btnContainer.getChildren().add(cancelButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(scrollPane);
        vbox.getChildren().add(btnContainer);

        return vbox;
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
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }
    @Override
    public void updateState(String key, Object value) {

    }
}
