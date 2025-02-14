package com.example.server.services;

import com.example.server.dao.UserDAO;
import com.example.server.models.User;
import com.example.server.utils.PasswordUtils;
import com.example.server.utils.UserIDUtils;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public boolean login(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user != null) {
            return PasswordUtils.checkPassword(password, user.getPassword());
        }
        return false;
    }

    public boolean register(String firstName, String lastName, String email, String phone, String username, String password, String designation) {
        if (userDAO.findByUsername(username) != null) {
            return false;
        }

        String hashedPassword = PasswordUtils.hashPassword(password);
        String userID = UserIDUtils.generateUserID(designation);
        User user = new User(userID, firstName, lastName, email, phone, username, hashedPassword, designation);
        return userDAO.registerEmployee(user);
    }


}
