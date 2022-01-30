package com.geekpig.qqserver.service;

import com.geekpig.qqcommon.Message;
import com.geekpig.qqcommon.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

public class ServerConnectClientThread extends Thread{
    private Socket socket = null;
    private String userID;//the user who connects to the server
    public ServerConnectClientThread(Socket socket,String userID){
       this.socket = socket;
       this.userID = userID;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run(){
       while(true){//here,this thread is in the run status,used to receive/send message
           try {
               System.out.println("服务端和客户端" + userID + " 保持通信，读取数据...");
               //new ObjectOutputStream(socket.getOutputStream())
               ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             //  ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
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
                   ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                   //transmit message back
                   oos.writeObject(message);
               }else if(msg.getMsgType().equals(MessageType.MESSAGE_COMM_MES)){
                   //what should i do here , transmit this message to the specified client
                   //use the corresponding socket
                   //write to the dest client
                   ServerConnectClientThread serverConnectClientThread = ManageServerConnectClientThread.getServerConnectClientThread(msg.getGetter());
                   ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                   oos.writeObject(msg);

               }else if(msg.getMsgType().equals(MessageType.MESSAGE_TO_ALL_MES)){

                   //sent the message to all the socket except itself
                   HashMap<String, ServerConnectClientThread> allThread = ManageServerConnectClientThread.getAllThread();
                   Iterator<String> iterator = allThread.keySet().iterator();
                   while(iterator.hasNext()){
                       String onlineUsers = iterator.next().toString();
                       if(!onlineUsers.equals(msg.getSender())){
                           ObjectOutputStream oos =new ObjectOutputStream(allThread.get(onlineUsers).getSocket().getOutputStream());
                           oos.writeObject(msg);
                       }
                   }



               }
               else if(msg.getMsgType().equals(MessageType.MESSAGE_CLIENT_EXIT)){
                   System.out.println(msg.getSender() + " 退出");
                   //client send a message to close socket ,and terminate the thread(drop the loop)
                   //find the socket corresponding the userID
                   String userID  = msg.getSender();
                   //remove the thread from the collection
                   ManageServerConnectClientThread.removeServerConnectClientThread(userID);
                   //shutdown the socket , there exists many "ServerConnectClientThread",many "socket"
                   //here, the socket is just in this thread ,in this communication
                   socket.close();
                   break;//jump out the loop ,kill the thread
               }



           } catch (Exception e) {
               e.printStackTrace();
           }

       }
    }


}
