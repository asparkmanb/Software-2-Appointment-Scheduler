package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DAO.AppointmentsDAO;
import sample.DAO.UsersDAO;
import sample.model.Appointments;
import sample.model.Users;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

/** Public class Login for the Login controller, implements Initializable method. */
public class Login implements Initializable {

    @FXML
    private Button cancelButtonLogin;

    @FXML
    private Label loginHeaderText;

    @FXML
    private Label passwordLabel;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button saveButtonLogin;

    @FXML
    private Label timeZoneLabel;

    @FXML
    private Label timeZoneText;

    @FXML
    private Label userNameLabel;

    @FXML
    private TextField userNameText;

    private Stage stage;

    private Scene scene;

    private Parent root;

    ResourceBundle rb2 = ResourceBundle.getBundle("bundle", Locale.getDefault());

    /** Lambda expression is used in this method. This method exits the program and prompts for a confirmation to exit, this is where lambda 1 is used. In places like a confirmation box a lambda is a good option because it allows for the developer to write less code and clean up the alert code/functionality. It will still work the same as other confirmation dialog boxes but it is cleaner and easier to read.*/
    @FXML
    void onActionCancel(ActionEvent event) {
        if(Locale.getDefault().getLanguage().equals("fr")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(rb2.getString("ExitApp"));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                System.exit(0);
            }
        }
        else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to exit the program?");
            alert.showAndWait().ifPresent((confirmation-> {
                if (confirmation == ButtonType.OK) {
                    System.exit(0);
                }
            }));
        }

    }

    /** Method that validates the users login upon button click, changes scene to open program, sets local time and date, logs to login_activity.txt. */
    @FXML
    void onActionLogin(ActionEvent event) throws IOException, SQLException {

        LocalDateTime loginTime = LocalDateTime.now();
        ZonedDateTime localZDTLogin = ZonedDateTime.of(loginTime, ZoneId.systemDefault());
        ZoneId zoneIdEst = ZoneId.of("America/New_York");
        ZonedDateTime localZDTLoginToEst = ZonedDateTime.ofInstant(localZDTLogin.toInstant(), zoneIdEst);


        String filename = "login_activity.txt";
        FileWriter fw = new FileWriter(filename, true);
        PrintWriter pw = new PrintWriter(fw);



        ResourceBundle rb2 = ResourceBundle.getBundle("bundle", Locale.getDefault());


        String userName = userNameText.getText();
        String userPassword = passwordText.getText();
        Boolean isValidLogin = false;
        LocalDateTime currentTime = LocalDateTime.now();

        for(Users user : UsersDAO.getAllUsers()){
            if(userName.equals(user.getUserName()) && userPassword.equals(user.getUserPassword())){
                isValidLogin = true;
            }
        }

        if(isValidLogin){

            pw.println("User: " + userNameText.getText() + " | " + "Date/Time in EST: " + localZDTLoginToEst + " | " + "Successful: Yes\n");
        }
        else{

            pw.println("User: " + userNameText.getText() + " | " + "Date/Time in EST: " + localZDTLoginToEst + " | " + "Successful: No\n");
        }





        if(isValidLogin == true){


            boolean within15 = false;

            try {
                for (Appointments appointments : AppointmentsDAO.getAllAppointments()) {
                    LocalDateTime startTime = appointments.getStartDate();
                    long timeDifference = ChronoUnit.MINUTES.between(currentTime, startTime);
                    long timeDifferenceAccurate = timeDifference + 1;
                    if(timeDifferenceAccurate > 0 && timeDifferenceAccurate <= 15 && Locale.getDefault().getLanguage().equals("en")) {
                        within15 = true;
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText("You have a scheduled appointment within 15 minutes");
                        alert.setContentText("Appointment " + appointments.getAppointmentID() + " is scheduled for a time of " + appointments.getStartDate());
                        alert.showAndWait();
                    }
                    if(timeDifferenceAccurate > 0 && timeDifferenceAccurate <= 15 && Locale.getDefault().getLanguage().equals("fr")) {
                        within15 = true;
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText(rb2.getString("15"));
                        alert.setContentText(rb2.getString("Appointment") + " " + appointments.getAppointmentID() + " " + rb2.getString("Time") + " " + appointments.getStartDate());
                        alert.showAndWait();
                    }
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if(within15 == false && Locale.getDefault().getLanguage().equals("en")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("You have no scheduled appointment within 15 minutes");
                alert.setContentText("There are no appointments scheduled in the next 15 minutes");
                alert.showAndWait();
            }
            if(within15 == false && Locale.getDefault().getLanguage().equals("fr")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(rb2.getString("NoAppt"));
                alert.setContentText(rb2.getString("NoAppt2"));
                alert.showAndWait();
            }

            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/AppointmentsMain.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1250, 589);
            stage.centerOnScreen();
            stage.setTitle("Appointments");
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        }
        else{

            if(Locale.getDefault().getLanguage().equals("fr")){
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle(rb2.getString("Error"));
                alert2.setContentText(rb2.getString("Invalid"));
                alert2.showAndWait();
            }
            else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Invalid Username or Password");
                alert.showAndWait();
            }





        }

pw.close();

    }

    /** Initialize method, gets the locale from users computer to set the language and timezone. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        ResourceBundle rb2 = ResourceBundle.getBundle("bundle", Locale.getDefault());

        Locale localeCode = Locale.getDefault();
        Locale france = new Locale("fr", "FR");

        Scanner keyboard = new Scanner(System.in);

        if(Locale.getDefault().getLanguage().equals("fr")){
            Locale.setDefault(france);
            loginHeaderText.setText(rb2.getString("Login"));
            userNameLabel.setText(rb2.getString("Username"));
            passwordLabel.setText(rb2.getString("Password"));
            timeZoneLabel.setText(rb2.getString("TimeZone"));
            saveButtonLogin.setText(rb2.getString("Login"));
            cancelButtonLogin.setText(rb2.getString("Exit"));
        }

        ZoneId zoneId = ZoneId.systemDefault();
        String zoneIdString = zoneId.toString();
        timeZoneText.setText(zoneIdString);
    }

}

