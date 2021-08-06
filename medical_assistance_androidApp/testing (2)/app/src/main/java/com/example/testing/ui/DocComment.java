package com.example.testing.ui;

class DocComment {
    String text;
    String date;
    String pid;
//Time time;

    public DocComment(String text, String date, String pid) {
        this.text = text;
        this.date = date;
        this.pid = pid;
        //this.time = time;
    }

    public DocComment()
    {

    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /*public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }*/


}
