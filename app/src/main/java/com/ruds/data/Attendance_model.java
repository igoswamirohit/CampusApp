package com.ruds.data;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Attendance_model {
    String dep, lec, sem, ajkidate;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;

    public Attendance_model() {

    }

    public Attendance_model(String dep, String lec, String sem, String ajkidate) {
        this.dep = dep;
        this.lec = lec;
        this.sem = sem;
        this.ajkidate = ajkidate;
    }

    public String getDate() {
        return ajkidate;
    }

    public void setDate(String date) {
        this.ajkidate = date;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Department", "false");
        result.put("Lecture", "fe");
        result.put("Semester", "false");
        result.put("date", "false");

        return result;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getLec() {
        return lec;
    }

    public void setLec(String lec) {
        this.lec = lec;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }
}
