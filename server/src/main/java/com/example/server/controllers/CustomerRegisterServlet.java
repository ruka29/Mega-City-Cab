package com.example.server.controllers;

import com.example.server.services.CustomerService;
import com.example.server.utils.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/manage-customers/register")
public class CustomerRegisterServlet extends HttpServlet {
    public final CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> requestBody = JsonUtils.parseJsonRequest(request);

        String firstName = requestBody.get("firstName");
        String lastName = requestBody.get("lastName");
        String email = requestBody.get("email");
        String phone = requestBody.get("phone");
        String address = requestBody.get("address");
        String NIC = requestBody.get("NIC");

        Map<String, String> jsonResponse = new HashMap<>();
        if (firstName == null || lastName == null || email == null || phone == null || address == null || NIC == null) {
            jsonResponse.put("message", "All fields are required");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            boolean success = customerService.addCustomer(firstName, lastName, email, phone, address, NIC);
            if (success) {
                jsonResponse.put("message", "Customer added successfully!");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                jsonResponse.put("message", "Customer registration failed! Customer already exists!");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }

        JsonUtils.sendJsonResponse(response, jsonResponse);
    }
}
