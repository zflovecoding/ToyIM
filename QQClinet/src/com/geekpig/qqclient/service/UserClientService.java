package com.geekpig.qqclient.service;


import com.geekpig.qqcommon.Message;
import com.geekpig.qqcommon.MessageType;
import com.geekpig.qqcommon.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
//this class used to handle user login and sign up...and so on
public class UserClientService {
    //Because we may use user information in other places,
    // so make them member properties
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
     //pull all online users
     public void pullOnlineUserList(){
         Message message = new Message();
         message.setMsgType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
         message.setSender(u.getUserID());
         try {
             //transmit to Server
             //now the socket is not instantiated,wo need to get the socket
             //first get the thread controls that socket form the class manage thread
             //Get the thread object corresponding to userID
             ClientConnectServerThread clientConnectServerThread = ManageClientConnectServerThread.getClientConnectServerThread(u.getUserID());
             //by the thread ,get the socket
             Socket socket = clientConnectServerThread.getSocket();
             //use socket get the Output Stream ,to write Object to Server
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             //send message successfully
             oos.writeObject(message);





         } catch (IOException e) {
             e.printStackTrace();
         }


     }
     //log out client , send EXIT message to Server
    public void logout(){
         //send message to server to close the socket
        Message message = new Message();
        message.setMsgType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(u.getUserID()); //specify the sender of message to ensure which thread need be terminated
        try {

            ClientConnectServerThread clientConnectServerThread = ManageClientConnectServerThread.getClientConnectServerThread(u.getUserID());
            Socket socket = clientConnectServerThread.getSocket();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            //exit the client
            System.out.println(u.getUserID() + " 退出系统 ");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
