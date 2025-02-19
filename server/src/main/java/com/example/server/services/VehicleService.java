package com.example.server.services;

import com.example.server.dao.VehicleDAO;
import com.example.server.models.Vehicle;

public class VehicleService {
    private final VehicleDAO vehicleDAO = new VehicleDAO();

    public boolean addVehicle(String registrationNumber, String driverID, String type, String brand,String model, String year, String passengerCount, String insuranceExpDate, String licenseExpDate) {
        if (vehicleDAO.findByRegistrationNumber(registrationNumber) != null) {
            return false;
        }

        Vehicle vehicle = new Vehicle(registrationNumber, driverID, type, brand, model, year, "available", passengerCount, insuranceExpDate, licenseExpDate);
        return vehicleDAO.addVehicle(vehicle);
    }
}
