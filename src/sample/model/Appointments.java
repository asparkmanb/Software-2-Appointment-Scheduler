package sample.model;

import sample.DAO.ContactsDAO;

import java.sql.SQLException;
import java.time.LocalDateTime;
/** Public class Appointments.*/
public class Appointments {
    /** Private class member that creates an int variable. */
    private int appointmentID;
    /** @returns contactName */
    public String getContactName() {
        return contactName;
    }
    /** @param contactName sets the contactName for the appointment */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    /** Private class member that creates a string variable. */
    private String contactName;
    /** @returns appointmentID */
    public int getAppointmentID() {
        return appointmentID;
    }
    /** @param appointmentID sets the id for the appointment */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }
    /** @returns title */
    public String getTitle() {
        return title;
    }
    /** @param title sets the title for the appointment */
    public void setTitle(String title) {
        this.title = title;
    }
    /** @returns description */
    public String getDescription() {
        return description;
    }
    /** @param description sets the description for the appointment */
    public void setDescription(String description) {
        this.description = description;
    }
    /** @returns location */
    public String getLocation() {
        return location;
    }
    /** @param location sets the location for the appointment */
    public void setLocation(String location) {
        this.location = location;
    }
    /** @returns type */
    public String getType() {
        return type;
    }
    /** @param type sets the type for the appointment */
    public void setType(String type) {
        this.type = type;
    }
    /** @returns startDate */
    public LocalDateTime getStartDate() {
        return startDate;
    }
    /** @param startDate sets the startDate for the appointment */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    /** @returns endDate */
    public LocalDateTime getEndDate() {
        return endDate;
    }
    /** @param endDate sets the endDate for the appointment */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    /** @returns customerID */
    public int getCustomerID() {
        return customerID;
    }
    /** @param customerID sets the customerID for the appointment */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    /** @returns userID */
    public int getUserID() {
        return userID;
    }
    /** @param userID sets the userID for the appointment */
    public void setUserID(int userID) {
        this.userID = userID;
    }
    /** @returns contactID */
    public int getContactID() {
        return contactID;
    }
    /** @param contactID sets the contactID for the appointment */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
    /** Public constructor for Appointments that sets the variables for Appointment objects. */
    public Appointments(int appointmentID, String title, String description, String location, String type, LocalDateTime startDate, LocalDateTime endDate, int customerID, int userID, int contactID, String contactName) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contactName = contactName;
    }
    /** Private class member that creates a string variable. */
    private String title;
    /** Private class member that creates an string variable. */
    private String description;
    /** Private class member that creates a string variable. */
    private String location;
    /** Private class member that creates a string variable. */
    private String type;
    /** Private class member that creates a LocalDateTime variable. */
    private LocalDateTime startDate;
    /** Private class member that creates a LocalDateTime variable. */
    private LocalDateTime endDate;
    /** Private class member that creates a int variable. */
    private int customerID;
    /** Private class member that creates a LocalDateTime variable. */
    private int userID;
    /** Private class member that creates a LocalDateTime variable. */
    private int contactID;


}
