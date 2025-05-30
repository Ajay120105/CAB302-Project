package org.example.grade_predictor.model.SQLiteDAO;
import org.example.grade_predictor.model.Degree;
import org.example.grade_predictor.model.SqliteConnection;
import org.example.grade_predictor.model.interfaces.I_Degree;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqliteDegreeDAO implements I_Degree {

    private Connection connection;

    public SqliteDegreeDAO() {
        connection = SqliteConnection.getInstance();
    }

    @Override
    public void addDegree(Degree degree) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO degrees (id, degreeName) VALUES (?, ?)");
            statement.setString(1, degree.getDegree_ID());
            statement.setString(2, degree.getDegree_Name());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDegree(Degree degree) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE degrees SET degreeName = ? WHERE id = ?");
            statement.setString(1, degree.getDegree_Name());
            statement.setString(2, degree.getDegree_ID());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDegree(Degree degree) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM degrees WHERE id = ?");
            statement.setString(1, degree.getDegree_ID());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Degree getDegree(String degree_ID) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM degrees WHERE id = ?");
            statement.setString(1, degree_ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String degreeName = resultSet.getString("degreeName");
                return new Degree(degreeName, degree_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Degree> getAllDegrees() {
        List<Degree> degrees = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM degrees");
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String degreeName = resultSet.getString("degreeName");
                degrees.add(new Degree(degreeName, id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return degrees;
    }
}
