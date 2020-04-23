package com.ruds.data.models;

import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AttendanceStd extends ViewModel {
    String Name, startDate, endDate, attendancePercentage;

    public String getAttendancePercentage() {
        return attendancePercentage;
    }

    public void setAttendancePercentage(String attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
