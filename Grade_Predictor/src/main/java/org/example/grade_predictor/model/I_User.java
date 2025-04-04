package org.example.grade_predictor.model;
import java.util.List;
public interface I_User {
    /**
     * Adds a new user to the database.
     * @param user The contact to add.
     */
    public void addContact(User user);
    /**
     * Updates an existing user in the database.
     * @param user The contact to update.
     */
    public void updateContact(User user);
    /**
     * Deletes a user from the database.
     * @param user The contact to delete.
     */
    public void deleteContact(User user);
    /**
     * Retrieves a contact from the database.
     * @param user_ID The id of the user to retrieve.
     * @return The contact with the given id, or null if not found.
     */
    public User getContact(int user_ID);
    /**
     * Retrieves all contacts from the database.
     * @return A list of all contacts in the database.
     */
    public List<User> getAllUsers();
}
