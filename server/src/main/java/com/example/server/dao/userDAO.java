package com.example.server.dao;

import com.example.server.config.MongoDBConnection;
import com.example.server.models.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class userDAO {
    private final MongoCollection<Document> userCollection;

    public userDAO() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.userCollection = database.getCollection("users");
    }

    public User findByUsername(String username) {
        Document userDoc = userCollection.find(eq("username", username)).first();

        if(userDoc != null) {
            return new User(
                userDoc.getString("id"),
                userDoc.getString("firstName"),
                userDoc.getString("lastName"),
                userDoc.getString("email"),
                userDoc.getString("username"),
                userDoc.getString("password"),
                userDoc.getString("designation")
            );
        }

        return null;
    }

    public boolean registerUser(User user) {
        Document newUser = new Document()
                .append("firstName", user.getFirstName())
                .append("lastName", user.getLastName())
                .append("email", user.getEmail())
                .append("username", user.getUsername())
                .append("password", user.getPassword())
                .append("designation", user.getDesignation());

        userCollection.insertOne(newUser);
        return true;
    }
}
