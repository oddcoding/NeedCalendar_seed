package com.example.needcalendar;

import java.time.LocalDate;

public class Schedule {

    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String startTime;
    private String endTime;


    public Schedule(String title, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public String getTitle() {
        return title;
    }


    public LocalDate getStartDate() {
        return startDate;
    }


    public LocalDate getEndDate() {
        return endDate;
    }


    public String getStartTime() {
        return startTime;
    }


    public String getEndTime() {
        return endTime;
    }
}
