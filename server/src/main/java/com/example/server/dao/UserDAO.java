package com.example.server.dao;

import com.example.server.config.MongoDBConnection;
import com.example.server.models.User;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class UserDAO {
    private final MongoCollection<Document> userCollection;

    public UserDAO() {
        try {
            MongoDatabase database = MongoDBConnection.getDatabase();
            this.userCollection = database.getCollection("users");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User findByUsername(String username) {
        try {
            Document userDoc = userCollection.find(eq("username", username)).first();

            if(userDoc != null) {
                return new User(
                        userDoc.getString("userID"),
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User findByID(String id) {
        try {
            Document userDoc = userCollection.find(eq("userID", id)).first();

            if(userDoc != null) {
                return new User(
                        userDoc.getString("userID"),
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User findLastUser() {
        try {
            Document lastUser = userCollection.find().sort(Sorts.descending("userID")).first();

            if(lastUser != null) {
                return new User(
                        lastUser.getString("userID"),
                        lastUser.getString("firstName"),
                        lastUser.getString("lastName"),
                        lastUser.getString("email"),
                        lastUser.getString("phone"),
                        lastUser.getString("username"),
                        lastUser.getString("password"),
                        lastUser.getString("designation")
                );
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean registerEmployee(User user) {
        try {
            Document newUser = new Document()
                    .append("userID", user.getId())
                    .append("firstName", user.getFirstName())
                    .append("lastName", user.getLastName())
                    .append("email", user.getEmail())
                    .append("phone", user.getPhone())
                    .append("username", user.getUsername())
                    .append("password", user.getPassword())
                    .append("designation", user.getDesignation());

            userCollection.insertOne(newUser);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateUser(User user) {
        try {
            Document updatedUser = new Document()
                    .append("firstName", user.getFirstName())
                    .append("lastName", user.getLastName())
                    .append("email", user.getEmail())
                    .append("phone", user.getPhone())
                    .append("username", user.getUsername())
                    .append("designation", user.getDesignation());

            UpdateResult result = userCollection.updateOne(
                    Filters.eq("userID", user.getId()),
                    new Document("$set", updatedUser)
            );

            return result.getModifiedCount() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers(String designation) {
        try {
            List<User> drivers = new ArrayList<>();

            FindIterable<Document> driverDocs = userCollection.find(eq("designation", designation));
            for (Document doc : driverDocs) {
                drivers.add(new User(
                        doc.getString("userID"),
                        doc.getString("firstName"),
                        doc.getString("lastName"),
                        doc.getString("email"),
                        doc.getString("phone"),
                        doc.getString("username"),
                        doc.getString("designation")
                ));
            }
            return drivers;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
