package com.xzc.net.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * netty群聊 服务端
 *
 * @author xzc
 */
public class NettyChatServer {

    private int port;

    public NettyChatServer(int port) {
        this.port = port;
    }

    private void init() {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup(16);

        try {
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(boss, work);
            // 设置boss selector简历 channel
            boot.channel(NioServerSocketChannel.class);
            // boss等待连接的队列长度
            boot.option(ChannelOption.SO_BACKLOG, 128);
            // 让客户端保持长期活动
            boot.childOption(ChannelOption.SO_KEEPALIVE, true);
            boot.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    // 从channel中获取pipeline 并添加handle
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast("encode", new StringEncoder());
                    pipeline.addLast("decode", new StringDecoder());
                    // 自定义handler来处理消息
                    pipeline.addLast(new ServerMessageHandler());
                }
            });
            System.out.println("服务器开始启动............");
            // 绑定端口
            ChannelFuture channelFuture = boot.bind(port).sync();
            channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("服务器正在启动...");
                    }
                    if (future.isDone()) {
                        System.out.println("服务器启动成功...OK");
                    }
                }
            });
            // 监听channel关闭
            channelFuture.channel().closeFuture().sync();
            channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isCancelled()) {
                        System.out.println("服务器正在关闭...");
                    }
                    if (future.isCancellable()) {
                        System.out.println("服务器已关闭...OK");
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new NettyChatServer(9090).init();
    }
}
