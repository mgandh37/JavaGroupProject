package com.example.mauligandhi_comp228lab5;

import java.util.Date;

public class Repair {
    private int repairID;
    private int ownerID;
    private int carID;
    private Date serviceDate;
    private String description;
    private int cost;

    // Constructor
    public Repair(int repairID, int ownerID, int carID, Date serviceDate, String description, int cost) {
        this.repairID = repairID;
        this.ownerID = ownerID;
        this.carID = carID;
        this.serviceDate = serviceDate;
        this.description = description;
        this.cost = cost;
    }

    // Getters and Setters
    public int getRepairID() { return repairID; }
    public void setRepairID(int repairID) { this.repairID = repairID; }

    public int getOwnerID() { return ownerID; }
    public void setOwnerID(int ownerID) { this.ownerID = ownerID; }

    public int getCarID() { return carID; }
    public void setCarID(int carID) { this.carID = carID; }

    public Date getServiceDate() { return serviceDate; }
    public void setServiceDate(Date serviceDate) { this.serviceDate = serviceDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getCost() { return cost; }
    public void setCost(int cost) { this.cost = cost; }
}

