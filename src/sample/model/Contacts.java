package sample.model;
/** Public class Contacts.*/
public class Contacts {
    /** Private class member that creates an int variable. */
    private int contactID;
    /** Private class member that creates an string variable. */
    private String contactName;
    /** Private class member that creates an string variable. */
    private String email;
    /** @returns contactID */
    public int getContactID() {
        return contactID;
    }
    /** @param contactID sets the contactID for the contact */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
    /** @returns contactName */
    public String getContactName() {
        return contactName;
    }
    /** @param contactName sets the contactName for the contact */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    /** @returns email */
    public String getEmail() {
        return email;
    }
    /** @param email sets the email for the contact */
    public void setEmail(String email) {
        this.email = email;
    }
    /** Contacts public constructor */
    public Contacts(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }



}
