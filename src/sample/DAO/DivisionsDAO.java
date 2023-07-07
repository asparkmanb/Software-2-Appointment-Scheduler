package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;
import sample.model.Countries;
import sample.model.Divisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/** Public class DivisionsDAO, retrieves data from the database. */
public class DivisionsDAO{
    /** Public method that returns an ObservableList of Divisions from the database and creates Divisions objects. */
    public static ObservableList<Divisions> getAllDivisions() throws SQLException {
        ObservableList<Divisions> allDivisions = FXCollections.observableArrayList();
        JDBC.openConnection();
        String sql = "SELECT Division_ID, Division, Country_ID FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Divisions division;
        try {
            while (rs.next()) {
                division = new Divisions(rs.getInt("Division_ID"), rs.getString("Division"), rs.getInt("Country_ID"));
                allDivisions.add(division);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allDivisions;
    }
    /** Public method that returns an ObservableList of Divisions from the database and creates Customer objects. */
    public static ObservableList<Divisions> getUSDivisions() throws SQLException {
        ObservableList<Divisions> allUSDivisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 1";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Divisions division;
        try {
            while (rs.next()) {
                division = new Divisions(rs.getInt("Division_ID"), rs.getString("Division"), rs.getInt("Country_ID"));
                allUSDivisions.add(division);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allUSDivisions;
    }
    /** Public method that returns an ObservableList of Divisions in the UK */
    public static ObservableList<Divisions> getUKDivisions() throws SQLException {
        ObservableList<Divisions> allUKDivisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 2";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Divisions division;
        try {
            while (rs.next()) {
                division = new Divisions(rs.getInt("Division_ID"), rs.getString("Division"), rs.getInt("Country_ID"));
                allUKDivisions.add(division);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allUKDivisions;
    }
    /** Public method that returns an ObservableList of Divisions in Canada. */
    public static ObservableList<Divisions> getCADivisions() throws SQLException {
        ObservableList<Divisions> allCADivisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 3";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Divisions division;
        try {
            while (rs.next()) {
                division = new Divisions(rs.getInt("Division_ID"), rs.getString("Division"), rs.getInt("Country_ID"));
                allCADivisions.add(division);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allCADivisions;
    }
    /** Public method that returns an Integer of the Division ID given the Divisions name parameter.*/
    public static int loadDivisionsID(String name) throws SQLException {
        int divisionID =0;
        ObservableList<Integer> divisionsID = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions Where Division = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        try {
            while (rs.next()) {
                divisionsID.add(rs.getInt("Division_ID"));
                divisionID = divisionsID.get(0);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divisionID;
    }
    /** Public method that returns an ObservableList of Divisions in the US. */
    public static ObservableList<String> loadUSDivisions() throws SQLException {
        ObservableList<String> usDivisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions Where Country_ID = 1";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        try {
            while (rs.next()) {
                usDivisions.add(new String(rs.getString("Division")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usDivisions;
    }
    /** Public method that returns an ObservableList of UK division strings. */
    public static ObservableList<String> loadUKDivisions() throws SQLException {
        ObservableList<String> ukDivisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions Where Country_ID = 2";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        try {
            while (rs.next()) {
                ukDivisions.add(new String(rs.getString("Division")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ukDivisions;
    }
    /** Public method that returns an ObservableList of Canadian division strings. */
    public static ObservableList<String> loadCADivisions() throws SQLException {
        ObservableList<String> caDivisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions Where Country_ID = 3";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        try {
            while (rs.next()) {
                caDivisions.add(new String(rs.getString("Division")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return caDivisions;
    }
    /** Public method that returns an ObservableList of All division strings. */
    public static ObservableList<String> loadAllDivisions() throws SQLException {
        ObservableList<String> allDivisions = FXCollections.observableArrayList();
        JDBC.openConnection();
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        try {
            while (rs.next()) {
                allDivisions.add(new String(rs.getString("Division")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allDivisions;
    }
}