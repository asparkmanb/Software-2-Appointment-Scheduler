package sample.model;
/** Public class Customers.*/
public class Customers {
    /** Private class member that creates a string variable. */
    private String division;
    /** Private class member that creates an int variable. */
    private int customerId;
    /** Private class member that creates a string variable. */
    private String customerName;
    /** Private class member that creates a string variable. */
    private String customerAddress;
    /** Private class member that creates a string variable. */
    private String postalCode;
    /** Private class member that creates a string variable. */
    private String phoneNumber;
    /** Private class member that creates an int variable. */
    private int divisionId;
    /** @returns division */
    public String getDivision() {
        return division;
    }
    /** @param division sets the division for the customer. */
    public void setDivision(String division) {
        this.division = division;
    }
/** Public customer default constructor. */
    public Customers(int customerId, String customerName, String customerAddress, String postalCode, String phoneNumber, int divisionId, String division){
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
        this.division = division;
    }
    /** @returns customerId */
    public int getCustomerId() {
        return customerId;
    }
    /** @param customerId sets the customerID for the customer. */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    /** @returns customerName */
    public String getCustomerName() {
        return customerName;
    }
    /** @param customerName sets the customerName for the customer. */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    /** @returns customerAddress */
    public String getCustomerAddress() {
        return customerAddress;
    }
    /** @param customerAddress sets the customerAddress for the user. */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
    /** @returns postalCode */
    public String getPostalCode() {
        return postalCode;
    }
    /** @param postalCode sets the postalCode for the customer. */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /** @returns phoneNumber */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /** @param phoneNumber sets the phoneNumber for the customer. */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /** @returns divisionId */
    public int getDivisionId() {
        return divisionId;
    }
    /** @param divisionId sets the divisionID for the customer. */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

}
