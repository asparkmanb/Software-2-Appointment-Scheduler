package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;
import sample.model.Customers;
import sample.model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/** Public class CustomersDAO, retrieves data from the database. */
public class CustomersDAO {
    /** Public method that returns an ObservableList of Customers from the database and creates Customer objects. */
    public static ObservableList<Customers> getAllCustomers() throws SQLException {
        ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
        JDBC.openConnection();
        String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.Division FROM CUSTOMERS JOIN first_level_divisions on CUSTOMERS.DIVISION_ID = FIRST_LEVEL_DIVISIONS.DIVISION_ID";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Customers customer;
        try {
            while (rs.next()) {
                customer = new Customers(rs.getInt("Customer_ID"), rs.getString("Customer_Name"), rs.getString("Address"), rs.getString("Postal_Code"), rs.getString("Phone"), rs.getInt("Division_ID"), rs.getString("Division"));
                allCustomers.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allCustomers;
    }


    /** Public method that returns an ObservableList of Integers from the database of Customer IDs. */
    public static ObservableList<Integer> loadCustomerIds() throws SQLException {
        ObservableList<Integer> customerIds = FXCollections.observableArrayList();
        for(Customers customer: CustomersDAO.getAllCustomers()){
            customerIds.add(customer.getCustomerId());
        }
        return customerIds;
    }
/** Public method that returns an ObservableList of Strings from the database of Customer names. */
    public static ObservableList<String> loadCustomerNames() throws SQLException {
        ObservableList<String> customerNames = FXCollections.observableArrayList();
        for (Customers customers : CustomersDAO.getAllCustomers()) {
            String currentCustomerName = customers.getCustomerName();
            customerNames.add(currentCustomerName);
        }
        return customerNames;
    }
/** Public method that takes a string of customer name and returns the customerID. */
    public static int loadCustomerID(String name) throws SQLException {
        int customerID =0;
        ObservableList<Integer> customerIDs = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers Where Customer_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        try {
            while (rs.next()) {
                customerIDs.add(rs.getInt("Customer_ID"));
                customerID = customerIDs.get(0);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerID;
    }
}
