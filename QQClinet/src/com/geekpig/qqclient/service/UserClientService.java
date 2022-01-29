package com.geekpig.qqclient.service;


import com.geekpig.qqcommon.Message;
import com.geekpig.qqcommon.MessageType;
import com.geekpig.qqcommon.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
//this class used to handle user login and sign up
public class UserClientService {
    //Because we may use user information in other places, so make member properties
     private User u = new User();
     private Socket socket;
    //checkUser() will verify user in the server

     public boolean checkUser(String userID,String pwd){
         boolean b = false;
         u.setUserID(userID);
         u.setPasswd(pwd);
         //User user = new User(userID, pwd);
         //user will be transmitted to QQServer
         try {
             //write user to server
             socket = new Socket(InetAddress.getByName("127.0.0.1"),9999);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             oos.writeObject(u);

             //read message from server

             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             Message msg = (Message) ois.readObject();

             if(msg.getMsgType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)){

                 //Create a thread that communicates with the server
                 ClientConnectServerThread clientConnectServerThread =
                         new ClientConnectServerThread(socket);
                 //start up the thread
                 clientConnectServerThread.start();
                 //add this thread to ManageClientConnectServerThread
                 ManageClientConnectServerThread.addClientConnectServerThread(userID,clientConnectServerThread);
                 b = true;
             }else{
                 //if login fails,we couldn"t start up the thread that communicate server
                 //and we should close socket
                 socket.close();
             }


         } catch (Exception e) {
             e.printStackTrace();
         }
         return b;

     }


}
