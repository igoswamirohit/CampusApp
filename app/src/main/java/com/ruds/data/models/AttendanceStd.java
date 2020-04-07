package com.ruds.data.models;

import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AttendanceStd extends ViewModel {
    String Rohit, Name;
    Map<String, String> Lecture1, Lecture2, Lecture3, Lecture4, Lecture5, Lecture6 = new HashMap<>();

    public Map<String, String> getLecture1() {
        return Lecture1;
    }

    public void setLecture1(Map<String, String> lecture1) {
        Lecture1 = lecture1;
    }

    public Map<String, String> getLecture2() {
        return Lecture2;
    }

    public void setLecture2(Map<String, String> lecture2) {
        Lecture2 = lecture2;
    }

    public Map<String, String> getLecture3() {
        return Lecture3;
    }

    public void setLecture3(Map<String, String> lecture3) {
        Lecture3 = lecture3;
    }

    public Map<String, String> getLecture4() {
        return Lecture4;
    }

    public void setLecture4(Map<String, String> lecture4) {
        Lecture4 = lecture4;
    }

    public Map<String, String> getLecture5() {
        return Lecture5;
    }

    public void setLecture5(Map<String, String> lecture5) {
        Lecture5 = lecture5;
    }

    public Map<String, String> getLecture6() {
        return Lecture6;
    }

    public void setLecture6(Map<String, String> lecture6) {
        Lecture6 = lecture6;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getRohit() {
        return Rohit;
    }

    public void setRohit(String rohit) {
        Rohit = rohit;
    }
}
