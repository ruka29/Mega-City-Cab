package com.example.server.dao;

import com.example.server.config.MongoDBConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DistanceDAO {
    private final MongoCollection<Document> distanceCollection;

    public DistanceDAO() {
        try {
            MongoDatabase database = MongoDBConnection.getDatabase();
            this.distanceCollection = database.getCollection("distances");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getAllPickupLocations() {
        try {
            List<String> pickupLocations = new ArrayList<>();

            distanceCollection.find().forEach(doc -> {
                String pickup = doc.getString("pickup");

                if (!pickupLocations.contains(pickup)) {
                    pickupLocations.add(pickup);
                }
            });

            return pickupLocations;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getDistance(String pickup, String dropOff) {
        try {
            Document document = distanceCollection.find(Filters.eq("pickup", pickup)).first();

            if (document != null) {
                Document distances = (Document) document.get("distances");

                if (distances != null && distances.containsKey(dropOff)) {
                    return distances.getInteger(dropOff);
                }
            }

            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
