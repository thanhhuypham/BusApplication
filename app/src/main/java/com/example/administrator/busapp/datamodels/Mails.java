package com.example.administrator.busapp.datamodels;

/**
 * Created by Administrator on 19/04/2017.
 */

public class Mails {
    private String id;
    private String recipident;
    private String subject;
    private String content;
    private String time;

    public String getRecipident() {
        return recipident;
    }

    public void setRecipident(String recipident) {
        this.recipident = recipident;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Mails(String id, String recipident, String subject, String content, String time) {
        this.id = id;
        this.recipident = recipident;
        this.subject = subject;
        this.content = content;
        this.time = time;
    }

    public Mails() {

    }



}
