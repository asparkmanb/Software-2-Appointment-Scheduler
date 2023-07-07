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

/** Public class Customers for the Appointments controller, implements Initializable method. */
public class AppointmentsMain implements Initializable {

    @FXML
    private RadioButton allRb;

    @FXML
    private TableView<Appointments> appointmentsTB;

    @FXML
    private Button cancelButton;

    @FXML
    private TableColumn<Appointments, String> contactC;

    @FXML
    private ChoiceBox<String> contactDrop;

    @FXML
    private TableColumn<Appointments, Integer> contactID;

    @FXML
    private RadioButton curentMonth;

    @FXML
    private RadioButton currentWeek;

    @FXML
    private TableColumn<Appointments, Integer> customerID;

    @FXML
    private ChoiceBox<Integer> customerIdDrop;

    @FXML
    private Button customersButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<Appointments, String> descriptionC;

    @FXML
    private TextField descriptionText;

    @FXML
    private TableColumn<Appointments, LocalDate> endDateC;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TableColumn<Appointments, LocalDateTime> endTimeC;

    @FXML
    private ChoiceBox<String> endTimeDrop;

    @FXML
    private Button exitButton;

    @FXML
    private ToggleGroup filterBy;

    @FXML
    private TableColumn<Appointments, Integer> idC;

    @FXML
    private TextField idText;

    @FXML
    private TableColumn<Appointments, String> locationC;

    @FXML
    private TextField locationText;

    @FXML
    private Button reportsButton;

    @FXML
    private TableColumn<Appointments, LocalDate> startDateC;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private TableColumn<Appointments, LocalDateTime> startTimeC;

    @FXML
    private ChoiceBox<String> startTimeDrop;

    @FXML
    private TableColumn<Appointments, String> titleC;

    @FXML
    private TextField titleText;

    @FXML
    private TableColumn<Appointments, String> typeC;

    @FXML
    private TextField typeText;

    @FXML
    private Button updateButton;

    @FXML
    private TableColumn<Appointments, Integer> userID;

    @FXML
    private ChoiceBox<Integer> userIdDrop;

    @FXML
    private Button addButton;

    @FXML
    private Button saveButton;

    @FXML
    private Label addCustLabel;


    /** Creates an ObservableList of Strings for Appointment Times. */
    ObservableList<String> appointmentTimes = FXCollections.observableArrayList();

    /** Creates an ObservableList of Appointments for the the current months appointments. */
    ObservableList<Appointments> currentMonthAppointments = FXCollections.observableArrayList();

    /** Creates an ObservableList of Appointments for this current weeks appointments. */
    ObservableList<Appointments> currentWeekAppointments = FXCollections.observableArrayList();

