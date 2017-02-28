package userinterface;

import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Book;
import model.BookCollection;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Created by Sammytech on 2/21/17.
 */
public class BookCollectionView extends View{

    protected TableView<BookTableModel> tableOfBooks;
    protected Button cancelButton;
    protected TableColumn authorColumn;
    protected MessageView statusLog;

    public BookCollectionView(IModel model) {
        super(model, "BookCollectionView");

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

        ObservableList<BookTableModel> tableData = FXCollections.observableArrayList();
        try
        {
            BookCollection accountCollection = (BookCollection)myModel.getState("BookList");

            Vector entryList = (Vector)accountCollection.getState("Books");
            Enumeration entries = entryList.elements();

            while (entries.hasMoreElements() == true)
            {
                Book nextAccount = (Book)entries.nextElement();
                Vector<String> view = nextAccount.getEntryListView();

                // add this list entry to the list
                BookTableModel nextTableRowData = new BookTableModel(view);
                tableData.add(nextTableRowData);

            }

            tableOfBooks.setItems(tableData);
            tableOfBooks.getSortOrder().add(authorColumn);
            authorColumn.setSortable(false);
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

        Text prompt = new Text("LIST OF BOOKS");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfBooks = new TableView<BookTableModel>();
        tableOfBooks.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn bookIdColumn = new TableColumn("BookId") ;
        bookIdColumn.setMinWidth(100);
        bookIdColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("bookId"));
        bookIdColumn.setSortable(false);

        authorColumn = new TableColumn("Author") ;
        authorColumn.setMinWidth(100);
        authorColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("author"));
        authorColumn.setSortType(TableColumn.SortType.ASCENDING);

        TableColumn titleColumn = new TableColumn("Title") ;
        titleColumn.setMinWidth(100);
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("title"));
        titleColumn.setSortable(false);

        TableColumn pubYearColumn = new TableColumn("Publication Year") ;
        pubYearColumn.setMinWidth(120);
        pubYearColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("pubYear"));
        pubYearColumn.setSortable(false);

        TableColumn statusColumn = new TableColumn("Status") ;
        statusColumn.setMinWidth(100);
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("status"));
        statusColumn.setSortable(false);
        tableOfBooks.getColumns().addAll(bookIdColumn,
                authorColumn, titleColumn, pubYearColumn, statusColumn);


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(200, 150);
        scrollPane.setContent(tableOfBooks);



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
                myModel.stateChangeRequest("CancelBookList", null);
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
