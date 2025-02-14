package com.example.server.controllers;

import com.example.server.services.UserService;
import com.example.server.utils.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/auth/register")
public class RegisterServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> requestBody = JsonUtils.parseJsonRequest(request);

        String firstName = requestBody.get("firstName");
        String lastName = requestBody.get("lastName");
        String email = requestBody.get("email");
        String phone = requestBody.get("phone");
        String username = requestBody.get("username");
        String password = requestBody.get("password");
        String designation = requestBody.get("designation");

        Map<String, String> jsonResponse = new HashMap<>();
        if (firstName == null || lastName == null || email == null || phone == null || username == null || password == null || designation == null) {
            jsonResponse.put("message", "All fields are required");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            boolean success = userService.register(firstName, lastName, email, phone, username, password, designation);
            if (success) {
                jsonResponse.put("message", designation + " registered successfully!");
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }

        JsonUtils.sendJsonResponse(response, jsonResponse);
    }
}
