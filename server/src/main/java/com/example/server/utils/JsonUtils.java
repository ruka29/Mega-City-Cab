package com.example.server.utils;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonUtils {
    private static final Jsonb jsonb = JsonbBuilder.create();

    public static Map<String, String> parseJsonRequest(HttpServletRequest request) throws IOException {
        StringBuilder json = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        }

        return jsonb.fromJson(json.toString(), HashMap.class);
    }

    public static void sendJsonResponse(HttpServletResponse response, Map<String, String> jsonResponse) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonb.toJson(jsonResponse));
    }
}
