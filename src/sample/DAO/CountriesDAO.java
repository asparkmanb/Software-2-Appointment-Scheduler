package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.JDBC;
import sample.model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/** Public class CountriesDAO, retrieves data from the database. */
public class CountriesDAO {
    /** Public method that returns an ObservableList of Countries from the database and creates Country objects. */
    public static ObservableList<Countries> getAllCountries() throws SQLException {
        ObservableList<Countries> allCountries = FXCollections.observableArrayList();
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Countries country;
        try {
            while (rs.next()) {
                country = new Countries(rs.getInt("Country_ID"), rs.getString("Country"));
                allCountries.add(country);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allCountries;
    }
    /** Public method that returns an ObservableList of Strings from the database of Country names.*/
    public static ObservableList<String> loadCountries() throws SQLException {
        ObservableList<String> countryNames = FXCollections.observableArrayList();
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        try {
            while (rs.next()) {
                countryNames.add(new String(rs.getString("Country")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countryNames;
    }

}



