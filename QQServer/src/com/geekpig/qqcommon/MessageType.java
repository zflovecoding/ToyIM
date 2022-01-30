package com.geekpig.qqcommon;

public interface MessageType {
    //define some constant in interface
    //different values of the constants ,represents different type of message
    String  MESSAGE_LOGIN_SUCCEED= "1"; // login succeed
    String  MESSAGE_LOGIN_FAILED= "2";//login failed
    String MESSAGE_COMM_MES = "3"; //common information package
    String MESSAGE_GET_ONLINE_FRIEND = "4"; //get the users list
    String MESSAGE_RET_ONLINE_FRIEND = "5"; //return the users list
    String MESSAGE_CLIENT_EXIT = "6"; //Client asks for exit
    String MESSAGE_TO_ALL_MES = "7"; //group chat message
    String MESSAGE_FILE_MES = "8"; //send file message
}
