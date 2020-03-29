package com.ruds.data.models;

import androidx.lifecycle.ViewModel;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Students extends ViewModel {
    private static String uid, fullName, email, contact, enroll, sem, dep, gender, degree, password;

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        Students.uid = uid;
    }

    public Students(String name) {
        this.fullName = name;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Students.password = password;
    }

    public static String getContact() {
        return contact;
    }

    public static void setContact(String contact) {
        Students.contact = contact;
    }

    public static String getFullName() {
        return fullName;
    }

    public static void setFullName(String fullName) {
        Students.fullName = fullName;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Students.email = email;
    }

    public static String getEnroll() {
        return enroll;
    }

    public static void setEnroll(String enroll) {
        Students.enroll = enroll;
    }

    public static String getSem() {
        return sem;
    }

    public static void setSem(String sem) {
        Students.sem = sem;
    }

    public static String getDep() {
        return dep;
    }

    public static void setDep(String dep) {
        Students.dep = dep;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        Students.gender = gender;
    }

    public static String getDegree() {
        return degree;
    }

    public static void setDegree(String degree) {
        Students.degree = degree;
    }

    public Students() {
    }

    @Exclude
    public static Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("fullName", fullName);
        result.put("degree", degree);
        result.put("dep", dep);
        result.put("sem", sem);
        result.put("gender", gender);
        result.put("email", email);
        result.put("contact", contact);
        result.put("password", password);
        return result;
    }

    public String getName() {
        return fullName;
    }

    public void setName(String name) {
        this.fullName = name;
    }
}
