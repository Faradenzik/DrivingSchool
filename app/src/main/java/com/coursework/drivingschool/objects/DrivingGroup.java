package com.coursework.drivingschool.objects;

public class DrivingGroup {
    private int id;
    private String month;
    private String type;
    private String category;
    private String teacherId;

    public DrivingGroup() {
    }

    public DrivingGroup(int id, String month, String type, String category, String teacherId) {
        this.id = id;
        this.month = month;
        this.type = type;
        this.category = category;
        this.teacherId = teacherId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}

