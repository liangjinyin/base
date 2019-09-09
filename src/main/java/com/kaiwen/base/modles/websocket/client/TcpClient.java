package com.kaiwen.base.modles.websocket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.Charset;

/**
 * @author: liangjinyin
 * @Date: 2019-08-12
 * @Description:
 */
public class TcpClient {
    private int port;
    private String host;
    public SocketChannel socketChannel;
    private static final EventExecutorGroup group = new DefaultEventExecutorGroup(20);

    public TcpClient(int port, String host) {
        this.port = port;
        this.host = host;
        start();
    }

    private void start() {
        ChannelFuture future = null;
        try {
            EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.group(eventLoopGroup);
            bootstrap.remoteAddress(host, port);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new IdleStateHandler(20, 10, 0));
                    socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));
                    socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
                    //socketChannel.pipeline().addLast(new NettyClientHandler());
                }
            });
            future = bootstrap.connect(host, port).sync();
            if (future.isSuccess()) {
                socketChannel = (SocketChannel) future.channel();
                System.out.println("connect server  成功---------");
            } else {
                System.out.println("连接失败！");
                System.out.println("准备重连！");
                start();
            }
        } catch (Exception e) {

        } finally {
//          if(null != future){
//              if(null != future.channel() && future.channel().isOpen()){
//                  future.channel().close();
//              }
//          }
//          System.out.println("准备重连！");
//          start();
        }
    }

    //$AISORG,423556f03,56546654,CHANGJIANGYIHAO,CHSDDD,ClASS-A,0,7.5,231.3,231,130.585444,24.065612,25.5,8.4,0,07-12/15:32,5.6,WUHANG,12,+165.0,10\r\n;
    //$CORS,849cdd31314646c9829be1b9df46250k,test,123,20,0,1582905599,2,S031-S032-S033vrs_23.181416_113.416692_15.274148,1562058957,S031-S032-S033,1,23.1815052,113.416779,51.6100006,010100000037AAD381AC5A5C407C0F971C772E3740,4,1562058957,,12021,234,3,3,3,0,,,0,0\r\n
    public static void main(String[] args) throws InterruptedException {
        TcpClient bootstrap = new TcpClient(8987, "localhost");
        // 130.585444,24.065612
        Double lon = 130.585444;
        Double lat = 24.065612;
        for (int i = 0; i < 4; i++) {
            lon += 0.1;
            lat = (i % 2 == 0 ? lat + 0.1 : lat - 0.1);
            String location = String.format("%s,%s", String.valueOf(lon), String.valueOf(lat));
            String loginMsg = "$AISORG,423556f03,56546654,CHANGJIANGYIHAO,CHSDDD,ClASS-A,0,7.5,231.3,231,"+location+",25.5,8.4,0,07-12/15:32,5.6,WUHANG,12,+165.0,10\r\n";
            bootstrap.socketChannel.writeAndFlush(loginMsg);
        }
    }
}
