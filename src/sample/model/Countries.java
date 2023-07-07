package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/** Public class Countries.*/
public class Countries {

    /** Private class member that creates an int variable. */
    private int countryID;
    /** Public static member that creates a string variable. */
    public static String countryName;
    /** @returns countryID */
    public int getCountryID() {
        return countryID;
    }
    /** @param countryID sets the countryID for the contact */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
    /** @returns countryName */
    public static String getCountryName() {
        return countryName;
    }
    /** @param countryName sets the countryName for the contact */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    /** Public default constructor for Countries */
    public Countries(int countryID, String countryName){
        this.countryID = countryID;
        this.countryName = countryName;
    }
}
