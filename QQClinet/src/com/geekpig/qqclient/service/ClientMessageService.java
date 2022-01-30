package com.geekpig.qqclient.service;

import com.geekpig.qqcommon.Message;
import com.geekpig.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

//this class used to handle client message related things
public class ClientMessageService {
    //message service class must hold a socket to write output stream


    public void sendPrivateMessage(String content, String sender, String getter){
        Message message = new Message();
        message.setSender(sender);
        message.setMsgType(MessageType.MESSAGE_COMM_MES);//Normal chat messages of this type
        message.setContent(content);
        message.setGetter(getter);
        message.setSendTime(new Date().toString());
        System.out.println(sender + " 对 " + getter + " 说 " + content);
        try {
            //get the sender socket thread to send message to server
            //use sender as userID get the thread throw Manage class ,them get the socket throw thread
            //then output
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(sender).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
