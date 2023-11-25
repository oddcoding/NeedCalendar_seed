package com.example.needcalendar;

import java.time.LocalDate;

public class Schedule {

    private String title;         // 일정 제목
    private LocalDate startDate;  // 시작 날짜
    private LocalDate endDate;    // 종료 날짜
    private String startTime;     // 시작 시간 (옵션)
    private String endTime;       // 종료 시간 (옵션)

    // 생성자
    public Schedule(String title, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // 시작 시간 설정 메서드
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    // 종료 시간 설정 메서드
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    // 일정 제목 가져오기
    public String getTitle() {
        return title;
    }

    // 시작 날짜 가져오기
    public LocalDate getStartDate() {
        return startDate;
    }

    // 종료 날짜 가져오기
    public LocalDate getEndDate() {
        return endDate;
    }

    // 시작 시간 가져오기
    public String getStartTime() {
        return startTime;
    }

    // 종료 시간 가져오기
    public String getEndTime() {
        return endTime;
    }
}
