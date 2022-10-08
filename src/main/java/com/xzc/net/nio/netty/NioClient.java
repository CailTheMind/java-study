package com.xzc.net.nio.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class NioClient {

    Selector selector;

    public void initClient(String ip, int port) throws IOException {
        // 获取一个socket通道
        SocketChannel channel = SocketChannel.open();
        // 设置通道为非阻塞
        channel.configureBlocking(false);
        // 获取一个通道管理器
        this.selector = Selector.open();
        // 客户端连接服务器，其 实现方法执行并没有实现连接，需要在listen()方法中
        // 调用channel.finishConnection才能完成连接
        channel.connect(new InetSocketAddress(ip, port));
        // 将管道管理器和通道绑定，并为该通道注册SelectionKey.OP_CONNECT事件
        channel.register(this.selector, SelectionKey.OP_CONNECT);
    }

    public void connection() throws IOException {
        // 轮训访问selector
        while (true) {
            // 选择一组可以进行I、O操作的事件，放在selector中，客户端该方法不会阻塞
            // 这里和服务端的方法不一样，查看API注释可以知道，服务端当 至少一个通道被选中时
            // selector 的wakeup 方法被调用，方法返回，而对与客户端来说，通道是一直被选中的
            this.selector.select();
            Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                // 连接事件发生
                if (selectionKey.isConnectable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    // 如果正在连接则完成连接
                    if (socketChannel.isConnectionPending()) {
                        socketChannel.finishConnect();
                    }
                    //设置成非阻塞
                    socketChannel.configureBlocking(false);
                    // 向服务器发送消息
                    ByteBuffer buffer = ByteBuffer.wrap("我是客户端，hello server".getBytes(StandardCharsets.UTF_8));
                    socketChannel.write(buffer);
                    //连接成功之后注册读取服务器信息事件
                    socketChannel.register(this.selector, SelectionKey.OP_READ);

                } else if (selectionKey.isReadable()){
                    read(selectionKey);
                }
            }
        }
    }

    public void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int len = channel.read(buffer);
        if (len != -1) {
            System.out.println("接收到服务器信息:"+new String(buffer.array(), 0, len));
        }
    }

    public static void main(String[] args) throws IOException {
//        NioClient client = new NioClient();
//        client.initClient("127.1.1.1",9999);
//        client.connection();;

        cn.hutool.socket.nio.NioClient nioClient = new cn.hutool.socket.nio.NioClient(InetSocketAddress.createUnresolved("127.0.0.1", 9999));

        SocketChannel channel = nioClient.getChannel();
        channel.configureBlocking(false);

//        channel.register(this.selector, )
    }
}
