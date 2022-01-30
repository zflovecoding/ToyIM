package com.geekpig.qqclient.service;

import com.geekpig.qqcommon.Message;
import com.geekpig.qqcommon.MessageType;

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

                System.out.println("客户端线程，等待读取从服务器端发送的消息");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message msg = (Message) ois.readObject();
                //later,wo will use this message
                //according to the type of message,we have different business processing
                if(msg.getMsgType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)){
                    //get the user list form server
                    System.out.println("\n=======当前在线用户列表========");
                    String[] onlineUsers = msg.getContent().split(" ");
                    for (int i = 0; i < onlineUsers.length; i++) {
                        System.out.println("用户: " + onlineUsers[i]);
                    }


                }else if(msg.getMsgType().equals(MessageType.MESSAGE_COMM_MES)){
                    System.out.println("\n" + msg.getSender()
                            + " 对 " + msg.getGetter() + " 说: " + msg.getContent());
                }
                else{
                    //doesn't get the return message for pulling user list
                    System.out.println("其它类型的消息，暂不处理");
                }

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
