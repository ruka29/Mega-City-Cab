package com.example.server.controllers;

import com.example.server.models.Vehicle;
import com.example.server.services.VehicleService;
import com.example.server.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/manage-vehicles/get-available-vehicle")
public class GetAvailableVehicleServlet extends HttpServlet {
    public final VehicleService vehicleService = new VehicleService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> requestBody = JsonUtils.parseJsonRequest(request);
        String type = requestBody.get("type");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> jsonResponse = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        if (type != null) {
            Vehicle vehicle = vehicleService.getAvailableVehicle(type);
            if (vehicle != null) {
                jsonResponse.put("status", "success");
                jsonResponse.put("vehicle", vehicle);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "vehicle not found!");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "Vehicle type not included!");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        response.getWriter().write(objectMapper.writeValueAsString(jsonResponse));
    }
}
