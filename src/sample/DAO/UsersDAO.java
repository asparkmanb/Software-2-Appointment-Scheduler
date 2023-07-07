package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;
import sample.model.Customers;
import sample.model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.DuplicateFormatFlagsException;
/** Public class UsersDAO, retrieves data from the database. */
public class UsersDAO {
    /** Public method that returns an ObservableList of users from the database and creates User objects. */
    public static ObservableList<Users> getAllUsers() throws SQLException {
        ObservableList<Users> allUsers = FXCollections.observableArrayList();
        JDBC.openConnection();
        String sql = "SELECT * FROM Users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Users user;
        try {
            while (rs.next()) {
                user = new Users(rs.getInt("User_ID"), rs.getString("User_Name"), rs.getString("Password"));
                allUsers.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allUsers;
    }
    /** Public method that returns an ObservableList of integers from the database of user IDs.*/
    public static ObservableList<Integer> loadUserIds() throws SQLException {
        ObservableList<Integer> userIds = FXCollections.observableArrayList();
        for(Users user: UsersDAO.getAllUsers()){
            userIds.add(user.getUserID());
        }
        return userIds;
    }
    /** Public method that returns an ObservableList of strings from the database of user names.*/
    public static ObservableList<String> loadUserNames() throws SQLException {
        ObservableList<String> userNames = FXCollections.observableArrayList();
        for (Users users : UsersDAO.getAllUsers()) {
            String currentUserName = users.getUserName();
            userNames.add(currentUserName);
        }
        return userNames;
    }
    /** Public method that returns an Int of the users ID given the users name parameter.*/
    public static int loadUserID(String name) throws SQLException {
        int userID =0;
        ObservableList<Integer> userIDs = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users Where User_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        try {
            while (rs.next()) {
                userIDs.add(rs.getInt("User_ID"));
                userID = userIDs.get(0);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userID;
    }
}
