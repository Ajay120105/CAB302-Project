package org.example.grade_predictor.model.DAO;

import org.example.grade_predictor.model.User;
import org.example.grade_predictor.model.interfaces.I_User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO implements I_User {
    /**
     * A static list of contacts to be used as a mock database.
     */
    public static final ArrayList<User> users = new ArrayList<>();
    private static int autoIncrementedId = 0;

    @Override
    public void addUser(User user) {
        if (users.stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            throw new IllegalArgumentException("Email already exists!");
        }
        if (users.stream().anyMatch(u -> u.getPhone().equals(user.getPhone()))) {
            throw new IllegalArgumentException("Phone number already exists!");
        }
        user.setUser_ID(autoIncrementedId++);
        users.add(user);
    }

    @Override
    public void updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUser_ID() == user.getUser_ID()) {
                users.set(i, user);
                break;
            }
        }
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user);
    }

    @Override
    public User getUser(int user_ID) {
        for (User user : users) {
            if (user.getUser_ID() == user_ID) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}
