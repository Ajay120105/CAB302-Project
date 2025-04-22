package org.example.grade_predictor.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.example.grade_predictor.HelloApplication;

import java.io.IOException;

public class EditUnitController {

    @FXML
    private void handleButton1(ActionEvent event) {
        System.out.println("Button 1 clicked");
    }

    @FXML
    private void handleButton2(ActionEvent event) {
        System.out.println("Button 2 clicked");
    }

    @FXML
    private void handleButton3(ActionEvent event) {
        System.out.println("Button 3 clicked");
    }

    @FXML
    private void handleButton4(ActionEvent event) {
        System.out.println("Button 4 clicked");
    }

    @FXML
    private void goToHomePage(ActionEvent event) {
        try {
            HelloApplication.switchToHomePage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}