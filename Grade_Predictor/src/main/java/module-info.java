module org.example.grade_predictor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.grade_predictor to javafx.fxml;
    exports org.example.grade_predictor;
    exports org.example.grade_predictor.controller;
    opens org.example.grade_predictor.controller to javafx.fxml;
    exports org.example.grade_predictor.model;
    opens org.example.grade_predictor.model to javafx.fxml;
}