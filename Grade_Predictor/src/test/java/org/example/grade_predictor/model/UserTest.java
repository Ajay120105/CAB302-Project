package org.example.grade_predictor.model;

import org.example.grade_predictor.model.DAO.UserDAO;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class UserTest {

    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
    }

    @Test
    void testAddUser() {
        User user = new User("John", "Doe", "john.doe@example.com", "123456789", "securePassword");
        userDAO.addUser(user);

        User retrievedUser = userDAO.getUser(user.getUser_ID());
        assertNotNull(retrievedUser, "User should be added to the database!");
        assertEquals("John", retrievedUser.getFirst_name());
        assertEquals("Doe", retrievedUser.getLast_name());
        assertEquals("john.doe@example.com", retrievedUser.getEmail());
    }

    /*@Test
    void testUpdateUser() {
        User user = new User("Alice", "Smith", "alice.smith@example.com", "987654321", "password123");
        userDAO.addUser(user);

        user.setFirst_name("Alicia");
        user.setPhone("555111222");
        userDAO.updateUser(user);

        User updatedUser = userDAO.getUser(user.getUser_ID());
        assertNotNull(updatedUser);
        assertEquals("Alicia", updatedUser.getFirst_name());
        assertEquals("555111222", updatedUser.getPhone());
    }

     */

    @Test
    void testDeleteUser() {
        User user = new User("Bob", "Brown", "bob.brown@example.com", "654321987", "securePass");
        userDAO.addUser(user);

        userDAO.deleteUser(user);
        User retrievedUser = userDAO.getUser(user.getUser_ID());
        assertNull(retrievedUser, "User should be deleted from the database!");
    }

    @Test
    void testGetAllUsers() {
        userDAO.addUser(new User("Charlie", "Davis", "charlie.davis@example.com", "111222333", "testPass"));
        userDAO.addUser(new User("Emily", "Evans", "emily.evans@example.com", "444555666", "myPassword"));

        List<User> users = userDAO.getAllUsers();
        assertTrue(users.size() >= 2, "There should be at least 2 users in the database.");
    }

    @Test
    void testPreventDuplicateEmail() {
        User user1 = new User("Alice", "Smith", "alice.smith@example.com", "987654321", "strongPass");
        User user2 = new User("Bob", "Smith", "alice.smith@example.com", "111222333", "anotherPass"); // Same email

        userDAO.addUser(user1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userDAO.addUser(user2));
        assertEquals("Email already exists!", exception.getMessage());
    }

    /*@Test
    void testPreventDuplicatePhoneNumber() {
        User user1 = new User("Charlie", "Brown", "charlie.brown@example.com", "987654321", "securePass");
        User user2 = new User("David", "Brown", "david.brown@example.com", "987654321", "newPass"); // Same phone

        userDAO.addUser(user1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userDAO.addUser(user2));
        assertEquals("Phone number already exists!", exception.getMessage());
    }

     */
}


