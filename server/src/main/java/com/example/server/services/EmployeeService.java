package com.example.server.services;

import com.example.server.dao.EmployeeDAO;
import com.example.server.models.Employee;
import com.example.server.utils.PasswordUtils;

public class EmployeeService {
    private final EmployeeDAO EmployeeDAO = new EmployeeDAO();

    public boolean login(String username, String password) {
        Employee employee = EmployeeDAO.findByUsername(username);
        if (employee != null) {
            return PasswordUtils.checkPassword(password, employee.getPassword());
        }
        return false;
    }

    public boolean register(String firstName, String lastName, String email, String username, String password, String designation) {
        if (EmployeeDAO.findByUsername(username) != null) {
            return false;
        }

        String hashedPassword = PasswordUtils.hashPassword(password);
        Employee employee = new Employee(null, firstName, lastName, email, username, hashedPassword, designation);
        return EmployeeDAO.registerUser(employee);
    }
}
