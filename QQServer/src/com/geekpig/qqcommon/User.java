package com.geekpig.qqcommon;
//this class represents a user of this system

import java.io.Serializable;

//to be transmitted as a objectOutputStream ,must implements Serializable interface
public class User implements Serializable {
    //to ensure the compatibility,define a serialVersionUID
    private static final long serialVersionUID = 1L;
    private String userID;
    private String passwd;

    public User() {
    }

    public User(String userID, String passwd) {
        this.userID = userID;
        this.passwd = passwd;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
