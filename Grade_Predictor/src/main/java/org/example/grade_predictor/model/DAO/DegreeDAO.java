package org.example.grade_predictor.model.DAO;

import org.example.grade_predictor.model.Degree;
import org.example.grade_predictor.model.Unit;
import org.example.grade_predictor.model.interfaces.I_Degree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DegreeDAO implements I_Degree {
    public static final ArrayList<Degree> degrees = new ArrayList<>();

    @Override
    public void addDegree(Degree degree) {
        String code = degree.getDegree_ID();
        // Check if it matches the format: 2 uppercase letters + 2 digits
        if (!code.matches("^[A-Z]{2}\\d{2}$")) {
            throw new IllegalArgumentException("Invalid unit code format. Must be 2 uppercase letters followed by 2 digits (e.g. IX22).");
        }
        // Now insert into database
        degrees.add(degree);
    }

    @Override
    public void updateDegree(Degree degree) {
        for (int i = 0; i < degrees.size(); i++) {
            if (Objects.equals(degrees.get(i).getDegree_ID(), degree.getDegree_ID())) {
                degrees.set(i, degree);
                break;
            }
        }
    }

    @Override
    public void deleteDegree(Degree degree) {
        degrees.remove(degree);
    }

    @Override
    public Degree getDegree(String degree_ID) {
        for (Degree degree : degrees) {
            if (Objects.equals(degree.getDegree_ID(), degree_ID)) {
                return degree;
            }
        }
        return null;
    }

    @Override
    public List<Degree> getAllDegrees() {
        return new ArrayList<>(degrees);
    }

}
