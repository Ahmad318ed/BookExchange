package com.example.bookexchange.models;

public class User {

    String email,name,id;

    String image;


    public User() {
    }

    public User( String id,String email,String name) {

        this.email = email;
        this.name=name;
        this.id=id;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
