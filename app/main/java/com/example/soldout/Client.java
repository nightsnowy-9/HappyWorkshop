package com.example.soldout;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Client {
    public  String start(String args)  {
        // 1、准备Socket，连接服务器，需要指定服务器的IP地址和端口号
        try{
        Socket socket = new Socket("49.233.31.124", 8888);

        // 2、获取输出流，用来发送数据给服务器
        OutputStream out = socket.getOutputStream();
        PrintStream ps = new PrintStream(out);

        // 3、获取输入流，用来接收服务器发送给该客户端的数据
        InputStream input = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(input));

       ps.println(args);
        // 接收数据
       String feedback  = br.readLine();
       // System.out.println("从服务器收到的数据是：" + feedback);
               //5、关闭socket，断开与服务器的连接
        socket.close();
        System.out.println(args);
        System.out.println(feedback);
        return feedback;
    }
        catch (Exception e)
        {
            e.printStackTrace();
            return "false";
        }
    }

}