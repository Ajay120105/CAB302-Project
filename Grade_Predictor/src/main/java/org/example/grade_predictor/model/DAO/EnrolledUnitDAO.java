package org.example.grade_predictor.model.DAO;

import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.interfaces.I_EnrolledUnit;

import java.util.ArrayList;
import java.util.List;

public class EnrolledUnitDAO implements I_EnrolledUnit {
    private static final List<EnrolledUnit> enrolledUnits = new ArrayList<>();

    @Override
    public void addEnrolledUnit(EnrolledUnit unit) {
        enrolledUnits.add(unit);
    }

    @Override
    public void updateEnrolledUnit(EnrolledUnit unit) {
        for (int i = 0; i < enrolledUnits.size(); i++) {
            EnrolledUnit existing = enrolledUnits.get(i);
            if (existing.getStudent_ID() == unit.getStudent_ID() &&
                    existing.getUnit_code().equals(unit.getUnit_code())) {
                enrolledUnits.set(i, unit);
                return;
            }
        }
    }

    @Override
    public void deleteEnrolledUnit(int student_ID, String unit_code) {
        enrolledUnits.removeIf(unit ->
                unit.getStudent_ID() == student_ID &&
                        unit.getUnit_code().equals(unit_code));
    }

    @Override
    public EnrolledUnit getEnrolledUnit(int student_ID, String unit_code) {
        for (EnrolledUnit unit : enrolledUnits) {
            if (unit.getStudent_ID() == student_ID &&
                    unit.getUnit_code().equals(unit_code)) {
                return unit;
            }
        }
        return null;
    }

}
