package com.example.server.dao;

import com.example.server.models.Customer;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static com.mongodb.client.model.Filters.eq;

public class CustomerDAOTest {

    @Mock
    private MongoCollection<Document> customerCollection;

    @Mock
    private FindIterable<Document> mockFindIterable;

    @InjectMocks
    private CustomerDAO customerDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByPhone_WhenPhoneExists() {
        String phone = "0716441044";
        Document mockDoc = new Document()
                .append("id", "005")
                .append("firstName", "Nadun")
                .append("lastName", "Warshan")
                .append("email", "warshan@gmail.com")
                .append("phone", phone)
                .append("address", "Kaduwela, Athurugiriya.")
                .append("NIC", "123456789098");

        when(customerCollection.find(eq("phone", phone))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(mockDoc);

        Customer result = customerDAO.findByPhone(phone);

        assertNotNull(result);
        assertEquals("005", result.getId());
    }

    @Test
    void testFindByPhone_WhenPhoneNotFound() {
        when(customerCollection.find(eq("phone", "9999999999"))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(null);

        Customer result = customerDAO.findByPhone("9999999999");
        assertNull(result);
    }

    @Test
    void testFindLastCustomer() {
        Document mockDoc = new Document()
                .append("id", "005")
                .append("firstName", "Nadun")
                .append("lastName", "Warshan")
                .append("email", "warshan@gmail.com")
                .append("phone", "0716441044")
                .append("address", "Kaduwela, Athurugiriya.")
                .append("NIC", "123456789098");

        when(customerCollection.find()).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(Sorts.descending("id"))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(mockDoc);

        Customer result = customerDAO.findLastCustomer();
        assertNotNull(result);
    }

    @Test
    void testRegisterCustomer() {
        Customer customer = new Customer("006", "New", "User", "newuser@gmail.com",
                "0777777777", "Colombo, Sri Lanka", "987654321098");

        boolean result = customerDAO.registerCustomer(customer);

        assertTrue(result);
    }

    @Test
    void testUpdateCustomer() {
        Customer customer = new Customer("006", "Nadun", "UpdatedWarshan", "updated.warshan@gmail.com",
                "0777777777", "Colombo, Sri Lanka", "987654321098");
        UpdateResult mockResult = mock(UpdateResult.class);
        when(mockResult.getModifiedCount()).thenReturn(1L);
        when(customerCollection.updateOne(any(Bson.class), any(Bson.class))).thenReturn(mockResult);

        boolean result = customerDAO.updateCustomer(customer);

        assertTrue(result);
    }
}
