/* Lab - 5
Course Code and section: COMP 228 - Section 405
Professor: Shaharm Jalaliniya
Group Number:
Member's Name: Isabel Lorrelyn Lag-ang, Mauli Gandhi
Student Number: 301385246 and 301486344
*/
package com.example.mauligandhi_comp228lab5;
// car class with its attributes getters and setters
public class Car {
    private int carID;
    private String make;
    private String model;
    private int VIN;
    private int buildYear;
    private String type;

    // Constructor
    public Car(int carID, String make, String model, int VIN, int buildYear, String type) {
        this.carID = carID;
        this.make = make;
        this.model = model;
        this.VIN = VIN;
        this.buildYear = buildYear;
        this.type = type;
    }

    // Getters and Setters
    public int getCarID() { return carID; }
    public void setCarID(int carID) { this.carID = carID; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getVIN() { return VIN; }
    public void setVIN(int VIN) { this.VIN = VIN; }

    public int getBuildYear() { return buildYear; }
    public void setBuildYear(int buildYear) { this.buildYear = buildYear; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}

