package com.example.server.dao;

import com.example.server.config.MongoDBConnection;
import com.example.server.models.Vehicle;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class VehicleDAO {
    private final MongoCollection<Document> vehicleCollection;

    public VehicleDAO() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.vehicleCollection = database.getCollection("vehicles");
    }

    public Vehicle findByRegistrationNumber(String registrationNumber) {
        Document vehicleDoc = vehicleCollection.find(eq("registrationNumber", registrationNumber)).first();

        if (vehicleDoc != null) {
            return new Vehicle(
                vehicleDoc.getString("registrationNumber"),
                vehicleDoc.getString("driverID"),
                vehicleDoc.getString("type"),
                vehicleDoc.getString("brand"),
                vehicleDoc.getString("model"),
                vehicleDoc.getString("year"),
                vehicleDoc.getString("passengerCount"),
                vehicleDoc.getString("insuranceExpDate"),
                vehicleDoc.getString("licenseExpDate")
            );
        }

        return null;
    }

    public boolean addVehicle(Vehicle vehicle) {
        Document newVehicle = new Document()
                .append("registrationNumber", vehicle.getRegistrationNumber())
                .append("driverID", vehicle.getDriverID())
                .append("type", vehicle.getType())
                .append("brand", vehicle.getBrand())
                .append("model", vehicle.getModel())
                .append("year", vehicle.getYear())
                .append("passengerCount", vehicle.getPassengerCount())
                .append("InsuranceExpDate", vehicle.getInsuranceExpDate())
                .append("licenseExpDate", vehicle.getLicenseExpDate());

        vehicleCollection.insertOne(newVehicle);
        return true;
    }
}
