package com.jay_adhikariyahoo.homeapp;

/**
 * Created by jay on 25/04/18.
 */

public class deviceClass {
    private String name;
    private String status;
    private String type;


    public deviceClass(String name, String status, String type) {
        this.name = name;
        this.status = status;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
