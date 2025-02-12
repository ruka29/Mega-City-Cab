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

@WebServlet("/api/auth/login")
public class LoginServlet extends HttpServlet {
    private final EmployeeService employeeService = new EmployeeService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> requestBody = JsonUtils.parseJsonRequest(request);
        String username = requestBody.get("username");
        String password = requestBody.get("password");

        boolean success = employeeService.login(username, password);
        Map<String, String> responseJson = new HashMap<>();

        if (success) {
            responseJson.put("message", "Login Successful!");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            responseJson.put("message", "Invalid Username or Password!");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        JsonUtils.sendJsonResponse(response, responseJson);
    }
}
