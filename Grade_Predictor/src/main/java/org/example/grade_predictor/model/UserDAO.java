package org.example.grade_predictor.model;

import java.util.ArrayList;
import java.util.List;

public class UserDAO implements I_User{
    /**
     * A static list of contacts to be used as a mock database.
     */
    public static final ArrayList<User> users = new ArrayList<>();
    private static int autoIncrementedId = 0;

    @Override
    public void addUser(User user) {
        user.setUser_ID(autoIncrementedId);
        autoIncrementedId++;
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
