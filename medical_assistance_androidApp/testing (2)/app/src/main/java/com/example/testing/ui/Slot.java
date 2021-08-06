package com.example.testing.ui;

public class Slot {

    String timing, id,day, sid, name;
    int no;

    public Slot(String timing, int no, String day, String sid) {
        this.timing = timing;
        this.no = no;
        this.id = "N/A";
        this.day = day;
        this.sid = sid;
        this.name = "N/A";
    }

    public String getSid() {
        return sid;
    }

    public Slot()
    {

    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {this.id = id;}

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
