package sample.model;
/** Public class Divisions.*/
public class Divisions {
    /** Private class member that creates an int variable. */
    private int divisionID;
    /** Private class member that creates a string variable. */
    private String division;
    /** Private class member that creates an int variable. */
    private int countryID;
    /** @returns divisionID */
    public int getDivisionID() {
        return divisionID;
    }
    /** @param divisionID sets the divisionID for the division. */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
    /** @returns division */
    public String getDivision() {
        return division;
    }
    /** @param division sets the division for the division. */
    public void setDivision(String division) {
        this.division = division;
    }
    /** @returns countryID */
    public int getCountryID() {
        return countryID;
    }
    /** @param countryID sets the countryID for the division. */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
/** Public default constructor for Divisions.*/
    public Divisions(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }



}
