package com.xzc.net.chat;

import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ServerMessageHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 管理全局的channel
     * GlobalEventExecutor.INSTANCE 全局事件监听器
     * 一旦 将channel加入ChannelGroup，就不用手动移除，它会自动处理
     */
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 为了实现私聊功能，这里key存储用户的唯一标识
     * 需要手动维护用户的上下线，不能像ChannelGroup那样自动管理
     */
    private static Map<String, Channel> all = new HashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        if (msg.contains("#")) {
            String id = msg.split("#")[0];
            String body = msg.split("#")[1];
            Channel userChannel = all.get(id);
            String key = channel.remoteAddress().toString().split(":")[1];
            userChannel.writeAndFlush(LocalDateTime.now() + "\n [用户] " + key + " 说：" + body);
            return;
        }
        for (Channel c : channels) {
            String addr = c.remoteAddress().toString();
            if (!channel.equals(c)) {
                c.writeAndFlush(LocalDateTime.now() + "\n [用户] " + addr + " 说：" + msg);
            } else {
                c.writeAndFlush(LocalDateTime.now() + "\n [自己] " + addr + " 说：" + msg);
            }
        }
    }

    /**
     * 建立连接后第一个调用的方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String addr = channel.remoteAddress().toString();

        channel.writeAndFlush(LocalDateTime.now() + "\n [用户] " + addr + " 加入聊天室");
        channels.add(channel);
        String key = channel.remoteAddress().toString().split(":")[1];

        all.put(key, channel);
    }

    /**
     * channel连接状态就绪以后调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String addr = ctx.channel().remoteAddress().toString();
        System.out.println(LocalDateTime.now() + "\n [用户] " + addr + " 上线");
    }

    /**
     * channel 连接状态断开后触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String addr = ctx.channel().remoteAddress().toString();
        System.out.println(LocalDateTime.now() + "\n [用户] " + addr + " 下线");
        String key = ctx.channel().remoteAddress().toString().split(":")[1];
        // 下线移除
        all.remove(key);
    }

    /**
     * 连接发送异常时触发
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 断开连接时会触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String addr = channel.remoteAddress().toString();
        channels.writeAndFlush(LocalDateTime.now() + "\n [用户] " + addr + " 离开了");
        // 打印ChannelGroup中的人数
        System.out.println("当前在线人数是：" + channels.size());
        System.out.println("all：" + all.size());
    }
}
