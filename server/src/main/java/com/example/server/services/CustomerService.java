package com.example.server.services;

import com.example.server.dao.CustomerDAO;
import com.example.server.models.Customer;
import com.example.server.utils.IDUtils;

public class CustomerService {
    private final CustomerDAO customerDAO = new CustomerDAO();

    public boolean addCustomer(String firstName, String lastName, String email, String phone, String address, String NIC) {
        if (customerDAO.findByPhone(phone) != null) {
            return false;
        }

        String id = IDUtils.generateCustomerID(customerDAO);
        Customer customer = new Customer(id, firstName, lastName, email, phone, address, NIC);
        return customerDAO.registerCustomer(customer);
    }
}
