package com.coursework.drivingschool.roles;

public class Coursant {
    private String Uid;
    private String surname;
    private String name;
    private String patronomyc;
    private String phone;
    private String birthday;
    private String email;
    private String url_im;
    private String grades;
    private int groupId;

    public Coursant() {
    }

    public Coursant(String Uid, String surname, String name, String patronomyc, String phone, String birthday, String email, String grades, int groupId) {
        this.Uid = Uid;
        this.surname = surname;
        this.name = name;
        this.patronomyc = patronomyc;
        this.phone = phone;
        this.birthday = birthday;
        this.email = email;
        this.grades = grades;
        this.groupId = groupId;
    }

    public Coursant(String Uid, String surname, String name, String patronomyc, String phone, String birthday, String email, String url_im, String grades, int groupId) {
        this.Uid = Uid;
        this.surname = surname;
        this.name = name;
        this.patronomyc = patronomyc;
        this.phone = phone;
        this.birthday = birthday;
        this.email = email;
        this.url_im = url_im;
        this.grades = grades;
        this.groupId = groupId;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronomyc() {
        return patronomyc;
    }

    public void setPatronomyc(String patronomyc) {
        this.patronomyc = patronomyc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl_im() {
        return url_im;
    }

    public void setUrl_im(String url_im) {
        this.url_im = url_im;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
