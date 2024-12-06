/* Lab - 5
Course Code and section: COMP 228 - Section 405
Professor: Shaharm Jalaliniya
Group Number:
Member's Name: Isabel Lorrelyn Lag-ang, Mauli Gandhi
Student Number: 301385246 and 301486344
*/
package com.example.mauligandhi_comp228lab5;
// owner class with its attributes getters and setters
public class Owner {
    private int ownerID;
    private String name;
    private String address;
    private String phone;
    private String email;

    // Constructor
    public Owner(int ownerID, String name, String address, String phone, String email) {
        this.ownerID = ownerID;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    // Getters and Setters
    public int getOwnerID() { return ownerID; }
    public void setOwnerID(int ownerID) { this.ownerID = ownerID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

