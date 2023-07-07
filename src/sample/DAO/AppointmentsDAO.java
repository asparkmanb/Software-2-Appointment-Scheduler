package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;
import sample.model.Appointments;
import sample.model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/** Public class AppointmentsDAO, retrieves data from the database. */
public class AppointmentsDAO {
    /** Public method that returns an ObservableList of appointments from the database and creates Appointment objects. */
    public static ObservableList<Appointments> getAllAppointments() throws SQLException {
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
        JDBC.openConnection();
        String sql = "SELECT * from APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Appointments appointment;
        try {
            while (rs.next()) {
                String contactName = ContactsDAO.loadContactName(rs.getInt("Contact_ID"));
                appointment = new Appointments(rs.getInt("Appointment_ID"), rs.getString("Title"), rs.getString("Description"), rs.getString("Location"), rs.getString("Type"), rs.getTimestamp("Start").toLocalDateTime(), rs.getTimestamp("End").toLocalDateTime(), rs.getInt("Customer_ID"), rs.getInt("User_ID"), rs.getInt("Contact_ID"), contactName);
                allAppointments.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allAppointments;
    }
    /** Public method that returns an ObservableList of appointment strings that fills in appointment types from the database. */
    public static ObservableList<String> loadTypes() throws SQLException {
        ObservableList<String> types = FXCollections.observableArrayList();
        for (Appointments appointments : AppointmentsDAO.getAllAppointments()) {


            String currentType = appointments.getType();
            if(types.contains(currentType)) {
                return types;
            }
            else {
                types.add(currentType);
            }


        }
        return types;
    }
}
