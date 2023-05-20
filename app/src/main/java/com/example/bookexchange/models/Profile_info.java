package com.example.bookexchange.models;

public class Profile_info {
    String name, collage, facebook_link, instagram_link;
    String img;
    String phoneNum;
    String countryNum;
    String whatsAppNum;
    String username, userId;
    String major;
    int SelectedItem;

    public Profile_info() {
    }

    public Profile_info(String name, String collage, String facebook_link, String instagram_link, String phoneNum, String countryNum, String whatsAppNum, String username, String userId, String major, int selectedItem) {
        this.name = name;
        this.collage = collage;
        this.facebook_link = facebook_link;
        this.instagram_link = instagram_link;
        this.phoneNum = phoneNum;
        this.countryNum = countryNum;
        this.whatsAppNum = whatsAppNum;
        this.username = username;
        this.userId = userId;
        this.major = major;
        SelectedItem = selectedItem;
    }

    public Profile_info(String name, String collage, String facebook_link, String instagram_link, String img, String phoneNum, String countryNum, String whatsAppNum, String username, String userId, String major, int selectedItem) {
        this.name = name;
        this.collage = collage;
        this.facebook_link = facebook_link;
        this.instagram_link = instagram_link;
        this.img = img;
        this.phoneNum = phoneNum;
        this.countryNum = countryNum;
        this.whatsAppNum = whatsAppNum;
        this.username = username;
        this.userId = userId;
        this.major = major;
        SelectedItem = selectedItem;
    }

//    public Profile_info(String name, String collage, String facebook_link, String instagram_link, String phoneNum, String countryNum, String whatsAppNum, String username, String userId, String major, int selectedItem) {
//        this.name = name;
//        this.collage = collage;
//        this.facebook_link = facebook_link;
//        this.instagram_link = instagram_link;
//        this.phoneNum = phoneNum;
//        this.countryNum = countryNum;
//        this.whatsAppNum = whatsAppNum;
//        this.username = username;
//        this.userId = userId;
//        this.major = major;
//        SelectedItem = selectedItem;
//    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollage() {
        return collage;
    }

    public void setCollage(String collage) {
        this.collage = collage;
    }

    public String getFacebook_link() {
        return facebook_link;
    }

    public void setFacebook_link(String facebook_link) {
        this.facebook_link = facebook_link;
    }

    public String getInstagram_link() {
        return instagram_link;
    }

    public void setInstagram_link(String instagram_link) {
        this.instagram_link = instagram_link;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCountryNum() {
        return countryNum;
    }

    public void setCountryNum(String countryNum) {
        this.countryNum = countryNum;
    }

    public int getSelectedItem() {
        return SelectedItem;
    }

    public void setSelectedItem(int selectedItem) {
        SelectedItem = selectedItem;
    }

    public String getWhatsAppNum() {
        return whatsAppNum;
    }

    public void setWhatsAppNum(String whatsAppNum) {
        this.whatsAppNum = whatsAppNum;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
