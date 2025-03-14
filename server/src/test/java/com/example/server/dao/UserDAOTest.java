package com.example.server.dao;

import com.example.server.models.User;
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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.mongodb.client.model.Filters.eq;

public class UserDAOTest {

    @Mock
    private MongoCollection<Document> userCollection;

    @Mock
    private FindIterable<Document> mockFindIterable;

    @InjectMocks
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByUsername_WhenUsernameExists() {
        String username = "emp_john";
        Document mockDoc = new Document()
                .append("userID", "006")
                .append("firstName", "John")
                .append("lastName", "Doe")
                .append("email", "john@gmail.com")
                .append("phone", "0702820529")
                .append("username", username)
                .append("password", "$2a$12$hashedpassword")
                .append("designation", "Employee");

        when(userCollection.find(eq("username", username))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(mockDoc);

        User result = userDAO.findByUsername(username);

        assertNotNull(result);
        assertEquals("006", result.getId());
        assertEquals(username, result.getUsername());
    }

    @Test
    void testFindByUsername_WhenUsernameNotFound() {
        when(userCollection.find(eq("username", "unknown_user"))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(null);

        User result = userDAO.findByUsername("unknown_user");

        assertNull(result);
    }

    @Test
    void testFindByID_WhenIDExists() {
        String userID = "006";
        Document mockDoc = new Document()
                .append("userID", userID)
                .append("firstName", "John")
                .append("lastName", "Doe")
                .append("email", "john@gmail.com")
                .append("phone", "0702820529")
                .append("username", "emp_john")
                .append("password", "$2a$12$hashedpassword")
                .append("designation", "Employee");

        when(userCollection.find(eq("userID", userID))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(mockDoc);

        User result = userDAO.findByID(userID);

        assertNotNull(result);
        assertEquals(userID, result.getId());
    }

    @Test
    void testFindByID_WhenIDNotFound() {
        when(userCollection.find(eq("userID", "999"))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(null);

        User result = userDAO.findByID("999");

        assertNull(result);
    }

    @Test
    void testFindLastUser() {
        Document mockDoc = new Document()
                .append("userID", "006")
                .append("firstName", "John")
                .append("lastName", "Doe")
                .append("email", "john@gmail.com")
                .append("phone", "0702820529")
                .append("username", "emp_john")
                .append("password", "$2a$12$hashedpassword")
                .append("designation", "Employee");

        when(userCollection.find()).thenReturn(mockFindIterable);
        when(mockFindIterable.sort(Sorts.descending("userID"))).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(mockDoc);

        User result = userDAO.findLastUser();
        assertNotNull(result);
    }

    @Test
    void testRegisterEmployee() {
        User user = new User("007", "Jane", "Doe", "jane@gmail.com",
                "0701234567", "emp_jane", "$2a$12$hashedpassword", "Employee");

        boolean result = userDAO.registerEmployee(user);

        assertTrue(result);
    }

    @Test
    void testUpdateUser() {
        User user = new User("006", "John", "UpdatedDoe", "jone_updated@gmail.com",
                "0709876543", "emp_john", "$2a$12$hashedpassword", "Employee");

        UpdateResult mockResult = mock(UpdateResult.class);
        when(mockResult.getModifiedCount()).thenReturn(1L);
        when(userCollection.updateOne(any(Bson.class), any(Bson.class))).thenReturn(mockResult);

        boolean result = userDAO.updateUser(user);

        assertTrue(result);
    }

    @Test
    void testGetAllUsers_WithDesignation() {
        List<Document> mockDocs = new ArrayList<>();
        Document user1 = new Document()
                .append("userID", "006")
                .append("firstName", "John")
                .append("lastName", "Doe")
                .append("email", "john@gmail.com")
                .append("phone", "0702820529")
                .append("username", "emp_john")
                .append("designation", "Employee");

        Document user2 = new Document()
                .append("userID", "007")
                .append("firstName", "Jane")
                .append("lastName", "Doe")
                .append("email", "jane@gmail.com")
                .append("phone", "0701234567")
                .append("username", "emp_jane")
                .append("designation", "Employee");

        mockDocs.add(user1);
        mockDocs.add(user2);

        when(userCollection.find(eq("designation", "Employee"))).thenReturn(mockFindIterable);

        // Instead of mocking iterator(), we mock forEach()
        doAnswer(invocation -> {
            Consumer<Document> consumer = invocation.getArgument(0);
            mockDocs.forEach(consumer);
            return null;
        }).when(mockFindIterable).forEach(any());

        List<User> users = userDAO.getAllUsers("Employee");

        assertNotNull(users);
    }
}

