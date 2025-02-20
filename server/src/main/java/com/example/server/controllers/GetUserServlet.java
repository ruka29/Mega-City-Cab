package com.example.server.controllers;

import com.example.server.models.Customer;
import com.example.server.models.User;
import com.example.server.services.UserService;
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

@WebServlet("/api/manage-users/get-user")
public class GetUserServlet extends HttpServlet {
    public final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> requestBody = JsonUtils.parseJsonRequest(request);
        String username = requestBody.get("username");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> jsonResponse = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        if (username != null) {
            User user = userService.getUser(username);
            if (user != null) {
                jsonResponse.put("status", "success");
                jsonResponse.put("user", user);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "User not found!");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "Username not included!");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        response.getWriter().write(objectMapper.writeValueAsString(jsonResponse));
    }
}
