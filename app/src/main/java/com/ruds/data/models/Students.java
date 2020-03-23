package com.ruds.data.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Students {
    private static String name;

    public Students() {

    }

    public Students(String name) {
        this.name = name;
    }

    @Exclude
    public static Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put(name, name + "true");
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
