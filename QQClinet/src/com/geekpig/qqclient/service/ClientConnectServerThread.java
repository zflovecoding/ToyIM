package com.geekpig.qqclient.service;

import com.geekpig.qqcommon.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends  Thread{
    //hold a socket to communicate
    private Socket socket;
    //this constructor can accept a socket object
    public ClientConnectServerThread(Socket socket){
        this.socket = socket;

    }


    @Override
    public void run() {
        //continuously  communicate
        while (true){
            try {
                System.out.println("客户端线程，等待从读取从服务器端发送的消息");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message msg = (Message) ois.readObject();
                //later,wo will use this message

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //to get socket easily
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
