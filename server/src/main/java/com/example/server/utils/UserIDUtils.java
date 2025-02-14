package com.example.server.utils;

import com.example.server.dao.UserDAO;
import com.example.server.models.User;

public class UserIDUtils {
    private static final UserDAO userDAO = new UserDAO();

    public static String generateUserID(String designation) {
        String userID = "001";
        
        User lastUser = userDAO.findLastUser(designation);
        
        if(lastUser != null) {
            String lastID = lastUser.getId();
            int numericID = Integer.parseInt(lastID);
            userID = String.format("%03d", numericID + 1);
        }

        return userID;
    }
}
