package com.example.bookexchange.models;

public class Request {

    String bookName,bookEdition,bookCollege,bookPrice,bookSeller;
    int img;

    public Request() {
    }

    public Request(String bookName, String bookEdition,int img,String bookCollege,String bookPrice,String bookSeller) {
        this.bookName = bookName;
        this.bookEdition = bookEdition;
        this.img = img;
        this.bookCollege=bookCollege;
        this.bookPrice=bookPrice;
        this.bookSeller=bookSeller;

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

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookEdition() {
        return bookEdition;
    }

    public void setBookEdition(String bookEdition) {
        this.bookEdition = bookEdition;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
