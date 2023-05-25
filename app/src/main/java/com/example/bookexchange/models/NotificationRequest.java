package com.example.bookexchange.models;


public class NotificationRequest {

    public String userID,userName;
    public String notificationDate,notificationID;

    public String bookName;

    public NotificationRequest() {
    }

    public NotificationRequest(String userID, String userName, String bookName, String notificationDate) {
        this.userID = userID;
        this.userName = userName;
        this.bookName = bookName;
        this.notificationDate=notificationDate;


    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}

