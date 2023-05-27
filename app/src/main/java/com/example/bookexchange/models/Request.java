package com.example.bookexchange.models;

import java.io.Serializable;

public class Request implements Serializable {

    String bookSellerName, bookSellerId, bookSellerEmail, bookName
            , bookCollege, bookPrice
            , bookDetails, bookEdition, bookStates;
    String requestDate;
    String requestID;
    String img;

    public Request() {
    }

    public Request(String img, String bookSellerName, String bookSellerEmail, String bookSellerId
            , String bookName, String bookEdition, String bookCollege, String bookPrice, String bookStates, String bookDetails, String requestDate) {
        this.bookName = bookName;
        this.bookCollege = bookCollege;
        this.bookPrice = bookPrice;
        this.bookSellerName = bookSellerName;
        this.img = img;
        this.bookEdition = bookEdition;
        this.bookStates = bookStates;
        this.bookDetails = bookDetails;
        this.bookSellerId = bookSellerId;
        this.bookSellerEmail = bookSellerEmail;
        this.requestDate = requestDate;


    }

    public Request(String bookName, String bookSellerName, String bookCollege, String bookPrice, String takersNumbers) {
        this.bookName = bookName;
        this.bookCollege = bookCollege;
        this.bookPrice = bookPrice;
        this.bookSellerName = bookSellerName;
    }


    public String getBookSellerName() {
        return bookSellerName;
    }

    public void setBookSellerName(String bookSellerName) {
        this.bookSellerName = bookSellerName;
    }

    public String getBookSellerId() {
        return bookSellerId;
    }

    public void setBookSellerId(String bookSellerId) {
        this.bookSellerId = bookSellerId;
    }

    public String getBookSellerEmail() {
        return bookSellerEmail;
    }

    public void setBookSellerEmail(String bookSellerEmail) {
        this.bookSellerEmail = bookSellerEmail;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookCollege() {
        return bookCollege;
    }

    public void setBookCollege(String bookCollege) {
        this.bookCollege = bookCollege;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(String bookDetails) {
        this.bookDetails = bookDetails;
    }

    public String getBookEdition() {
        return bookEdition;
    }

    public void setBookEdition(String bookEdition) {
        this.bookEdition = bookEdition;
    }

    public String getBookStates() {
        return bookStates;
    }

    public void setBookStates(String bookStates) {
        this.bookStates = bookStates;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
