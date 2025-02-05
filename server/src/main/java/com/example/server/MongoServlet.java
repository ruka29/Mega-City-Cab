package com.example.server;

import java.io.*;

import com.example.server.config.MongoDBConnection;
import com.mongodb.client.MongoDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "testDatabase", value = "/mongo-test")
public class MongoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try{
            MongoDatabase database = MongoDBConnection.getDatabase();
            out.println("Connected to database: " + database.getName());
        } catch (Exception e){
            out.println("Error: " + e.getMessage());
        }
    }
}