package userinterface;

import javafx.beans.property.SimpleStringProperty;

import java.util.Vector;

/**
 * Created by Sammytech on 2/21/17.
 */
public class BookTableModel {

    private final SimpleStringProperty bookId;
    private final SimpleStringProperty author;
    private final SimpleStringProperty title;
    private final SimpleStringProperty pubYear;
    private final SimpleStringProperty status;

    //----------------------------------------------------------------------------
    public BookTableModel(Vector<String> accountData)
    {
        bookId =  new SimpleStringProperty(accountData.elementAt(0));
        author =  new SimpleStringProperty(accountData.elementAt(1));
        title =  new SimpleStringProperty(accountData.elementAt(2));
        pubYear =  new SimpleStringProperty(accountData.elementAt(3));
        status =  new SimpleStringProperty(accountData.elementAt(4));
    }

    public String getBookId() {
        return bookId.get();
    }


    public void setBookId(String bookId) {
        this.bookId.set(bookId);
    }

    public String getAuthor() {
        return author.get();
    }



    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getTitle() {
        return title.get();
    }


    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getPubYear() {
        return pubYear.get();
    }


    public void setPubYear(String pubYear) {
        this.pubYear.set(pubYear);
    }

    public String getStatus() {
        return status.get();
    }


    public void setStatus(String status) {
        this.status.set(status);
    }
}
