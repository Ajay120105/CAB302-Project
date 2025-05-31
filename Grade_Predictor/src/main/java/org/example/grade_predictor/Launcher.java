package org.example.grade_predictor;

public class Launcher {
    public static void main(String[] args) {
        System.setProperty("javafx.verbose", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "true");
        
        HelloApplication.main(args);
    }
} 