package com.coursework.drivingschool.objects;

public class Lesson {

    private String date;
    private String time;
    private String teacherId;
    private String coursantId;

    public Lesson() {

    }

    public Lesson(String date, String time, String teacherId, String coursantId) {
        this.date = date;
        this.time = time;
        this.teacherId = teacherId;
        this.coursantId = coursantId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getCoursantId() {
        return coursantId;
    }

    public void setCoursantId(String coursantId) {
        this.coursantId = coursantId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
