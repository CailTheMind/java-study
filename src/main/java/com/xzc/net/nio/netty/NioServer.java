package com.xzc.net.nio.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class NioServer {

    private Selector selector;

    /**
     * 初始化
     *
     * @param port
     */
    public void initServer(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 设置通道为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 绑定端口号
        serverSocketChannel.bind(new InetSocketAddress(port));
        // 获取通道管理器
        this.selector = Selector.open();
        /**
         * 将通道管理器和通道绑定，并为通道注册 SelectionKey.OP_ACCEPT事件
         * 当该事件到达时，selector.select()会返回，如果该事件没有到达selecttor.select()会一直阻塞
         */
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * 监听
     */
    public void listen() throws IOException {
        while (true) {
            int select = selector.select();
            if (select == 0) {
                continue;
            }
            // 获取选中的迭代器
            Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                // 删除防止重复处理
                iterator.remove();
                if (selectionKey.isAcceptable()) {
                    // 客户端请求连接事件
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    // 获取客户端连接通道
                    SocketChannel channel = serverSocketChannel.accept();
                    //设置非阻塞
                    channel.configureBlocking(false);
                    // 在与客户端连接成功之后，给通道设置权限
                    channel.register(this.selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    read(selectionKey);
                }
            }
        }
    }

    public void read(SelectionKey key) throws IOException {
        // 服务器可读取消息，得到事件发生的通道
        SocketChannel socketChannel = (SocketChannel) key.channel();
        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        socketChannel.read(byteBuffer);
        byte[] array = byteBuffer.array();
        String msg = new String(array);
        System.out.println("服务端获取的数据：" + msg.trim());
        String res = "我是 服务端响应信息：hello client";
        ByteBuffer buffer = ByteBuffer.wrap(res.getBytes(StandardCharsets.UTF_8));
        // 将消息发给服务端
        socketChannel.write(buffer);
    }


    public static void main(String[] args) throws IOException {
        NioServer server = new NioServer();
        server.initServer(9999);
        server.listen();
    }


}
