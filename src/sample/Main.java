package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.JDBC;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/** Public main class that gets called at application launch, sets the scene and opens, closes database connection.*/

public class Main extends Application {

    /** Public start method that sets the scene for the program .*/
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/login.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root, 600, 398));
        primaryStage.show();
    }

    /** Public main method that opens the database connection and then closes it at the end .*/
    public static void main(String[] args) {
        JDBC.openConnection();
        LocalDate myLd = LocalDate.of(2023,04,19);
        launch(args);
        JDBC.closeConnection();
    }
}
