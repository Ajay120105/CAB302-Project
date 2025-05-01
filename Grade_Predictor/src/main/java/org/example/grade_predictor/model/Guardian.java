package org.example.grade_predictor.model;

public class Guardian  extends User{
    private int guardian_id;
    private int student_id;

    public Guardian(String first_name, String last_name, String email, String phone, String password, int guardian_id, int student_id) {
        super(first_name, last_name, email, phone, password);
        this.guardian_id = guardian_id;
        this.student_id = student_id;
    }

    public int getGuardian_id() {
        return guardian_id;
    }

    public void setGuardian_id(int guardian_id) {
        this.guardian_id = guardian_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }
}
