package com.example.administrator.busapp.datamodels;

/**
 * Created by Administrator on 20/04/2017.
 */

public class Duration {
    private String text;
    private int value;

    public Duration(String text, int value) {
        this.setText(text);
        this.setValue(value);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
