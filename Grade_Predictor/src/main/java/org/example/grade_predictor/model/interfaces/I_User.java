package org.example.grade_predictor.model.interfaces;

import org.example.grade_predictor.model.User;

import java.util.List;

public interface I_User {
    /**
     * Adds a new user to the database.
     * @param user The user to add.
     */
    public void addUser(User user);
    /**
     * Updates an existing user in the database.
     * @param user The user to update.
     */
    public void updateUser(User user);
    /**
     * Deletes a user from the database.
     * @param user The user to delete.
     */
    public void deleteUser(User user);
    /**
     * Retrieves a user from the database.
     * @param user_ID The id of the user to retrieve.
     * @return The user with the given id, or null if not found.
     */
    public User getUser(int user_ID);
    /**
     * Retrieves all users from the database.
     *
     * @return A list of all users in the database.
     */
    public List<User> getAllUsers();
}
