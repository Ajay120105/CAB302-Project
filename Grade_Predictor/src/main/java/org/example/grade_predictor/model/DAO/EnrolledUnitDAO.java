package org.example.grade_predictor.model.DAO;

import org.example.grade_predictor.model.EnrolledUnit;
import org.example.grade_predictor.model.interfaces.I_EnrolledUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            if (existing.getEnrollment_ID() == unit.getEnrollment_ID() &&
                    existing.getUnit_code().equals(unit.getUnit_code())) {
                enrolledUnits.set(i, unit);
                return;
            }
        }
    }

    @Override
    public void deleteEnrolledUnit(int enrollment_ID, String unit_code) {
        enrolledUnits.removeIf(unit ->
                unit.getEnrollment_ID() == enrollment_ID &&
                        unit.getUnit_code().equals(unit_code));
    }

    @Override
    public EnrolledUnit getEnrolledUnit(int enrollment_ID, String unit_code) {
        for (EnrolledUnit unit : enrolledUnits) {
            if (unit.getEnrollment_ID() == enrollment_ID &&
                    unit.getUnit_code().equals(unit_code)) {
                return unit;
            }
        }
        return null;
    }

    @Override
    public List<EnrolledUnit> getEnrolledUnitsForEnrollment(int enrollment_ID) {
        return enrolledUnits.stream()
                .filter(unit -> unit.getEnrollment_ID() == enrollment_ID)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnrolledUnit> getAllEnrolledUnits() {
        return new ArrayList<>(enrolledUnits);
    }

}
