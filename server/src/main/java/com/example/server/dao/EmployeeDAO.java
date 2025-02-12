package com.example.server.dao;

import com.example.server.config.MongoDBConnection;
import com.example.server.models.Employee;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class EmployeeDAO {
    private final MongoCollection<Document> userCollection;

    public EmployeeDAO() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.userCollection = database.getCollection("users");
    }

    public Employee findByUsername(String username) {
        Document userDoc = userCollection.find(eq("username", username)).first();

        if(userDoc != null) {
            return new Employee(
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

    public boolean registerUser(Employee employee) {
        Document newUser = new Document()
                .append("firstName", employee.getFirstName())
                .append("lastName", employee.getLastName())
                .append("email", employee.getEmail())
                .append("username", employee.getUsername())
                .append("password", employee.getPassword())
                .append("designation", employee.getDesignation());

        userCollection.insertOne(newUser);
        return true;
    }
}
