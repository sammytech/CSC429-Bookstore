package userinterface;

import javafx.beans.property.SimpleStringProperty;

import java.util.Vector;

/**
 * Created by Sammytech on 2/21/17.
 */
public class PatronTableModel {

    private final SimpleStringProperty patronId;
    private final SimpleStringProperty name;
    private final SimpleStringProperty address;
    private final SimpleStringProperty city;
    private final SimpleStringProperty stateCode;
    private final SimpleStringProperty zip;
    private final SimpleStringProperty email;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty status;

    //----------------------------------------------------------------------------
    public PatronTableModel(Vector<String> accountData)
    {
        patronId =  new SimpleStringProperty(accountData.elementAt(0));
        name =  new SimpleStringProperty(accountData.elementAt(1));
        address =  new SimpleStringProperty(accountData.elementAt(2));
        city =  new SimpleStringProperty(accountData.elementAt(3));
        stateCode =  new SimpleStringProperty(accountData.elementAt(4));
        zip =  new SimpleStringProperty(accountData.elementAt(5));
        email =  new SimpleStringProperty(accountData.elementAt(6));
        dateOfBirth =  new SimpleStringProperty(accountData.elementAt(7));
        status =  new SimpleStringProperty(accountData.elementAt(8));
    }

    public String getPatronId() {
        return patronId.get();
    }



    public void setPatronId(String patronId) {
        this.patronId.set(patronId);
    }

    public String getName() {
        return name.get();
    }



    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return address.get();
    }



    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getCity() {
        return city.get();
    }



    public void setCity(String city) {
        this.city.set(city);
    }

    public String getStateCode() {
        return stateCode.get();
    }



    public void setStateCode(String stateCode) {
        this.stateCode.set(stateCode);
    }

    public String getZip() {
        return zip.get();
    }



    public void setZip(String zip) {
        this.zip.set(zip);
    }

    public String getEmail() {
        return email.get();
    }



    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getDateOfBirth() {
        return dateOfBirth.get();
    }



    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    public String getStatus() {
        return status.get();
    }


    public void setStatus(String status) {
        this.status.set(status);
    }
}
