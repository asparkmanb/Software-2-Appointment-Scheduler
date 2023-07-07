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
//import jdk.incubator.foreign.SymbolLookup;
import sample.DAO.*;
import sample.JDBC;
import sample.model.Appointments;
import sample.model.Contacts;
import sample.model.Customers;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;
/** Public class Reports for the reports controller, implements Initializable method. */
public class Reports implements Initializable {

        @FXML
        private ComboBox<String> UsersCB;

    @FXML
    private Button showApptButton;

        @FXML
        private TableColumn<Appointments, Integer> custC;

        @FXML
        private ComboBox<String> customerCB;

        @FXML
        private TableView<Appointments> customerSchedule;

        @FXML
        private TableColumn<Appointments, String> descriptionC;

        @FXML
        private TableColumn<Appointments, LocalDate> endC;

        @FXML
        private Label generateNumApptIUsersText;

        @FXML
        private Label generateNumApptMonthText;

        @FXML
        private Button generateNumApptMonths;

        @FXML
        private Button generateNumApptUsers;

        @FXML
        private TableColumn<Appointments, Integer> idC;

        @FXML
        private ComboBox<String> monthCB;

        @FXML
        private TableColumn<Appointments, LocalDate> startC;

        @FXML
        private TableColumn<Appointments, String> titleC;

        @FXML
        private TableColumn<Appointments, String> typeC;

        @FXML
        private ComboBox<String> typeCB;

        /** Method that generates the number of appointments per given month per given type and then sets the label to display this information.*/
        @FXML
        void generateNumApptMonth(ActionEvent event) throws SQLException {

            if(monthCB.getSelectionModel().getSelectedItem() == null || typeCB.getSelectionModel().getSelectedItem() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Month and type must be selected");
                alert.showAndWait();
                return;
            }
            int numOfAppts = 0;
          String chosenMonth =  monthCB.getSelectionModel().getSelectedItem();
          String chosenType = typeCB.getSelectionModel().getSelectedItem();

          for(Appointments appointment : AppointmentsDAO.getAllAppointments()){
              String appointmentMonth = appointment.getStartDate().toLocalDate().getMonth().toString();
              String appointmentType = appointment.getType();

              if(appointmentMonth.equalsIgnoreCase(chosenMonth) && appointmentType.equals(chosenType)){
                  numOfAppts += 1;
              }
          }
            generateNumApptMonthText.setText("There are " + numOfAppts + " appointments of type " + chosenType + " in " + chosenMonth);
        }

        /** Method that generates the number of appointments per user, sets the label with this information. */
        @FXML
        void generateNumApptUser(ActionEvent event) throws SQLException {

            if(UsersCB.getSelectionModel().getSelectedItem() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Customer must be selected");
                alert.showAndWait();
                return;
            }

            int numOfAppointments = 0;
            String chosenUser = UsersCB.getSelectionModel().getSelectedItem();
            int userId = UsersDAO.loadUserID(chosenUser);

            for(Appointments appointment : AppointmentsDAO.getAllAppointments()){
                if(userId == appointment.getUserID()){
                    numOfAppointments += 1;
                }
            }
            generateNumApptIUsersText.setText("There are " + numOfAppointments + " appointments associated with user " + chosenUser);
        }

        /** Method that exits the program upon button click.*/
        @FXML
        void onActionExit(ActionEvent event) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to exit the program?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.exit(0);
            }

        }

        /** Method that sets the scene to the Appointments page.*/
        @FXML
        void onActionViewAppointments(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/AppointmentsMain.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root,   1250, 589);
            stage.setTitle("Appointments");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            stage.centerOnScreen();
        }

    /** Method that sets the tableview based upon the contact chosen in the combobox. */
    @FXML
    void onActionShowAppts(ActionEvent event) {

        if(customerCB.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Contact must be selected");
            alert.showAndWait();
            return;
        }

        ObservableList<Appointments> contactAppointments = FXCollections.observableArrayList();
        String chosenContact = customerCB.getSelectionModel().getSelectedItem();
        int chosenContactId = 0;
        try {
            chosenContactId = ContactsDAO.loadContactID(chosenContact);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            for(Appointments appointment : AppointmentsDAO.getAllAppointments()){
                if(appointment.getContactID() == chosenContactId){
                    contactAppointments.add(appointment);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        customerSchedule.setItems(contactAppointments);
    }

/** Method that changes the scene to Customers page upon button press.*/
        @FXML
        void onActionViewCustomers(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/ViewCustomers.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1319, 519);
            stage.setTitle("Customers");
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        }

        /** Initialize method, sets the labels to blank, sets the tableview. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        generateNumApptIUsersText.setText("");
        generateNumApptMonthText.setText("");

            ObservableList<String> allMonths = FXCollections.observableArrayList();
            allMonths.addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
            monthCB.setItems(allMonths);

        idC.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("appointmentID"));
        titleC.setCellValueFactory(new PropertyValueFactory<Appointments, String>("title"));
        descriptionC.setCellValueFactory(new PropertyValueFactory<Appointments, String>("description"));
        typeC.setCellValueFactory(new PropertyValueFactory<Appointments, String>("type"));
        startC.setCellValueFactory(new PropertyValueFactory<Appointments, LocalDate>("startDate"));
        endC.setCellValueFactory(new PropertyValueFactory<Appointments, LocalDate>("endDate"));
        custC.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("customerID"));

            try {
                    customerCB.setItems(ContactsDAO.loadContactNames());
                    UsersCB.setItems(UsersDAO.loadUserNames());
                    typeCB.setItems(AppointmentsDAO.loadTypes());
            } catch (SQLException throwables) {
                    throwables.printStackTrace();
            }

    }

}


