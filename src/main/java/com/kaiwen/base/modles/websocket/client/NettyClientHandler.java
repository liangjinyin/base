package com.kaiwen.base.modles.websocket.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: liangjinyin
 * @Date: 2019-08-12
 * @Description:
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {
    public static ChannelHandlerContext context = null;

    //利用写空闲发送心跳检测消息
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println(evt);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println(msg);
    }
}
