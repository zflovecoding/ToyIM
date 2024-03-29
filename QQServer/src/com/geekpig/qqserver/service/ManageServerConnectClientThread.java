package com.geekpig.qqserver.service;

import java.util.HashMap;
import java.util.Iterator;

public class ManageServerConnectClientThread {
    private static  HashMap<String ,ServerConnectClientThread> hm = new HashMap<>();
    public static HashMap<String,ServerConnectClientThread> getAllThread(){
        return hm;
    }
    public static void addServerConnectClientThread(String userID,ServerConnectClientThread scct){
        hm.put(userID,scct);
    }
    public static ServerConnectClientThread getServerConnectClientThread(String userID){
        return hm.get(userID);
    }
    //remove a thread object from hashmap
    public static void  removeServerConnectClientThread(String userID){
        hm.remove(userID);
    }

    //traverse the hashmap,get all online users
    public static String getAllOnlineUsers(){
        String onlineUserList = "";
        //traverse hashmap
        Iterator<String> iterator = hm.keySet().iterator();
        while(iterator.hasNext()){
            onlineUserList += iterator.next().toString() + " ";

        }
        return onlineUserList;
    }



}
