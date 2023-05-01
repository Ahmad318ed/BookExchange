package com.example.bookexchange.models;

public class Request {

    String bookName,bookSeller,bookCollege,bookPrice;
    int img;

    public Request() {
    }

    public Request(String bookName, String bookSeller,int img,String bookCollege,String bookPrice) {
        this.bookName = bookName;
        this.img=img;
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



    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