    /** Method that saves the modified appointment when the button is pressed, returns void. */
    @FXML
    void onActionSaveModify(ActionEvent event) throws SQLException {

        LocalTime companyOpenTime = LocalTime.of(8, 0);
        LocalTime companyCloseTime = LocalTime.of(22, 0);
        LocalDate startDateChoice = startDatePicker.getValue();
        LocalDate endDateChoice = endDatePicker.getValue();

        if (startDatePicker.getValue() == null || endDatePicker.getValue() == null || startTimeDrop.getValue() == null || endTimeDrop.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("All fields must be populated");
            alert.showAndWait();
            return;
        }

        if (titleText.getText().isEmpty() || descriptionText.getText().isEmpty() || locationText.getText().isEmpty() || typeText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("All fields must be populated");
            alert.showAndWait();
            return;
        }

        LocalTime startTime = LocalTime.parse(startTimeDrop.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime endTime = LocalTime.parse(endTimeDrop.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalDateTime startDate = LocalDateTime.of(startDateChoice, startTime);
        LocalDateTime endDate = LocalDateTime.of(endDateChoice, endTime);
        Timestamp startDateTimeForDBConversion = Timestamp.valueOf(startDate);
        Timestamp endDateTimeForDBConversion = Timestamp.valueOf(endDate);
        ZonedDateTime localZDTStart = ZonedDateTime.of(startDate, ZoneId.systemDefault());
        ZonedDateTime localZDTEnd = ZonedDateTime.of(endDate, ZoneId.systemDefault());
        ZoneId zoneIdEst = ZoneId.of("America/New_York");
        ZonedDateTime localZDTStartToEst = ZonedDateTime.ofInstant(localZDTStart.toInstant(), zoneIdEst);
        ZonedDateTime localZDTEndToEst = ZonedDateTime.ofInstant(localZDTEnd.toInstant(), zoneIdEst);
        LocalTime localStartConvertedToEst = localZDTStartToEst.toLocalTime();
        LocalTime localEndConvertedToEst = localZDTEndToEst.toLocalTime();

        int selectedAppointment = appointmentsTB.getSelectionModel().getSelectedItem().getAppointmentID();

        String title = titleText.getText();
        String description = descriptionText.getText();
        String location = locationText.getText();
        String type = typeText.getText();
        Timestamp createDate = new Timestamp(System.currentTimeMillis());
        String createdBy = "script";
        Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());
        String lastUpdatedBy = "script";


        int chosenCustomerId = customerIdDrop.getSelectionModel().getSelectedItem();
        int chosenUserId = userIdDrop.getSelectionModel().getSelectedItem();
        String chosenContactName = contactDrop.getSelectionModel().getSelectedItem();
        int chosenContactId = 0;
        chosenContactId = ContactsDAO.loadContactID(chosenContactName);


        if (localStartConvertedToEst.isBefore(companyOpenTime) || localEndConvertedToEst.isAfter(companyCloseTime) || localStartConvertedToEst.isAfter(companyCloseTime) || localEndConvertedToEst.isBefore(companyOpenTime)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Cannot schedule an appointment that is outside 8 A.M - 10 P.M EST");
            alert.showAndWait();
            return;
        }

        if (localEndConvertedToEst.isBefore(localStartConvertedToEst) || localStartConvertedToEst.isAfter(localEndConvertedToEst) || localStartConvertedToEst == localEndConvertedToEst) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Appointment start time must be before appointment end time");
            alert.showAndWait();
            return;
        }

        for (Appointments appointments : AppointmentsDAO.getAllAppointments()) {

            LocalDateTime startDateTime = appointments.getStartDate();
            LocalDateTime endDateTime = appointments.getEndDate();

            if (appointments.getCustomerID() == chosenCustomerId && appointments.getAppointmentID() != selectedAppointment) {

                if (((startDate.isAfter(startDateTime) || startDate.isEqual(startDateTime)) && startDate.isBefore(endDateTime))) {
                    System.out.println("New appointment start time for customer" + startDate);
                    System.out.println("New appointment end time for customer" + endDate);
                    System.out.println("-----");
                    System.out.println("This existing customers appointment starts at" + startDateTime);
                    System.out.println("This existing customers appointment starts at" + endDateTime);
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setContentText("Customer cannot have more than one appointment at the same time");
                    alert.showAndWait();
                    return;
                }

                if (endDate.isAfter(startDateTime) && ((endDate.isBefore(endDateTime) || endDate.isEqual(endDateTime)))) {
                    System.out.println("New appointment start time for customer" + startDate);
                    System.out.println("New appointment end time for customer" + endDate);
                    System.out.println("-----");
                    System.out.println("This existing customers appointment starts at" + startDateTime);
                    System.out.println("This existing customers appointment starts at" + endDateTime);
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setContentText("Customer cannot have more than one appointment at the same time");
                    alert.showAndWait();
                    return;
                }

                if ((startDate.isBefore(startDateTime) || startDate.isEqual(startDateTime)) && (endDate.isAfter(endDateTime) || endDate.isEqual(endDateTime))) {
                    System.out.println("New appointment start time for customer" + startDate);
                    System.out.println("New appointment end time for customer" + endDate);
                    System.out.println("-----");
                    System.out.println("This existing customers appointment starts at" + startDateTime);
                    System.out.println("This existing customers appointment starts at" + endDateTime);
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setContentText("Customer cannot have more than one appointment at the same time");
                    alert.showAndWait();
                    return;
                }


            }

        }

        String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, startDateTimeForDBConversion);
        ps.setTimestamp(6, endDateTimeForDBConversion);
        ps.setTimestamp(7, createDate);
        ps.setString(8, createdBy);
        ps.setTimestamp(9, lastUpdate);
        ps.setString(10, lastUpdatedBy);
        ps.setInt(11, chosenCustomerId);
        ps.setInt(12, chosenUserId);
        ps.setInt(13, chosenContactId);
        ps.setInt(14, selectedAppointment);
        ps.executeUpdate();

