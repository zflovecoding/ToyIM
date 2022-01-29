package com.geekpig.qqcommon;
//this class represents a user of this system

import java.io.Serializable;

//to be transmitted as a objectOutputStream ,must implements Serializable interface
public class User implements Serializable {
    //to ensure the compatibility,define a serialVersionUID
    private static final long serialVersionUID = 1L;
    private int userID;
    private int passwd;

    public User() {
    }

    public User(int userID, int passwd) {
        this.userID = userID;
        this.passwd = passwd;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getPasswd() {
        return passwd;
    }

    public void setPasswd(int passwd) {
        this.passwd = passwd;
    }
}
