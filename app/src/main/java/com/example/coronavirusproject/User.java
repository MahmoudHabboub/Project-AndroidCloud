package com.example.coronavirusproject;

import java.io.Serializable;

public class User implements Serializable {
    String Email;
    String Uid;

    public User(String email, String uid) {
        Email = email;
        Uid = uid;
    }

    public User() {
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
