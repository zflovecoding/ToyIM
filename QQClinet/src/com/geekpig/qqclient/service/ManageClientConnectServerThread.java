package com.geekpig.qqclient.service;

import java.util.HashMap;

public class ManageClientConnectServerThread {
    private static HashMap<String ,ClientConnectServerThread> hm = new HashMap<>();
    public static void addClientConnectServerThread(String userID,ClientConnectServerThread cl){
        hm.put(userID,cl);
    }
    public static ClientConnectServerThread getClientConnectServerThread(String userID){
        return hm.get(userID);
    }

}
