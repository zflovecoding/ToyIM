package com.geekpig.qqserver.service;

import java.util.HashMap;

public class ManageServerConnectClientThread {
    private static  HashMap<String ,ServerConnectClientThread> hm = new HashMap<>();

    public static void addServerConnectClientThread(String userID,ServerConnectClientThread scct){
        hm.put(userID,scct);
    }
    public static ServerConnectClientThread getServerConnectClientThread(String userID){
        return hm.get(userID);
    }

}