        titleText.setText("");
        descriptionText.setText("");
        locationText.setText("");
        typeText.setText("");
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        startTimeDrop.setValue(null);
        endTimeDrop.setValue(null);
        customerIdDrop.setValue(null);
        userIdDrop.setValue(null);
        contactDrop.setValue(null);

        addCustLabel.setText("Add Appointment");
        addButton.setVisible(true);
        saveButton.setVisible(false);
        idText.setText("Auto-Generated");

        if(curentMonth.isSelected()){
            setRBs();
            appointmentsTB.setItems(currentMonthAppointments);
        }
        else if(currentWeek.isSelected()){
            setRBs();
            appointmentsTB.setItems(currentWeekAppointments);
        }
        else{
            appointmentsTB.setItems(AppointmentsDAO.getAllAppointments());
        }




    }

    /** Method that sets the information from the selected appointment and sends it to the modify fields for updating. Returns void. */
    @FXML
    void onActionUpdate(ActionEvent event) throws IOException, SQLException {

        Appointments currentAppointment = appointmentsTB.getSelectionModel().getSelectedItem();

        if (currentAppointment != null) {
            addCustLabel.setText("Modify Appointment");
            addButton.setVisible(false);
            saveButton.setVisible(true);

            int currentAppointmentId = currentAppointment.getAppointmentID();

            idText.setText(String.valueOf(currentAppointmentId));
            titleText.setText(currentAppointment.getTitle());
            descriptionText.setText(currentAppointment.getDescription());
            locationText.setText(currentAppointment.getLocation());
            typeText.setText(currentAppointment.getType());

            LocalDateTime currentAppointmentStartDateTime = currentAppointment.getStartDate();
            LocalDateTime currentAppointmentEndDateTime = currentAppointment.getEndDate();

            LocalDate currentAppointmentStartDate = currentAppointmentStartDateTime.toLocalDate();
            LocalDate currentAppointmentEndDate = currentAppointmentEndDateTime.toLocalDate();
            LocalTime currentAppointmentStartTime = currentAppointmentStartDateTime.toLocalTime();
            LocalTime currentAppointmentEndTime = currentAppointmentEndDateTime.toLocalTime();
            String currentAppointmentStartTimeString = currentAppointmentStartTime.toString();
            String currentAppointmentStartEndString = currentAppointmentEndTime.toString();

            startDatePicker.setValue(currentAppointmentStartDate);
            endDatePicker.setValue(currentAppointmentEndDate);
            startTimeDrop.setValue(currentAppointmentStartTimeString);
            endTimeDrop.setValue(currentAppointmentStartEndString);

            customerIdDrop.setValue(currentAppointment.getCustomerID());
            userIdDrop.setValue(currentAppointment.getUserID());

            String currentContactName = ContactsDAO.loadContactName(currentAppointment.getContactID());

            contactDrop.setValue(currentContactName);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Must select an appointment to modify first");
            alert.showAndWait();
        }

    }


    /** Method that creates a new appointment whenever all of the fields are populated and sends that information to the database. */
    @FXML
    void onActionAdd(ActionEvent event) throws SQLException {

        LocalTime companyOpenTime = LocalTime.of(8, 0);
        LocalTime companyCloseTime = LocalTime.of(22, 0);
        LocalDate startDateChoice = startDatePicker.getValue();
        LocalDate endDateChoice = endDatePicker.getValue();

        if (startDatePicker.getValue() == null || endDatePicker.getValue() == null || startTimeDrop.getValue() == null || endTimeDrop.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("All fields must be populated");
            alert.showAndWait();
            return;
        }

        if (titleText.getText().isEmpty() || descriptionText.getText().isEmpty() || locationText.getText().isEmpty() || typeText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("All fields must be populated");
            alert.showAndWait();
            return;
        }

        LocalTime startTime = LocalTime.parse(startTimeDrop.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime endTime = LocalTime.parse(endTimeDrop.getValue(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalDateTime startDate = LocalDateTime.of(startDateChoice, startTime);
        LocalDateTime endDate = LocalDateTime.of(endDateChoice, endTime);
        Timestamp startDateTimeForDBConversion = Timestamp.valueOf(startDate);
        Timestamp endDateTimeForDBConversion = Timestamp.valueOf(endDate);
        ZonedDateTime localZDTStart = ZonedDateTime.of(startDate, ZoneId.systemDefault());
        ZonedDateTime localZDTEnd = ZonedDateTime.of(endDate, ZoneId.systemDefault());
        ZoneId zoneIdEst = ZoneId.of("America/New_York");
        ZonedDateTime localZDTStartToEst = ZonedDateTime.ofInstant(localZDTStart.toInstant(), zoneIdEst);
        ZonedDateTime localZDTEndToEst = ZonedDateTime.ofInstant(localZDTEnd.toInstant(), zoneIdEst);
        LocalTime localStartConvertedToEst = localZDTStartToEst.toLocalTime();
        LocalTime localEndConvertedToEst = localZDTEndToEst.toLocalTime();

        String title = titleText.getText();
        String description = descriptionText.getText();
        String location = locationText.getText();
        String type = typeText.getText();
        Timestamp createDate = new Timestamp(System.currentTimeMillis());
        String createdBy = "script";
        Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());
        String lastUpdatedBy = "script";

        int chosenCustomerId = customerIdDrop.getSelectionModel().getSelectedItem();
        int chosenUserId = userIdDrop.getSelectionModel().getSelectedItem();
        String chosenContactName = contactDrop.getSelectionModel().getSelectedItem();
        int chosenContactId = 0;
        chosenContactId = ContactsDAO.loadContactID(chosenContactName);

        if (localStartConvertedToEst.isBefore(companyOpenTime) || localEndConvertedToEst.isAfter(companyCloseTime) || localStartConvertedToEst.isAfter(companyCloseTime) || localEndConvertedToEst.isBefore(companyOpenTime)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Cannot schedule an appointment that is outside 8 A.M - 10 P.M EST");
            alert.showAndWait();
            return;
        }

        if (localEndConvertedToEst.isBefore(localStartConvertedToEst) || localStartConvertedToEst.isAfter(localEndConvertedToEst) || localStartConvertedToEst == localEndConvertedToEst) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Appointment start time must be before appointment end time");
            alert.showAndWait();
            return;
        }

        for (Appointments appointments : AppointmentsDAO.getAllAppointments()) {

            LocalDateTime startDateTime = appointments.getStartDate();
            LocalDateTime endDateTime = appointments.getEndDate();

            if (appointments.getCustomerID() == chosenCustomerId) {

                if (((startDate.isAfter(startDateTime) || startDate.isEqual(startDateTime)) && startDate.isBefore(endDateTime))) {
                    System.out.println("New appointment start time for customer" + startDate);
                    System.out.println("New appointment end time for customer" + endDate);
                    System.out.println("-----");
                    System.out.println("This existing customers appointment starts at" + startDateTime);
                    System.out.println("This existing customers appointment starts at" + endDateTime);
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setContentText("Customer cannot have more than one appointment at the same time");
                    alert.showAndWait();
                    return;
                }

                if (endDate.isAfter(startDateTime) && ((endDate.isBefore(endDateTime) || endDate.isEqual(endDateTime)))) {
                    System.out.println("New appointment start time for customer" + startDate);
                    System.out.println("New appointment end time for customer" + endDate);
                    System.out.println("-----");
                    System.out.println("This existing customers appointment starts at" + startDateTime);
                    System.out.println("This existing customers appointment starts at" + endDateTime);
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setContentText("Customer cannot have more than one appointment at the same time");
                    alert.showAndWait();
                    return;
                }

                if ((startDate.isBefore(startDateTime) || startDate.isEqual(startDateTime)) && (endDate.isAfter(endDateTime) || endDate.isEqual(endDateTime))) {
                    System.out.println("New appointment start time for customer" + startDate);
                    System.out.println("New appointment end time for customer" + endDate);
                    System.out.println("-----");
                    System.out.println("This existing customers appointment starts at" + startDateTime);
                    System.out.println("This existing customers appointment starts at" + endDateTime);
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setContentText("Customer cannot have more than one appointment at the same time");
                    alert.showAndWait();
                    return;
                }

            }
        }

        String sql = "INSERT INTO APPOINTMENTS(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, startDateTimeForDBConversion);
        ps.setTimestamp(6, endDateTimeForDBConversion);
        ps.setTimestamp(7, createDate);
        ps.setString(8, createdBy);
        ps.setTimestamp(9, lastUpdate);
        ps.setString(10, lastUpdatedBy);
        ps.setInt(11, chosenCustomerId);
        ps.setInt(12, chosenUserId);
        ps.setInt(13, chosenContactId);
        ps.executeUpdate();
        try {
            appointmentsTB.setItems(AppointmentsDAO.getAllAppointments());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        titleText.setText("");
        descriptionText.setText("");
        locationText.setText("");
        typeText.setText("");
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        startTimeDrop.setValue(null);
        endTimeDrop.setValue(null);
        customerIdDrop.setValue(null);
        userIdDrop.setValue(null);
        contactDrop.setValue(null);

        if(curentMonth.isSelected()){
            setRBs();
            appointmentsTB.setItems(currentMonthAppointments);
        }
        else if(currentWeek.isSelected()){
            setRBs();
            appointmentsTB.setItems(currentWeekAppointments);
        }
        else{
            appointmentsTB.setItems(AppointmentsDAO.getAllAppointments());
        }


    }

    /** Method that deletes the selected appointment whenever the delete button is pressed with this appointment selected. */
    @FXML
    void onActionDelete(ActionEvent event) throws SQLException {

        JDBC.openConnection();

        Appointments currentAppointment = appointmentsTB.getSelectionModel().getSelectedItem();

        if (currentAppointment != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Performing this action will delete appointment " + currentAppointment.getAppointmentID() + ", would you like to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String sql = "DELETE FROM Appointments WHERE Appointment_ID = ?";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                int selectedAppointment = currentAppointment.getAppointmentID();
                ps.setInt(1, selectedAppointment);
                ps.executeUpdate();

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Success");
                alert2.setContentText("Appointment " + currentAppointment.getAppointmentID() + " of type " + currentAppointment.getType() + " has been deleted");
                alert2.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Must select an appointment before deleting");
            alert.showAndWait();
        }
        if(curentMonth.isSelected()){
            setRBs();
            appointmentsTB.setItems(currentMonthAppointments);
        }
        else if(currentWeek.isSelected()){
            setRBs();
            appointmentsTB.setItems(currentWeekAppointments);
        }
        else{
            appointmentsTB.setItems(AppointmentsDAO.getAllAppointments());
        }
    }

    /** Method that exits the program upon clicking the exit button. */

    @FXML
    void onActionExit(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to exit the program?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /** Method that changes the scene to the Customers scene upon button click. */

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


    /** Cancels the add/update feature, clears all fields for the appointment. */
    @FXML
    void onActionCancel(ActionEvent event) {

        if (addCustLabel.getText() == "Add Customer") {
            titleText.setText("");
            descriptionText.setText("");
            locationText.setText("");
            typeText.setText("");
            startDatePicker.setValue(null);
            endDatePicker.setValue(null);
            startTimeDrop.setValue(null);
            endTimeDrop.setValue(null);
            customerIdDrop.setValue(null);
            userIdDrop.setValue(null);
            contactDrop.setValue(null);

        } else {
            titleText.setText("");
            descriptionText.setText("");
            locationText.setText("");
            typeText.setText("");
            startDatePicker.setValue(null);
            endDatePicker.setValue(null);
            startTimeDrop.setValue(null);
            endTimeDrop.setValue(null);
            customerIdDrop.setValue(null);
            userIdDrop.setValue(null);
            contactDrop.setValue(null);
            addCustLabel.setText("Add Appointment");
            addButton.setVisible(true);
            saveButton.setVisible(false);
            idText.setText("Auto-Generated");
        }

    }

    /** When button pressed, changes the scene to the reports tab. */
    @FXML
    void onActionViewReports(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/Reports.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1255, 716);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }


    /** If the current month radio button is selected, this method sets the tableview with the current month ObservableList. */
    @FXML
    void currentMonthRadio(ActionEvent event) {
        if(curentMonth.isSelected()){
            setRBs();
            appointmentsTB.setItems(currentMonthAppointments);
        }
    }

    /** If the current week radio button is selected, this method sets the tableview with the current week ObservableList. */
    @FXML
    void currentWeekRadio(ActionEvent event) {
        if(currentWeek.isSelected()){
            setRBs();
            appointmentsTB.setItems(currentWeekAppointments);

        }
    }

    /** If the all radio button is selected, this method sets the tableview with the all appointments ObservableList. */
    @FXML
    void allAppointments(ActionEvent event) throws SQLException {
        if(allRb.isSelected()){
            appointmentsTB.setItems(AppointmentsDAO.getAllAppointments());
        }
    }

    /** Public method that sets the radio buttons by filling in the ObservableLists for week and month depending on the button. */
    public void setRBs(){
        currentMonthAppointments.clear();
        currentWeekAppointments.clear();
        try {
            for(Appointments appointments : AppointmentsDAO.getAllAppointments()){
                LocalDateTime allAppointmentDateTime = appointments.getStartDate();
                LocalDate localDateExtraction = allAppointmentDateTime.toLocalDate();
                LocalDate nowDate = LocalDate.now();
                Month userMonth = nowDate.getMonth();
                Month localMonthExtraction = localDateExtraction.getMonth();
                int daysBetweenAppointments = nowDate.compareTo(localDateExtraction);

                if(userMonth.equals(localMonthExtraction)){
                    currentMonthAppointments.add(appointments);
                    if(daysBetweenAppointments >= -7 &&  daysBetweenAppointments <= 7){
                        currentWeekAppointments.add(appointments);
                    }
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** Initialize method, sets the timezone and sets the EST business hours, sets the tableview. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idText.setText("Auto-Generated");

        ZoneId zoneIdEst = ZoneId.of("America/New_York");
        ZonedDateTime estZDT = ZonedDateTime.now(zoneIdEst);
        LocalTime companyOpenTime = LocalTime.of(8,0);
        LocalTime companyCloseTime = LocalTime.of(22,0);

        saveButton.setVisible(false);

        idC.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("appointmentID"));
        titleC.setCellValueFactory(new PropertyValueFactory<Appointments, String>("title"));
        descriptionC.setCellValueFactory(new PropertyValueFactory<Appointments, String>("description"));
        locationC.setCellValueFactory(new PropertyValueFactory<Appointments, String>("location"));
        contactC.setCellValueFactory(new PropertyValueFactory<Appointments, String>("contactName"));
        typeC.setCellValueFactory(new PropertyValueFactory<Appointments, String>("type"));
        startDateC.setCellValueFactory(new PropertyValueFactory<Appointments, LocalDate>("startDate"));
        endDateC.setCellValueFactory(new PropertyValueFactory<Appointments, LocalDate>("endDate"));
        customerID.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("customerID"));
        userID.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("userID"));

        try {
            appointmentsTB.setItems(AppointmentsDAO.getAllAppointments());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        appointmentTimes.addAll("00:00", "00:15","00:30","00:45", "01:00","01:15","01:30","01:45", "02:00","02:15","02:30","02:45", "03:00","03:15","03:30","03:45", "04:00","04:15","04:30","04:45", "05:00","05:15","05:30","05:45", "06:00","06:15","06:30","06:45", "07:00","07:15","07:30","07:45", "08:00","08:15","08:30","08:45", "09:00","09:15","09:30","09:45", "10:00","10:15","10:30","10:45", "11:00","11:15","11:30","11:45", "12:00","12:15","12:30","12:45","13:00","13:15","13:30","13:45", "14:00","14:15","14:30","14:45","15:00","15:15","15:30","15:45", "16:00","16:15","16:30","16:45", "17:00","17:15","17:30","17:45", "18:00","18:15","18:30","18:45", "19:00","19:15","19:30","19:45", "20:00","20:15","20:30","20:45", "21:00","21:15","21:30","21:45", "22:00","22:15","22:30","22:45","23:00","23:15","23:30","23:45");
        startTimeDrop.setItems(appointmentTimes);
        endTimeDrop.setItems(appointmentTimes);

        try {
            customerIdDrop.setItems(CustomersDAO.loadCustomerIds());
            userIdDrop.setItems(UsersDAO.loadUserIds());
            contactDrop.setItems(ContactsDAO.loadContactNames());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
