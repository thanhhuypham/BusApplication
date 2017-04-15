package com.example.administrator.busapp;

/**
 * Created by Administrator on 29/03/2017.
 */

public class Bus {
    private String id;
    private String name;
    private String start;
    private String end;

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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Bus() {

    }

    public Bus(String id, String name, String start, String end) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
    }
}
