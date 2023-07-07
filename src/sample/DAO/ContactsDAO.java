package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;
import sample.model.Contacts;
import sample.model.Customers;
import sample.model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/** Public class ContactsDAO, retrieves data from the database. */
public class ContactsDAO {
    /** Public method that returns an ObservableList of contacts from the database and creates Contact objects. */
    public static ObservableList<Contacts> getAllContacts() throws SQLException {
        ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
        JDBC.openConnection();
        String sql = "SELECT * FROM Contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Contacts contact;
        try {
            while (rs.next()) {
                contact = new Contacts(rs.getInt("Contact_ID"), rs.getString("Contact_Name"), rs.getString("Email"));
                allContacts.add(contact);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allContacts;
    }
    /** Public method that returns an ObservableList of integers for Contact Ids. */
    public static ObservableList<Integer> loadContactIds() throws SQLException {
        ObservableList<Integer> contactIds = FXCollections.observableArrayList();
        for(Contacts contact: ContactsDAO.getAllContacts()){
            contactIds.add(contact.getContactID());
        }
        return contactIds;
    }
    /** Public method that returns an ObservableList of Strings for Contact Names. */
    public static ObservableList<String> loadContactNames() throws SQLException {
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for (Contacts contact : ContactsDAO.getAllContacts()) {
            String currentContactName = contact.getContactName();
           contactNames.add(currentContactName);
        }
        return contactNames;
    }
    /** Public method that returns an Integer for the chosen customer, takes in a customer name as a parameter. */
    public static int loadContactID(String name) throws SQLException {
        int contactID =0;
        ObservableList<Integer> contactIDs = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts Where Contact_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        try {
            while (rs.next()) {
                contactIDs.add(rs.getInt("Contact_ID"));
                contactID = contactIDs.get(0);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactID;
    }
    /** Public method that returns a String for the chosen customer, takes in a customer id as a parameter. */
    public static String loadContactName(Integer contactID) throws SQLException {
        String contactName = "";
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts Where Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactID);
        ResultSet rs = ps.executeQuery();
        try {
            while (rs.next()) {
                contactNames.add(rs.getString("Contact_Name"));
                contactName = contactNames.get(0);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactName;
    }
}
