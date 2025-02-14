package com.example.server.models;

public class Vehicle {
    private String registrationNumber;
    private String driverID;
    private String type;
    private String brand;
    private String model;
    private String year;
    private String passengerCount;
    private String insuranceExpDate;
    private String licenseExpDate;

    public Vehicle() {}

    public Vehicle(String registrationNumber, String driverID, String type, String brand, String model, String year, String passengerCount, String insuranceExpDate, String licenseExpDate) {
        this.registrationNumber = registrationNumber;
        this.driverID = driverID;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.passengerCount = passengerCount;
        this.insuranceExpDate = insuranceExpDate;
        this.licenseExpDate = licenseExpDate;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getDriverID() {
        return driverID;
    }
    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }

    public String getPassengerCount() {
        return passengerCount;
    }
    public void setPassengerCount(String passengerCount) {
        this.passengerCount = passengerCount;
    }

    public String getInsuranceExpDate() {
        return insuranceExpDate;
    }
    public void setInsuranceExpDate(String insuranceExpDate) {
        this.insuranceExpDate = insuranceExpDate;
    }

    public String getLicenseExpDate() {
        return licenseExpDate;
    }
    public void setLicenseExpDate(String licenseExpDate) {
        this.licenseExpDate = licenseExpDate;
    }
}
