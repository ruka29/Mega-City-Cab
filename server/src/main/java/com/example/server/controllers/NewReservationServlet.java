package com.example.server.controllers;

import com.example.server.services.ReservationService;
import com.example.server.utils.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/manage-reservation/new")
public class NewReservationServlet extends HttpServlet {
    public final ReservationService reservationService = new ReservationService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> requestBody = JsonUtils.parseJsonRequest(request);

        String vehicleID = requestBody.get("vehicleID");
        String driverID = requestBody.get("driverID");
        String customerID = requestBody.get("customerID");
        String date = requestBody.get("date");
        String time = requestBody.get("time");
        String pickLocation = requestBody.get("pickLocation");
        String dropLocation = requestBody.get("dropLocation");
        double fare = Double.parseDouble(requestBody.get("fare"));

        Map<String, String> jsonResponse = new HashMap<>();
        if (vehicleID == null || driverID == null || customerID == null || time == null || date == null || pickLocation == null || dropLocation == null || fare <= 0) {
            jsonResponse.put("message", "All fields are required");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            boolean success = reservationService.addReservation(vehicleID, driverID, customerID, date, time, pickLocation, dropLocation, fare);
            if (success) {
                jsonResponse.put("message", "Reservation added successfully!");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                jsonResponse.put("message", "Reservation failed!");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }

        JsonUtils.sendJsonResponse(response, jsonResponse);
    }
}
