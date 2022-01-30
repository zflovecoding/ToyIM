package com.geekpig.qqclient.view;


import com.geekpig.qqclient.service.ClientMessageService;
import com.geekpig.qqclient.service.UserClientService;
import com.geekpig.qqclient.utils.Utility;

//the client menu interface
public class QQView {

    private boolean loop = true;
    private String key;
    //NOTICE:here userClientService must be instantiated,OR ! There will be a NullPointerException when call a method in it
    private UserClientService userClientService = new UserClientService();
    //handle message service
    private ClientMessageService clientMessageService = new ClientMessageService();

   // private ClientMessageService clientMessageService = new ClientMessageService();
    public static void main(String[] args) {
        new QQView().mainMenu();
        System.out.println("客户端退出系统.....");
    }
    public  void mainMenu(){
        while (loop){
            System.out.println("===========欢迎登录网络通信系统===========");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 9 退出系统");
            System.out.print("请输入你的选择: ");
            key = Utility.readString(1);
            switch (key){
                case "1":

                    //login need userID and pwd
                    System.out.print("请输入用户号: ");
                    String userID = Utility.readString(50);
                    System.out.print("请输入密  码: ");
                    String pwd = Utility.readString(50);
                    //这里就比较麻烦了, 需要到服务端去验证该用户是否合法
                    //这里有很多代码, 我们这里编写一个类 UserClientService[用户登录/注册]
                    if(userClientService.checkUser(userID,pwd)){
                        System.out.println("===========欢迎 (用户 " + userID + " 登录成功) ===========");
                        //user login succeed
                        //show secondary menu
                        while (loop) {
                            System.out.println("\n=========网络通信系统二级菜单(用户 " + userID + " )=======");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择: ");
                            key = Utility.readString(1);
                            switch (key) {

                                case "1":
                                    userClientService.pullOnlineUserList();
                                    break;
                                case "2":
                                    System.out.println("请输入想对大家说的话: ");
                                    String s = Utility.readString(100);
                                   clientMessageService.sendMessageToAll(s,userID);
                                    break;
                                case "3":
                                    System.out.print("请输入想聊天的用户号(在线): ");
                                    String getterId = Utility.readString(50);
                                    System.out.print("请输入想说的话: ");
                                    String content = Utility.readString(100);
                                    //write a method to send message
                                    clientMessageService.sendPrivateMessage(content,userID,getterId);
                                    break;
                                case "4":
                                    System.out.println("发送文件");
                                    break;
                                case "9":
                                    //call a method which can send message(Type:EXIT) to server
                                    // for close corresponding socket
                                    userClientService.logout();
                                    loop = false;
                                    break;
                            }
                        }
                    }else{
                        System.out.println("=========登录失败=========");

                    }
                    break;
                case "9":
                    loop = false;

                    break;
                default :
                    System.out.println("请输入正确的数字");
            }
        }
    }
}
