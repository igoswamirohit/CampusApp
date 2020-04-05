package com.ruds.data.models;

import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AttendanceStd extends ViewModel {
    String Rohit, Name, Lecture2, Lecture3, Lecture4, Lecture5, Lecture6;
    //HashMap<String,Object> Lecture1;

    /*public HashMap<String, Object> getLecture1() {
        return Lecture1;
    }

    public void setLecture1(HashMap<String, Object> lecture1) {
        Lecture1 = lecture1;
    }*/

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
