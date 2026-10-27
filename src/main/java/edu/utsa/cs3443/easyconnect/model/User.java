package edu.utsa.cs3443.easyconnect.model;

public class User implements toCSV {

    private String name;
    private String password;
    private String email;
    private String address;
    private String gender;
    private String dateOfBirth;
    private String phoneNumber;
    private int id;

    public User(String n, String p, String e, String a, String g, String d, String pN, int i) {
        name = n;
        password = p;
        email = e;
        address = a;
        gender = g;
        dateOfBirth = d;
        phoneNumber = pN;
        id = i;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toCSV() {
        return null;
    }
}
