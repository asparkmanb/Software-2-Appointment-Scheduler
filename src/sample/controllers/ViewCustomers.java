package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DAO.AppointmentsDAO;
import sample.DAO.CountriesDAO;
import sample.DAO.CustomersDAO;
import sample.DAO.DivisionsDAO;
import sample.JDBC;
import sample.model.Appointments;
import sample.model.Customers;
import sample.model.Divisions;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

/** Public class Customers for the reports controller, implements Initializable method. */
public class ViewCustomers implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private Label addCustLabel;

    @FXML
    private TableColumn<Customers, String> addressC;

    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<Customers, Integer> idC;

    @FXML
    private TableColumn<Customers, String> nameC;

    @FXML
    private TableColumn<Customers, String> phoneC;

    @FXML
    private TableColumn<Customers, String> postalC;

    @FXML
    private TableColumn<Customers, String> stateP;

    @FXML
    private Button updateButton;

    @FXML
    private TableView<Customers> viewCustomersTB;
    @FXML
    private TextField addressText;

    @FXML
    private Button cancelButton;

    @FXML
    private ChoiceBox<String> countryCB;

    @FXML
    private TextField idText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField phoneText;

    @FXML
    private TextField postalText;

    @FXML
    private Button saveButtonAdd;

    @FXML
    private ChoiceBox<String> stateCB;

    @FXML
    private Label modifyCustomer;

    @FXML
    private Button saveButtonSave;

    @FXML
    private TextField idTextModify;

    ObservableList<String> empty = FXCollections.observableArrayList();

    /** Method that saves the modified customer via the choices made/saves new customer and updates tableview. */
    @FXML
    void onActionSaveModify(ActionEvent event) throws SQLException {

        int selectedCustomer = viewCustomersTB.getSelectionModel().getSelectedItem().getCustomerId();

        if(nameText.getText().isEmpty() || addressText.getText().isEmpty() ||postalText.getText().isEmpty() || phoneText.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("All fields must be populated");
            alert.showAndWait();
            return;
        }

        if(stateCB.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("All fields must be populated");
            alert.showAndWait();
            return;
        }
        if(countryCB.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("All fields must be populated");
            alert.showAndWait();
            return;
        }

        String name = nameText.getText();
        String address = addressText.getText();
        String postal = postalText.getText();
        String phone = phoneText.getText();
        Timestamp createDate = new Timestamp(System.currentTimeMillis());
        String createdBy = "script";
        Timestamp updatedDate = new Timestamp(System.currentTimeMillis());
        String updatedBy = "script";

        int divisionID = 0;

        String chosenCity = stateCB.getSelectionModel().getSelectedItem();
        divisionID = DivisionsDAO.loadDivisionsID(chosenCity);

        String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,name);
        ps.setString(2, address);
        ps.setString(3, postal);
        ps.setString(4,phone);
        ps.setInt(5,divisionID);
        ps.setInt(6,selectedCustomer );
        ps.executeUpdate();
        try {
            viewCustomersTB.setItems(CustomersDAO.getAllCustomers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        nameText.setText("");
        addressText.setText("");
        postalText.setText("");
        phoneText.setText("");
        countryCB.setValue(null);
        stateCB.setValue(null);

        addCustLabel.setText("Add Customer");
        saveButtonAdd.setVisible(true);
        saveButtonSave.setVisible(false);
        idText.setVisible(true);
        idTextModify.setVisible(false);
    }

    /** Method that sends the data of the chosen customer to the Update Customer tab for modifying.*/
    @FXML
    void onActionUpdate(ActionEvent event) throws SQLException {

        Customers currentCustomer = viewCustomersTB.getSelectionModel().getSelectedItem();

        if(currentCustomer != null) {
            addCustLabel.setText("Modify Customer");
            saveButtonAdd.setVisible(false);
            saveButtonSave.setVisible(true);
            idText.setVisible(false);
            idTextModify.setVisible(true);


            int currentCustomerId = currentCustomer.getCustomerId();


            idTextModify.setText(String.valueOf(currentCustomerId));
            nameText.setText(currentCustomer.getCustomerName());
            addressText.setText(currentCustomer.getCustomerAddress());
            postalText.setText(currentCustomer.getPostalCode());
            phoneText.setText(currentCustomer.getPhoneNumber());
            stateCB.setValue(currentCustomer.getDivision());


            for (int i = 0; i < DivisionsDAO.getUSDivisions().size(); i++) {
                if (DivisionsDAO.loadUSDivisions().contains(currentCustomer.getDivision())) {
                    countryCB.getSelectionModel().select("U.S");
                } else if (DivisionsDAO.loadUKDivisions().contains(currentCustomer.getDivision())) {
                    countryCB.getSelectionModel().select("UK");
                } else if (DivisionsDAO.loadCADivisions().contains(currentCustomer.getDivision())) {
                    countryCB.getSelectionModel().select("Canada");
                } else {
                    return;
                }
                stateCB.setValue(currentCustomer.getDivision());
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Must select a customer to modify first");
            alert.showAndWait();
        }
    }


    /** Method that clears the fields for adding or updating a customer. */
    @FXML
    void onActionCancel(ActionEvent event) {
            nameText.setText("");
            addressText.setText("");
            postalText.setText("");
            phoneText.setText("");
            if(countryCB.getValue() != null){
                    countryCB.setValue(null);}

            if(stateCB.getValue() != null){
                stateCB.setValue(null);}

            addCustLabel.setText("Add Customer");
            saveButtonAdd.setVisible(true);
            saveButtonSave.setVisible(false);
            idText.setVisible(true);
            idTextModify.setVisible(false);

    }

    /** Method that creates a new customer object and saves it to the database based on the information given. */
    @FXML
    void onActionSave(ActionEvent event) throws SQLException, IOException {

        if(nameText.getText().isEmpty() || addressText.getText().isEmpty() ||postalText.getText().isEmpty() || phoneText.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("All fields must be populated");
            alert.showAndWait();
            return;
        }

        if(stateCB.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("All fields must be populated");
            alert.showAndWait();
            return;
        }
        if(countryCB.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("All fields must be populated");
            alert.showAndWait();
            return;
        }

        String name = nameText.getText();
        String address = addressText.getText();
        String postal = postalText.getText();
        String phone = phoneText.getText();
        Timestamp createDate = new Timestamp(System.currentTimeMillis());
        String createdBy = "script";
        Timestamp updatedDate = new Timestamp(System.currentTimeMillis());
        String updatedBy = "script";

        int divisionID = 0;

        String chosenCity = stateCB.getSelectionModel().getSelectedItem();
        divisionID = DivisionsDAO.loadDivisionsID(chosenCity);

        String sql = "INSERT INTO CUSTOMERS(Customer_name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1,name);
        ps.setString(2, address);
        ps.setString(3, postal);
        ps.setString(4,phone);
        ps.setTimestamp(5,createDate);
        ps.setString(6,createdBy);
        ps.setTimestamp(7,updatedDate);
        ps.setString(8,updatedBy);
        ps.setInt(9,divisionID);
        ps.executeUpdate();
        try {
            viewCustomersTB.setItems(CustomersDAO.getAllCustomers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        nameText.setText("");
        addressText.setText("");
        postalText.setText("");
        phoneText.setText("");
        countryCB.setValue(null);
        stateCB.setValue(null);

    }

    /** Lambda expression #2 is used in this method. This method sets the State/Province combobox based upon which country is selected. This lambda uses an event trigger to reset the State combo box if the country selection changes. A lambda is used here because it works well with action event triggers and allows for an easier coding of the combo box chaining..*/
    public void changeCB(){
        countryCB.setOnAction( e-> {
            if(countryCB.getSelectionModel().getSelectedItem().equals("U.S")){
                try {
                    stateCB.setItems(DivisionsDAO.loadUSDivisions());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            else if(countryCB.getSelectionModel().getSelectedItem().equals("Canada")){
                try {
                    stateCB.setItems(DivisionsDAO.loadCADivisions());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            else if(countryCB.getSelectionModel().getSelectedItem().equals("UK")){
                try {
                    stateCB.setItems(DivisionsDAO.loadUKDivisions());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    /** Method that changes the scene to the Appointments page */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/AppointmentsMain.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,   1250, 589);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        stage.centerOnScreen();
    }

    /** Method that takes the chosen customer and deletes them upon button press */
    @FXML
    void onActionDelete(ActionEvent event) throws SQLException {

        JDBC.openConnection();

        Customers currentCustomer = viewCustomersTB.getSelectionModel().getSelectedItem();
        boolean hasAppointment = false;
        boolean isNull = false;

        if(currentCustomer == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Must choose a customer to delete");
            alert.showAndWait();
        }
        else{
            for(Appointments appointment: AppointmentsDAO.getAllAppointments()){
                if(currentCustomer.getCustomerId() == appointment.getCustomerID()){
                    hasAppointment = true;
                }
            }

            if(hasAppointment == true){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Performing this action will delete the customer " + currentCustomer.getCustomerId() + ", as well as any scheduled appointments. Would you like to continue?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    String sql = "DELETE FROM appointments WHERE Customer_ID = ?";
                    String sql2 = "DELETE FROM customers where Customer_ID = ?";
                    PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                    PreparedStatement ps2 = JDBC.connection.prepareStatement(sql2);
                    int selectedCustomer = currentCustomer.getCustomerId();
                    ps.setInt(1, selectedCustomer);
                    ps2.setInt(1,selectedCustomer);
                    ps.executeUpdate();
                    ps2.executeUpdate();
                    viewCustomersTB.setItems(CustomersDAO.getAllCustomers());
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Success");
                    alert2.setContentText("Customer " + currentCustomer.getCustomerId() + " has been deleted.");
                    alert2.showAndWait();
                }
            }

            if(hasAppointment == false){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Performing this action will delete customer " + currentCustomer.getCustomerId() +", would you like to continue?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    String sql2 = "DELETE FROM customers where Customer_ID = ?";
                    PreparedStatement ps2 = JDBC.connection.prepareStatement(sql2);
                    int selectedCustomer = currentCustomer.getCustomerId();
                    ps2.setInt(1, selectedCustomer);
                    ps2.executeUpdate();
                    viewCustomersTB.setItems(CustomersDAO.getAllCustomers());
                    Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                    alert3.setTitle("Success");
                    alert3.setContentText("Customer " + currentCustomer.getCustomerId() + " has been deleted.");
                    alert3.showAndWait();
                }
            }
        }
    }

    /** Initializable method, sets the tableview and the combo boxes. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        idC.setCellValueFactory(new PropertyValueFactory<Customers, Integer>("customerId"));
        nameC.setCellValueFactory(new PropertyValueFactory<Customers, String>("customerName"));
        addressC.setCellValueFactory(new PropertyValueFactory<Customers, String>("customerAddress"));
        postalC.setCellValueFactory(new PropertyValueFactory<Customers, String>("postalCode"));
        phoneC.setCellValueFactory(new PropertyValueFactory<Customers, String>("phoneNumber"));
        stateP.setCellValueFactory(new PropertyValueFactory<Customers, String>("division"));

        try {
            viewCustomersTB.setItems(CustomersDAO.getAllCustomers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            countryCB.setItems(CountriesDAO.loadCountries());
            changeCB();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    }

