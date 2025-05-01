package org.example.grade_predictor.model.DAO;

import org.example.grade_predictor.model.Guardian;
import org.example.grade_predictor.model.interfaces.I_Guardian;

import java.util.ArrayList;
import java.util.List;

public class GuardianDAO implements I_Guardian {
    /**
     * A static list of guardians to be used as a mock database.
     */
    public static final ArrayList<Guardian> guardians = new ArrayList<>();
    private static int autoIncrementedId = 0;

    @Override
    public void addGuardian(Guardian guardian) {
        guardian.setGuardian_id(autoIncrementedId);
        autoIncrementedId++;
        guardians.add(guardian);
    }

    @Override
    public void updateGuardian(Guardian guardian) {
        for (int i = 0; i < guardians.size(); i++) {
            if (guardians.get(i).getGuardian_id() == guardian.getGuardian_id()) {
                guardians.set(i, guardian);
                break;
            }
        }
    }

    @Override
    public void deleteGuardian(Guardian guardian) {
        guardians.remove(guardian);
    }

    @Override
    public Guardian getGuardian(int guardian_id) {
        for (Guardian guardian : guardians) {
            if (guardian.getGuardian_id() == guardian_id) {
                return guardian;
            }
        }
        return null;
    }

    @Override
    public List<Guardian> getAllGuardians() {
        return new ArrayList<>(guardians);
    }
}
