package com.geekpig.qqserver.service;

import com.geekpig.qqcommon.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnectClientThread extends Thread{
    private Socket socket = null;
    private String userID;//the user who connect to server
    public ServerConnectClientThread(Socket socket,String userID){
       this.socket = socket;
       this.userID = userID;
    }
    @Override
    public void run(){
       while(true){//here,this thread is in the run status,used to receive/send message
           try {
               System.out.println("服务端和客户端" + userID + " 保持通信，读取数据...");
               //new ObjectOutputStream(socket.getOutputStream())
               ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
               Message msg =(Message) ois.readObject();
               //The message will be used later,
               // and the corresponding business processing
               // will be done according to the type of the message



           } catch (Exception e) {
               e.printStackTrace();
           }

       }
    }


}
