package com.example.needcalendar;

public class list {

    private String title;
    private String place;
    private String memo;


    public list(String title, String place, String memo) {
        this.title = title;
        this.place = place;
        this.memo = memo;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


}