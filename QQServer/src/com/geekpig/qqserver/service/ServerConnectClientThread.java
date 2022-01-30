package com.geekpig.qqserver.service;

import com.geekpig.qqcommon.Message;
import com.geekpig.qqcommon.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnectClientThread extends Thread{
    private Socket socket = null;
    private String userID;//the user who connects to the server
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
               ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
               Message msg =(Message) ois.readObject();
               //The message will be used later,
               // and the corresponding business processing
               // will be done according to the type of the message

               //Client pulls the online user list
               if(msg.getMsgType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)){
                   System.out.println(msg.getSender() + " 要在线用户列表");
                   //Server should write back message contains online user list
                   //traverse the HashMap which stores all the thread contains sockets
                   String allOnlineUsers = ManageServerConnectClientThread.getAllOnlineUsers();
                   //after we get the list ,wo need a message to transmit it back
                   Message message = new Message();
                   message.setMsgType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                   message.setGetter(msg.getSender());
                   message.setContent(allOnlineUsers);
                   //transmit message back
                   oos.writeObject(message);
               }



           } catch (Exception e) {
               e.printStackTrace();
           }

       }
    }


}
