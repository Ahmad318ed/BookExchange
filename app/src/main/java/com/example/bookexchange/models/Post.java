package com.example.bookexchange.models;

import java.io.Serializable;

public class Post implements Serializable {

    String bookName,bookSeller,bookCollege,bookPrice,person_num,bookEdition,states,details,bookSellerId,bookSellerEmail,postDate;
    String img;

    public Post() {
    }
    public Post(String img,String bookName, String bookSeller,String bookSellerEmail,String bookSellerId,String bookCollege,String bookPrice,String person_num,String bookEdition,String states,String details,String postDate) {
        this.bookName = bookName;
        this.person_num = person_num;
        this.bookCollege=bookCollege;
        this.bookPrice=bookPrice;
        this.bookSeller=bookSeller;
        this.img=img;
        this.bookEdition=bookEdition;
        this.states=states;
        this.details=details;
        this.bookSellerId=bookSellerId;
        this.bookSellerEmail=bookSellerEmail;
        this.postDate=postDate;
    }
    public Post(String bookName, String bookSeller,String bookCollege,String bookPrice,String person_num) {
        this.bookName = bookName;
        this.person_num = person_num;

        this.bookCollege=bookCollege;
        this.bookPrice=bookPrice;
        this.bookSeller=bookSeller;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getBookEdition() {
        return bookEdition;
    }

    public void setBookEdition(String bookEdition) {
        this.bookEdition = bookEdition;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getBookSellerId() {
        return bookSellerId;
    }

    public void setBookSellerId(String bookSellerId) {
        this.bookSellerId = bookSellerId;
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

    public String getBookSeller() {
        return bookSeller;
    }

    public void setBookSeller(String bookSeller) {
        this.bookSeller = bookSeller;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPerson_num() {
        return person_num;
    }

    public void setPerson_num(String person_num) {
        this.person_num = person_num;
    }

    public String getBookSellerEmail() {
        return bookSellerEmail;
    }

    public void setBookSellerEmail(String bookSellerEmail) {
        this.bookSellerEmail = bookSellerEmail;
    }
}
