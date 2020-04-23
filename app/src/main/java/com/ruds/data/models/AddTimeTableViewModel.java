package com.ruds.data.models;

import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

public class AddTimeTableViewModel extends ViewModel {
    String Lecture_1, Lecture_2, Lecture_3, Lecture_4, Lecture_5, Lecture_6;

    public AddTimeTableViewModel(String lecture_1, String lecture_2, String lecture_3, String lecture_4, String lecture_5, String lecture_6) {
        Lecture_1 = lecture_1;
        Lecture_2 = lecture_2;
        Lecture_3 = lecture_3;
        Lecture_4 = lecture_4;
        Lecture_5 = lecture_5;
        Lecture_6 = lecture_6;
    }

    public AddTimeTableViewModel() {
    }

    public String getLecture_1() {
        return Lecture_1;
    }

    public void setLecture_1(String lecture_1) {
        Lecture_1 = lecture_1;
    }

    public String getLecture_2() {
        return Lecture_2;
    }

    public void setLecture_2(String lecture_2) {
        Lecture_2 = lecture_2;
    }

    public String getLecture_3() {
        return Lecture_3;
    }

    public void setLecture_3(String lecture_3) {
        Lecture_3 = lecture_3;
    }

    public String getLecture_4() {
        return Lecture_4;
    }

    public void setLecture_4(String lecture_4) {
        Lecture_4 = lecture_4;
    }

    public String getLecture_5() {
        return Lecture_5;
    }

    public void setLecture_5(String lecture_5) {
        Lecture_5 = lecture_5;
    }

    public String getLecture_6() {
        return Lecture_6;
    }

    public void setLecture_6(String lecture_6) {
        Lecture_6 = lecture_6;
    }
}