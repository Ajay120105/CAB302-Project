package org.example.grade_predictor.model;
import java.util.ArrayList;
import java.util.List;

public class User {

    private int user_ID;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String role;
    private String password;
    private List<Enrollment> enrollments;

    /**
     * Constructor for the User class
     * @param first_name The first name of the user
     * @param last_name The last name of the user
     * @param email The email of the user
     * @param phone The phone number of the user
     * @param password The password of the user
     */
    public User(String first_name, String last_name, String email, String phone, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.enrollments = new ArrayList<>();
    }

    /**
     * Get the id of the user
     * @return The id of the user
     */
    public int getUser_ID() {
        return user_ID;
    }

    /**
     * Get the first name of the user
     * @return The first name of the user
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * Get the last name of the user
     * @return The last name of the user
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * Get the email of the user
     * @return The email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get the phone number of the user
     * @return The phone number of the user
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Get the password of the user
     * @return The password of the user, currently has been hashed by BCrypt
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get the enrollments of the user
     * @return The enrollments of the user
     */
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    /**
     * Set the id of the user
     * @param user_ID The id of the user
     */
    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    /**
     * Set the first name of the user
     * @param first_name The first name of the user
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * Set the last name of the user
     * @param last_name The last name of the user
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * Set the email of the user
     * @param email The email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set the phone number of the user
     * @param phone The phone number of the user
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Set the password of the user
     * @param password The password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the enrollments of the user
     * @param enrollments The enrollments of the user
     */
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    /**
     * Add an enrollment to the user
     * @param enrollment The enrollment to add
     */
    public void addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
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
