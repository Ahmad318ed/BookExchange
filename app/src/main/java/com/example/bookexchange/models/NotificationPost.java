package com.example.bookexchange.models;


public class NotificationPost {

    public String userID,userName;
    public String notificationDate,notificationID;

    public String bookName,postID;

    public NotificationPost() {
    }

    public NotificationPost(String userID, String userName, String bookName, String notificationDate) {
        this.userID = userID;
        this.userName = userName;
        this.bookName = bookName;
        this.notificationDate=notificationDate;


    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
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

