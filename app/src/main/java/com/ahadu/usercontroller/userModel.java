package com.ahadu.usercontroller;

public class userModel {
    String fullName,content,Date;
    int userPhoto;
    public userModel(){

    }

    public userModel(String fullName, String content, String date, int userPhoto) {
        this.fullName = fullName;
        this.content = content;
        Date = date;
        this.userPhoto = userPhoto;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setUserPhoto(int userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return Date;
    }

    public int getUserPhoto() {
        return userPhoto;
    }
}
