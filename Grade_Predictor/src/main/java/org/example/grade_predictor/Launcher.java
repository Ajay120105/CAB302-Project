package org.example.grade_predictor;

public class Launcher {
    /**
     * Main method to launch the application
     * Currently required for the double click runnable JAR compiled from autobuild
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("javafx.verbose", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "true");
        
        HelloApplication.main(args);
    }
} 