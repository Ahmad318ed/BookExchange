package com.example.bookexchange.models;

public class User {

    String email,name,id;


    public User() {
    }

    public User( String id,String email,String name) {

        this.email = email;
        this.name=name;
        this.id=id;

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
