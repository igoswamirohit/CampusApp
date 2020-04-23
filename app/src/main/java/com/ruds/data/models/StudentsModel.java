package com.ruds.data.models;

import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

public class StudentsModel extends ViewModel {
    public static String uid, name, email, contact, enroll, sem, dep, gender, degree, password;

    public StudentsModel(String uid, String name, String email, String contact, String enroll, String sem, String dep, String gender, String degree, String password) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.enroll = enroll;
        this.sem = sem;
        this.dep = dep;
        this.gender = gender;
        this.degree = degree;
        this.password = password;
    }

    public StudentsModel(String name) {
        this.name = name;
    }

    public StudentsModel() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEnroll() {
        return enroll;
    }

    public void setEnroll(String enroll) {
        this.enroll = enroll;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("degree", degree);
        result.put("dep", dep);
        result.put("sem", sem);
        result.put("gender", gender);
        result.put("email", email);
        result.put("contact", contact);
        result.put("password", password);
        return result;
    }

}
