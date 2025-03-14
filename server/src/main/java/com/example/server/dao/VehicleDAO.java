package com.example.server.dao;

import com.example.server.config.MongoDBConnection;
import com.example.server.models.Vehicle;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.eq;

public class VehicleDAO {
    private final MongoCollection<Document> vehicleCollection;

    public VehicleDAO() {
        try {
            MongoDatabase database = MongoDBConnection.getDatabase();
            this.vehicleCollection = database.getCollection("vehicles");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Vehicle findByRegistrationNumber(String registrationNumber) {
        try {
            Document vehicleDoc = vehicleCollection.find(eq("registrationNumber", registrationNumber)).first();

            return getVehicle(vehicleDoc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Vehicle findByAvailability(String type) {
        try {
            List<Document> vehicles = vehicleCollection.find(
                    Filters.and(Filters.eq("type", type), Filters.eq("status", "available"))
            ).into(new ArrayList<>());

            Document vehicleDoc = vehicles.isEmpty() ? null : vehicles.get(new Random().nextInt(vehicles.size()));

            return getVehicle(vehicleDoc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Vehicle getVehicle(Document vehicleDoc) {
        try {
            if (vehicleDoc != null) {
                return new Vehicle(
                        vehicleDoc.getString("registrationNumber"),
                        vehicleDoc.getString("username"),
                        vehicleDoc.getString("type"),
                        vehicleDoc.getString("brand"),
                        vehicleDoc.getString("model"),
                        vehicleDoc.getString("year"),
                        vehicleDoc.getString("status"),
                        vehicleDoc.getString("passengerCount"),
                        vehicleDoc.getString("insuranceExpDate"),
                        vehicleDoc.getString("licenseExpDate")
                );
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addVehicle(Vehicle vehicle) {
        try {
            Document newVehicle = new Document()
                    .append("registrationNumber", vehicle.getRegistrationNumber())
                    .append("username", vehicle.getUsername())
                    .append("type", vehicle.getType())
                    .append("brand", vehicle.getBrand())
                    .append("model", vehicle.getModel())
                    .append("year", vehicle.getYear())
                    .append("status", vehicle.getStatus())
                    .append("passengerCount", vehicle.getPassengerCount())
                    .append("InsuranceExpDate", vehicle.getInsuranceExpDate())
                    .append("licenseExpDate", vehicle.getLicenseExpDate());

            vehicleCollection.insertOne(newVehicle);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vehicle> getAllVehicles() {
        try {
            List<Vehicle> vehicles = new ArrayList<>();

            FindIterable<Document> vehicleDocs = vehicleCollection.find();
            for (Document vehicleDoc : vehicleDocs) {
                vehicles.add(new Vehicle(
                        vehicleDoc.getString("registrationNumber"),
                        vehicleDoc.getString("username"),
                        vehicleDoc.getString("type"),
                        vehicleDoc.getString("brand"),
                        vehicleDoc.getString("model"),
                        vehicleDoc.getString("year"),
                        vehicleDoc.getString("status"),
                        vehicleDoc.getString("passengerCount"),
                        vehicleDoc.getString("insuranceExpDate"),
                        vehicleDoc.getString("licenseExpDate")
                ));
            }

            return vehicles;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
