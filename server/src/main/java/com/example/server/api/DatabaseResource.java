package com.example.server.api;

import com.example.server.database.MongoDBConnection;
import com.mongodb.client.MongoDatabase;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/database")
public class DatabaseResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response testDatabaseConnection() {
        try {
            MongoDatabase database = MongoDBConnection.connect();
            return Response.ok("Connected to database : " + database.getName()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Faild to connect : " + e.getMessage()).build();
        }
    }
}
