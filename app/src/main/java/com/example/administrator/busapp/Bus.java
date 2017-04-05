package com.example.administrator.busapp;

/**
 * Created by Administrator on 29/03/2017.
 */

public class Bus {
    private String id;
    private String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bus() {

    }

    public Bus(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
