package sample.model;
/** Public class Users.*/
public class Users {
    /** Private class member that creates an int variable. */
    private int userID;
    /** Private class member that creates a string variable. */
    private String userName;
    /** Private class member that creates a string variable. */
    private String userPassword;
    /** @returns userID */
    public int getUserID() {
        return userID;
    }
    /** @param userID sets the userID for the User. */
    public void setUserID(int userID) {
        this.userID = userID;
    }
    /** @returns userName */
    public String getUserName() {
        return userName;
    }
    /** @param userName sets the userName for the User. */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /** @returns userPassword */
    public String getUserPassword() {
        return userPassword;
    }
    /** @param userPassword sets the userPassword for the User. */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
/** Default public constructor for users. */
    public Users(int userID, String userName, String userPassword) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }
}
