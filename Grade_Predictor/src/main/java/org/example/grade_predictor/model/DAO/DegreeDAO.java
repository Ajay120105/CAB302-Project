package org.example.grade_predictor.model.DAO;

import org.example.grade_predictor.model.Degree;
import org.example.grade_predictor.model.interfaces.I_Degree;

import java.util.ArrayList;
import java.util.List;

public class DegreeDAO implements I_Degree {
    public static final ArrayList<Degree> degrees = new ArrayList<>();
    private static int autoIncrementedId = 0;

    @Override
    public void addDegree(Degree degree) {
        degree.setDegree_ID(autoIncrementedId);
        autoIncrementedId++;
        degrees.add(degree);
    }

    @Override
    public void updateDegree(Degree degree) {
        for (int i = 0; i < degrees.size(); i++) {
            if (degrees.get(i).getDegree_ID() == degree.getDegree_ID()) {
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
    public Degree getDegree(int degree_ID) {
        for (Degree degree : degrees) {
            if (degree.getDegree_ID() == degree_ID) {
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
