package org.example.grade_predictor.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.grade_predictor.model.SQLiteDAO.*;
import org.example.grade_predictor.model.User;
import java.util.List;


public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("JavaFX");
        //Used For Testing
        SqliteUserDAO userDAO = new SqliteUserDAO();
        SqliteStudentDAO studentDAO = new SqliteStudentDAO();
        SqliteUnitDAO unitDAO = new SqliteUnitDAO();
        SqliteDegreeDAO DegreeDAO = new SqliteDegreeDAO();
        SqliteGuardianDAO GuardianDAO = new SqliteGuardianDAO();

        List<User> users = userDAO.getAllUsers();
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < users.size(); i++) {
            out.append(users.get(i).getFirst_name()).append("\n"); // assuming User has getUsername()
        }
        System.out.println(out);



    }
}