module org.example.grade_predictor {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.grade_predictor to javafx.fxml;
    exports org.example.grade_predictor;
}