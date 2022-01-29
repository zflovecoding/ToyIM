package com.geekpig.qqserver.service;

import com.geekpig.qqcommon.Message;
import com.geekpig.qqcommon.MessageType;
import com.geekpig.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//connect client
public class QQServer {
    private ServerSocket ss = null;

    public QQServer(){
        try {
            System.out.println("服务端在9999端口监听...");
            ss = new ServerSocket(9999);
            //When connected to a client,
            // it will continue to listen, so use while loop
            while(true){
                Socket socket = ss.accept();//if no client connects ,it will blocks here
                //Get the object input stream associated with the socket
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //Get the object output stream associated with the socket
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                //read user obj
                User u = (User)ois.readObject();
                //new message object ,ready to reply client
                Message msg = new Message();
                if(u.getUserID().equals("100") && u.getPasswd().equals("123456")){
                    msg.setMsgType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    //msg object reply client
                    oos.writeObject(msg);
                    //start up a thread to  keep communicating
                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, u.getUserID());
                    serverConnectClientThread.start();
                    //add to ManageServerConnectClientThread
                    ManageServerConnectClientThread.addServerConnectClientThread(u.getUserID(),serverConnectClientThread);



                }else {
                    msg.setMsgType(MessageType.MESSAGE_LOGIN_FAILED);
                    oos.writeObject(msg);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
