package com.example.bookexchange.models;

public class Request {

    String bookName,bookSeller,bookCollege,bookPrice,person_num;
    int img;

    public Request() {
    }

    public Request(String bookName, String bookSeller,int img,String bookCollege,String bookPrice,String person_num) {
        this.bookName = bookName;
        this.person_num = person_num;
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

    public String getPerson_num() {
        return person_num;
    }

    public void setPerson_num(String person_num) {
        this.person_num = person_num;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
