package com.xzc.net.bio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServer {
    static int count = 0;

    public static void main(String[] args) throws IOException {
        // 创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 创建 serversocket
        ServerSocket serverSocket = new ServerSocket();
        // 绑定端口号
        serverSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(), 9999));
        System.out.println("等待发送消息。。。。。。进入阻塞状态。。。。。。");
        while (true) {
            final Socket socket = serverSocket.accept();
            final PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            executorService.submit(() -> {
                count++;
                System.out.println("count = " + count);
                // 获取 outputstream
                try {
                    byte[] bytes = new byte[1024];
                    if (socket.getInputStream().read(bytes) > 0) {
                        System.out.println("接收到客户端消息是：" + new String(bytes).trim());
                    }
                    // 服务端响应
                    String msg = "我是服务端响应：hello client";
                    printWriter.write(msg);
                    printWriter.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
