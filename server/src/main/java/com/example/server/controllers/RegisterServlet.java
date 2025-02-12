package com.example.server.controllers;

import com.example.server.services.EmployeeService;
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
    private final EmployeeService employeeService = new EmployeeService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> requestBody = JsonUtils.parseJsonRequest(request);

        String firstName = requestBody.get("firstName");
        String lastName = requestBody.get("lastName");
        String email = requestBody.get("email");
        String username = requestBody.get("username");
        String password = requestBody.get("password");
        String designation = requestBody.get("designation");

        Map<String, String> jsonResponse = new HashMap<>();
        if (firstName == null || lastName == null || email == null || username == null || password == null || designation == null) {
            jsonResponse.put("message", "All fields are required");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            boolean success = employeeService.register(firstName, lastName, email, username, password, designation);
            if (success) {
                jsonResponse.put("message", "Employee registered successfully!");
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }

        JsonUtils.sendJsonResponse(response, jsonResponse);
    }
}
