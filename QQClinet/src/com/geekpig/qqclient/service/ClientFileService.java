package com.geekpig.qqclient.service;

import com.geekpig.qqcommon.Message;
import com.geekpig.qqcommon.MessageType;

import java.io.*;

public class ClientFileService {

     public void  sendPrivateFile(String src,String dest,String sender,String getter){
         //except to transmit message ,here ,we need to read file from src

         //set the message
         Message message = new Message();
         message.setMsgType(MessageType.MESSAGE_FILE_MES);
         message.setSender(sender);
         message.setGetter(getter);
         message.setSrc(src);
         message.setDest(dest);


         //read message
         FileInputStream fileInputStream = null;
         //byte array for transmitting  file
         byte[] bytes = new byte[(int)new File(src).length()];
         try {
             fileInputStream = new FileInputStream(src);
             fileInputStream.read(bytes);//get the src file read into byte array
             //output file into message, should set byte[]  here ,in finally{}, the stream will be closed
             message.setBytes(bytes);
         } catch (IOException e) {
             e.printStackTrace();
         }finally {
            //close stream
             try {
                 fileInputStream.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }

         //提示信息
         System.out.println("\n" + sender + " 给 " + getter + " 发送文件: " + src
                 + " 到对方的电脑的目录 " + dest);
         try {
             ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.
                     getClientConnectServerThread(sender).
                     getSocket().getOutputStream());
             oos.writeObject(message);
         } catch (IOException e) {
             e.printStackTrace();
         }


     }

}
