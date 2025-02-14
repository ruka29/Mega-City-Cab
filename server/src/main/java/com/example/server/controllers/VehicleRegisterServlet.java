package com.example.server.controllers;

import com.example.server.services.VehicleService;
import com.example.server.utils.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/manage-vehicles/register")
public class VehicleRegisterServlet extends HttpServlet {
    private final VehicleService vehicleService = new VehicleService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> requestBody = JsonUtils.parseJsonRequest(request);

        String registrationNumber = requestBody.get("registrationNumber");
        String driverID = requestBody.get("driverID");
        String type = requestBody.get("type");
        String brand = requestBody.get("brand");
        String model = requestBody.get("model");
        String year = requestBody.get("year");
        String passengerCount = requestBody.get("passengerCount");
        String insuranceExpDate = requestBody.get("insuranceExpDate");
        String licenseExpDate = requestBody.get("licenseExpDate");

        Map<String, String> jsonResponse = new HashMap<>();
        if(registrationNumber == null || driverID == null || type == null || brand == null || model == null || year == null || passengerCount == null || insuranceExpDate == null || licenseExpDate == null) {
            jsonResponse.put("message", "All fields are required!");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            boolean success = vehicleService.addVehicle(registrationNumber, driverID, type, brand, model, year, passengerCount, insuranceExpDate, licenseExpDate);
            if(success) {
                jsonResponse.put("message", "Vehicle registered successfully!");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                jsonResponse.put("message", "Vehicle registration failed! Vehicle already exists!");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }

        JsonUtils.sendJsonResponse(response, jsonResponse);
    }
}
