package com.kaiwen.base.modles.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;


/**
 * 服务启动监听器
 */
@Component
public class NettyServerListener {

    private final static Logger logger = LoggerFactory.getLogger(NettyServerListener.class);

    /**
     * 创建bootstrap
     */
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    /**
     * BOSS
     */
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    /**
     * Worker
     */
    EventLoopGroup workGroup = new NioEventLoopGroup();

    /**
     * 通道适配器
     */
    @Resource
    private AdapterCommon adapterCommon;

    /**
     * netty服务配置信息
     */
    @Resource
    private NettyServerConfig nettyConfig;

    /**
     * 关闭服务器方法
     */
    @PreDestroy
    public void close() {
        logger.info("关闭服务器....");
        //优雅退出
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }

    /**
     * 开启服务
     */
    public void startServer() throws Exception{
        // 从配置文件中(application.yml)获取服务端监听端口
        Integer port = nettyConfig.getPort();
        if(port!=null && port>0){
            try {
                serverBootstrap.group(bossGroup, workGroup)
                        .channel(NioServerSocketChannel.class)
                        .option(ChannelOption.SO_BACKLOG, 2048) //设置tcp缓冲区
                        //.option(ChannelOption.SO_SNDBUF, 32 * 1024) // 设置发送缓冲大小
                        //.option(ChannelOption.SO_RCVBUF, 32 * 1024) // 设置接收缓冲大小
                        //.option(ChannelOption.SO_KEEPALIVE, true) // 保持连接
                        .handler(new LoggingHandler(LogLevel.INFO))
                        .childHandler(new  ChannelInitializer<SocketChannel>(){ //绑定I/O事件的处理类
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                ChannelPipeline pipeline = socketChannel.pipeline();
                                //解码器
                                pipeline
                                        .addLast(new LineBasedFrameDecoder(nettyConfig.getMaxframelength())) // 基于换行符号
                                        .addLast(new StringDecoder(CharsetUtil.UTF_8)) //解码转String
                                        .addLast(new StringEncoder(CharsetUtil.UTF_8)) //编码器 String

                                        .addLast(adapterCommon); // 在管道中添加自定义的接收数据实现方法
                            }
                        });

                ChannelFuture f = serverBootstrap.bind(port).sync(); // 绑定端口
                logger.info("【netty服务器在 [{}] 端口启动监听】", port);
                f.channel().closeFuture().sync(); // 等待服务端监听端口关闭

            }finally {
                // 优雅的退出 释放线程池资源
                bossGroup.shutdownGracefully();
                workGroup.shutdownGracefully();
            }
        }

    }



}
