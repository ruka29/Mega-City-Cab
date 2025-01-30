package com.example.server.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    private static final String CONNECTION_STRING = "mongodb+srv://ruka29:Ruka2906@megacitycab.dz0ta.mongodb.net/?retryWrites=true&w=majority&appName=MegaCityCab";
    private static final String DATABASE_NAME = "MegaCityCab";

    private static MongoClient mongoClient = null;

    public static MongoDatabase connect() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(CONNECTION_STRING);
        }

        return mongoClient.getDatabase(DATABASE_NAME);
    }
}
