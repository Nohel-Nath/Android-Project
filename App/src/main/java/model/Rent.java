package model;

public class Rent  {
    private String title;
    private String location;
    private String address;
    private String fee;
    private String period;
    private String description;
    private int numOfBeds;
    private int numOfBaths;
    private String userName;
    private String contact;
    private String imageuri;
    private boolean Permission;
    private String contactEmail;
    private String available;

    private String key;
    public Rent(){}



    public Rent(String title, String location, String address, String fee, String period, String description, int numOfBeds, int numOfBaths, String userName, String contact,String contactEmail, String imageuri,String available) {
        this.title = title;
        this.location = location;
        this.address = address;
        this.fee = fee;
        this.period = period;
        this.description = description;
        this.numOfBeds = numOfBeds;
        this.numOfBaths = numOfBaths;
        this.userName = userName;
        this.contact = contact;
this.imageuri = imageuri;
this.contactEmail = contactEmail;

        this.Permission = Permission;
        this.available = available;

    }




    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public String getFee() {
        return fee;
    }

    public String getPeriod() {
        return period;
    }

    public String getDescription() {
        return description;
    }

    public int getNumOfBeds() {
        return numOfBeds;
    }

    public int getNumOfBaths() {
        return numOfBaths;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumOfBeds(int numOfBeds) {
        this.numOfBeds = numOfBeds;
    }

    public void setNumOfBaths(int numOfBaths) {
        this.numOfBaths = numOfBaths;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvailable() {return available;}
    public void setAvailable (String available) {this.available = available;}

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactEmail() {return contactEmail;}
    public void  setContactEmail() {this.contactEmail = contactEmail;}

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }

    public boolean getPermission() {
        return Permission;
    }
    public void setPermission(boolean Permission){this.Permission = Permission;}
}
