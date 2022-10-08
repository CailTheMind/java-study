package com.xzc.net.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class BioClient {

    public static void main(String[] args) throws IOException {
        // 创建socket
        Socket socket = new Socket();
        // 连接
        socket.connect(new InetSocketAddress(InetAddress.getLocalHost(), 9999));
        // 创建printWriter
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        // 读回数据
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // 发送请求的数据
        String msg = "我是客户端发送的请求：hello server";
        // 客户度写出的数据
        printWriter.write(msg);
        printWriter.flush();
        // 读回数据
        byte[] bytes = new byte[1024];
        if (socket.getInputStream().read(bytes) > 0) {
            System.out.println("服务端返回的信息:" + new String(bytes).trim());
        }


    }
}
