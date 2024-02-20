package com.coursework.drivingschool.roles;

public class Teacher {
    private String Uid;
    private String surname;
    private String name;
    private String patronomyc;
    private String phone;
    private String email;
    private String url_im;
    private int groupId;

    public Teacher() {
    }

    public Teacher(String Uid, String name, String surname, String patronomyc, String phone, String email, String url_im, int groupId) {
        this.Uid = Uid;
        this.name = name;
        this.surname = surname;
        this.patronomyc = patronomyc;
        this.phone = phone;
        this.email = email;
        this.url_im = url_im;
        this.groupId = groupId;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
