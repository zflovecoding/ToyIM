package com.geekpig.qqserver.service;

import com.geekpig.qqcommon.Message;
import com.geekpig.qqcommon.MessageType;
import com.geekpig.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

//connect client
public class QQServer {
    private ServerSocket ss = null;
    //hashmap is unsafe in multi-thread mode
    //Concurrent is safe to handle Concurrency Situation
    private static ConcurrentHashMap<String,User> validUsers  = new ConcurrentHashMap<>();
    //set hashmap static ,then can initialize it in static code block
    static {
        validUsers.put("233",new User("233","233233"));
        validUsers.put("rust",new User("rust","rustisfuture"));
        validUsers.put("go",new User("go","gogogo"));
        validUsers.put("大灰狼",new User("大灰狼","lovegxx"));
        validUsers.put("白团",new User("白团","baibaibai"));
        validUsers.put("黑团",new User("黑团","heiheihei"));
        validUsers.put("小白兔",new User("小白兔","lovegxx"));
        validUsers.put("鼠子",new User("鼠子","diedie"));
    }
    public boolean checkUser(String userID,String pwd){
        boolean b = true;
        User user = validUsers.get(userID);
        if(user == null){
            b = false;
        }else if(!(user.getPasswd().equals(pwd))){
            b = false;
        }
        return b;
    }
    //the constructor
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
                if(checkUser(u.getUserID(),u.getPasswd())){
                    msg.setMsgType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    //msg object reply client
                    oos.writeObject(msg);
                    //start up a thread to  keep communicating
                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, u.getUserID());
                    serverConnectClientThread.start();
                    //add to ManageServerConnectClientThread
                    ManageServerConnectClientThread.addServerConnectClientThread(u.getUserID(),serverConnectClientThread);

                }else {//login failed
                    System.out.println("用户 id=" + u.getUserID() + " pwd=" + u.getPasswd() + " 验证失败");
                    msg.setMsgType(MessageType.MESSAGE_LOGIN_FAILED);
                    oos.writeObject(msg);//return message
                    //login failed ,shutdown the socket
                    socket.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //If the server exits while,
            // it means that the server is not listening,
            // so close the ServerSocket
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
