package com.example.server.utils;

import com.example.server.dao.CustomerDAO;
import com.example.server.dao.UserDAO;
import com.example.server.models.User;

public class IDUtils {
    private static final String DEFAULT_ID = "001";

    public static String generateUserID(UserDAO userDAO, String designation) {
        return generateID(userDAO.findLastUser(designation));
    }

    public static String generateCustomerID(CustomerDAO customerDAO) {
        return generateID(customerDAO.findLastCustomer());
    }

    public static String generateID(Object lastUser) {
        String newID = DEFAULT_ID;
        
        if(lastUser != null) {
            try {
                String lastID = (String) lastUser.getClass().getMethod("getID").invoke(lastUser);
                int numericID = Integer.parseInt(lastID);
                newID = String.format("%03d", numericID + 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return newID;
    }
}
