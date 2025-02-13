package com.example.server.dao;

import com.example.server.config.MongoDBConnection;
import com.example.server.models.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class UserDAO {
    private final MongoCollection<Document> userCollection;

    public UserDAO() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.userCollection = database.getCollection("employees");
    }

    public User findByUsername(String username) {
        Document userDoc = userCollection.find(eq("username", username)).first();

        if(userDoc != null) {
            return new User(
                userDoc.getString("id"),
                userDoc.getString("firstName"),
                userDoc.getString("lastName"),
                userDoc.getString("email"),
                userDoc.getString("phone"),
                userDoc.getString("username"),
                userDoc.getString("password"),
                userDoc.getString("designation")
            );
        }

        return null;
    }

    public boolean registerEmployee(User user) {
        Document newUser = new Document()
                .append("firstName", user.getFirstName())
                .append("lastName", user.getLastName())
                .append("email", user.getEmail())
                .append("phone", user.getPhone())
                .append("username", user.getUsername())
                .append("password", user.getPassword())
                .append("designation", user.getDesignation());

        userCollection.insertOne(newUser);
        return true;
    }
}
