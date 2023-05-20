package com.example.bookexchange.models;

public class ReceivedNotification {

    String notificationID, userSenderID, owner_userID, owner_Username, bookName, states,date;


    public ReceivedNotification() {
    }

    public ReceivedNotification(String userSenderID, String owner_userID, String owner_Username, String bookName, String states,String date) {
        this.owner_userID = owner_userID;
        this.bookName = bookName;
        this.states = states;
        this.userSenderID = userSenderID;
        this.owner_Username = owner_Username;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOwner_Username() {
        return owner_Username;
    }

    public void setOwner_Username(String owner_Username) {
        this.owner_Username = owner_Username;
    }

    public String getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
    }

    public String getUserSenderID() {
        return userSenderID;
    }

    public void setUserSenderID(String userSenderID) {
        this.userSenderID = userSenderID;
    }

    public String getUserID() {
        return owner_userID;
    }

    public void setUserID(String userID) {
        this.owner_userID = userID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }
}
