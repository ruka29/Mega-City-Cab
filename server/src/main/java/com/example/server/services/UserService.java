package com.example.server.services;

import com.example.server.dao.userDAO;
import com.example.server.models.User;
import com.example.server.utils.PasswordUtils;

public class UserService {
    private final userDAO userDAO = new userDAO();

    public boolean login(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user != null) {
            return PasswordUtils.checkPassword(password, user.getPassword());
        }
        return false;
    }

    public boolean register(String firstName, String lastName, String email, String username, String password, String designation) {
        if (userDAO.findByUsername(username) != null) {
            return false;
        }

        String hashedPassword = PasswordUtils.hashPassword(password);
        User user = new User(null, firstName, lastName, email, username, hashedPassword, designation);
        return userDAO.registerUser(user);
    }
}
