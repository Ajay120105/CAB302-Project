package org.example.grade_predictor.model;

public class User {

    private int user_ID;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String role;
    private String password;

    public User(String first_name, String last_name, String email, String phone, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    //GETTERS
    public int getUser_ID() {
        return user_ID;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    //DEFINITELY NEEDS TO BE CHANGED
    public String getPassword() {
        return password;
    }

    //SETTERS


    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Mock method used for testing
     * @return details about user
     */
    @Override
    public String toString() {
        return "User{" +
                "user_ID=" + user_ID +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
