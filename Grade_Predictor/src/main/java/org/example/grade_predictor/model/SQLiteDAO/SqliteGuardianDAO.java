package org.example.grade_predictor.model.SQLiteDAO;

import org.example.grade_predictor.model.interfaces.I_Guardian;
import org.example.grade_predictor.model.Guardian;
import org.example.grade_predictor.model.SqliteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqliteGuardianDAO implements I_Guardian{

    private final Connection connection;

    public SqliteGuardianDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS guardians (" +
                    "guardian_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_ID INTEGER NOT NULL," +
                    "student_ID INTEGER NOT NULL," +
                    "FOREIGN KEY(user_ID) REFERENCES users(user_ID)," +
                    "FOREIGN KEY(student_ID) REFERENCES students(student_ID)" +
                    ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void addGuardian(Guardian guardian) {
        try {
            // Insert into users table
            PreparedStatement userStmt = connection.prepareStatement(
                    "INSERT INTO users (firstName, lastName, email, phone, password) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            userStmt.setString(1, guardian.getFirst_name());
            userStmt.setString(2, guardian.getLast_name());
            userStmt.setString(3, guardian.getEmail());
            userStmt.setString(4, guardian.getPhone());
            userStmt.setString(5, guardian.getPassword());
            userStmt.executeUpdate();

            ResultSet userKeys = userStmt.getGeneratedKeys();
            if (userKeys.next()) {
                guardian.setUser_ID(userKeys.getInt(1));
            }

            // Insert into guardians table
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO guardians (user_ID, student_ID) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            stmt.setInt(1, guardian.getUser_ID());
            stmt.setInt(2, guardian.getStudent_id());
            stmt.executeUpdate();

            ResultSet guardianKeys = stmt.getGeneratedKeys();
            if (guardianKeys.next()) {
                guardian.setGuardian_id(guardianKeys.getInt(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateGuardian(Guardian guardian) {
        try {
            // Update users table
            PreparedStatement userStmt = connection.prepareStatement(
                    "UPDATE users SET firstName = ?, lastName = ?, email = ?, phone = ?, password = ? WHERE user_ID = ?"
            );
            userStmt.setString(1, guardian.getFirst_name());
            userStmt.setString(2, guardian.getLast_name());
            userStmt.setString(3, guardian.getEmail());
            userStmt.setString(4, guardian.getPhone());
            userStmt.setString(5, guardian.getPassword());
            userStmt.setInt(6, guardian.getUser_ID());
            userStmt.executeUpdate();

            // Update guardians table
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE guardians SET student_ID = ? WHERE guardian_ID = ?"
            );
            stmt.setInt(1, guardian.getStudent_id());
            stmt.setInt(2, guardian.getGuardian_id());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteGuardian(Guardian guardian) {
        try {
            // Delete from guardians table
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM guardians WHERE guardian_ID = ?");
            stmt.setInt(1, guardian.getGuardian_id());
            stmt.executeUpdate();

            // Delete from users table
            PreparedStatement userStmt = connection.prepareStatement("DELETE FROM users WHERE user_ID = ?");
            userStmt.setInt(1, guardian.getUser_ID());
            userStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Guardian getGuardian(int guardian_ID) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT g.*, u.* FROM guardians g JOIN users u ON g.user_ID = u.user_ID WHERE g.guardian_ID = ?"
            );
            stmt.setInt(1, guardian_ID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Guardian guardian = new Guardian(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getInt("guardian_ID"),
                        rs.getInt("student_ID")
                );
                guardian.setUser_ID(rs.getInt("user_ID"));
                return guardian;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Guardian> getAllGuardians() {
        List<Guardian> guardians = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT g.*, u.* FROM guardians g JOIN users u ON g.user_ID = u.user_ID"
            );
            while (rs.next()) {
                Guardian guardian = new Guardian(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getInt("guardian_ID"),
                        rs.getInt("student_ID")
                );
                guardian.setUser_ID(rs.getInt("user_ID"));
                guardians.add(guardian);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return guardians;
    }



}
