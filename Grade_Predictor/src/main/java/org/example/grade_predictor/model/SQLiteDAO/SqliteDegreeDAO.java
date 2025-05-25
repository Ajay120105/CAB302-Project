package org.example.grade_predictor.model.SQLiteDAO;
import org.example.grade_predictor.model.Degree;
import org.example.grade_predictor.model.SqliteConnection;
import org.example.grade_predictor.model.interfaces.I_Degree;
import org.example.grade_predictor.model.SQLiteDAO.SqliteUnitDAO;

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
        createTable();
        //populateDegreesTable();
    }

    private void deleteTable(){
        try {
            Statement statement = connection.createStatement();
            String query = "DROP TABLE degrees";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateDegreesTable() {
        String insertQuery = "INSERT INTO degrees (id, degreeName) VALUES (?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(insertQuery);

            String[][] degrees = {
                    {"BS06", "Bachelor of Business"},
                    {"BS08", "Bachelor of Business - International"},
                    {"BZ05", "Bachelor of Business"},
                    {"CA01", "Bachelor of Creative Arts"},
                    {"CA02", "Bachelor of Creative Arts (Acting)"},
                    {"CS43", "Bachelor of Paramedic Science"},
                    {"CS44", "Bachelor of Podiatry"},
                    {"CS49", "Bachelor of Radiation Therapy"},
                    {"DE43", "Bachelor of Design"},
                    {"DE44", "Bachelor of Design Studies"},
                    {"DE45", "Bachelor of Design - International"},
                    {"DS01", "Bachelor of Data Science"},
                    {"ED29", "Bachelor of Educational Studies"},
                    {"ED34", "Bachelor of Early Childhood Education (Birth to Five)"},
                    {"ED39", "Bachelor of Education (Early Childhood)"},
                    {"ED49", "Bachelor of Education (Primary)"},
                    {"ED59", "Bachelor of Education (Secondary)"},
                    {"EN29", "Bachelor of Engineering Studies"},
                    {"HL25", "Bachelor of Health Studies"},
                    {"IN01", "Bachelor of Information Technology"},
                    {"IN05", "Bachelor of Games and Interactive Environments"},
                    {"LW38", "Bachelor of Laws (Honours)"},
                    {"NS42", "Bachelor of Nursing"},
                    {"ST01", "Bachelor of Science"},
                    {"IF80", "Master of Philosophy"},
                    {"IF49", "Doctor of Philosophy"},
                    {"ED11", "Doctor of Education"}
            };

            for (String[] degree : degrees) {
                pstmt.setString(1, degree[0]); // id
                pstmt.setString(2, degree[1]); // degreeName
                pstmt.executeUpdate();
            }

            System.out.println("Degrees table populated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS degrees (" +
                    "id VARCHAR PRIMARY KEY," +
                    "degreeName VARCHAR NOT NULL" +
                    ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
