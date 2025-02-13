package com.example.server.services;

import com.example.server.dao.UserDAO;
import com.example.server.models.User;
import com.example.server.utils.PasswordUtils;

public class UserService {
    private final UserDAO UserDAO = new UserDAO();

    public boolean login(String username, String password) {
        User user = UserDAO.findByUsername(username);
        if (user != null) {
            return PasswordUtils.checkPassword(password, user.getPassword());
        }
        return false;
    }

    public boolean register(String firstName, String lastName, String email, String phone, String username, String password, String designation) {
        if (UserDAO.findByUsername(username) != null) {
            return false;
        }

        String hashedPassword = PasswordUtils.hashPassword(password);
        User user = new User(null, firstName, lastName, email, phone, username, hashedPassword, designation);
        return UserDAO.registerEmployee(user);
    }
}
