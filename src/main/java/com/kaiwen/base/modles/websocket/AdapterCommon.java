package com.kaiwen.base.modles.websocket;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通道适配器 针对不同协议数据格式 调用不同的解析方法 存入redis中
 */
@Component
@Sharable //多线程共享
@Slf4j
public class AdapterCommon extends ChannelInboundHandlerAdapter {


    //保留船舶唯一标识：mmsi号 + 位置信息
    private static final Map<String, JSONObject> shipMap = new HashMap<>();
    //保留北斗定位船舶唯一标识：mmsi号, 用户统计北斗船舶数目
    private static final Set bdSet = new HashSet();

    @Resource
    private AisServiceImpl aisService;


    // 保存相对独立的客户端
    private static final Map<String, ChannelHandlerContext> clientmap; //设备ID + ctx
    static {
        clientmap = Collections.synchronizedMap(new ConcurrentHashMap<>());
    }

    /**
     * channelAction
     * channel 通道
     * action 活跃的
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
        log.info("【" + ctx.channel().id() + "】" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "==>>>"
                + "channelActive");
        String str = "connect success: " + new Date() + " " + ctx.channel().localAddress() + "\r\n";
        ctx.writeAndFlush(str);
    }

    /**
     * channelInactive
     * channel 通道
     * Inactive 不活跃的
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    public void channelInactive(ChannelHandlerContext ctx)
            throws Exception {
        log.info("【" + ctx.channel().id() + "】" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "==>>>"
                + "channelInactive");
        if (!CollectionUtils.isEmpty(clientmap)) {
            for (Map.Entry<String, ChannelHandlerContext> entry : clientmap.entrySet()) {
                if (ctx.channel().id() == entry.getValue().channel().id()) {
                    clientmap.remove(entry.getKey());
                }
            }
        }
    }

    /**
     * channelRead
     * channel 通道
     * Read 读
     * 简而言之就是从通道中读取数据，也就是服务端接收客户端发来的数据
     * 但是这个数据在不进行解码时它是ByteBuf类型的数据
     */
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //接收数据时间(本地时间)
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String receivetime = sdf.format(date);

        //注意此处已经不需要手工解码了
        log.info("【" + ctx.channel().id() + "】" + receivetime + "==>>>" + msg);
        String message = msg.toString();
        if (StringUtils.isEmpty(message)) {
            return;
        }

        /**
         * 成都天奥 推送船舶数据
         *
         * “$AISORG,MMSI号，IMO编号，船舶名称，呼号，AIS设备类型，航行状态，对地航速，对地航向，艏向，经度，纬度，船长，船宽，船货类型，预计到达时间，最大吃水深度，目的地，船载人数，转向率\r\n”
         * $AISORG,413556872,56546654,CHANGJIANGYIHAO,CHSDDD,ClASS-A,1,7.5,231.3,231,130.555444,24.015612,25.5,8.4,1,07-12/15:32,5.6,WUHANG,12,+165.0\r\n
         */
        if(message.contains("$AISORG,")) {
            JSONObject jsonObject = aisService.aisShipParser(message);
            if (jsonObject != null && !jsonObject.isEmpty() && jsonObject.containsKey("mmsi")
                    && jsonObject.containsKey("longitude") && jsonObject.containsKey("latitude")) {
                String mmsi = jsonObject.getString("mmsi");
                String longitude = jsonObject.getString("longitude");
                String latitude = jsonObject.getString("latitude");
                if(!StringUtils.isEmpty(mmsi) && !StringUtils.isEmpty(longitude) && !StringUtils.isEmpty(latitude)){
                    shipMap.put(mmsi, jsonObject);
                }

                //船舶单北斗定位 集合
                if(jsonObject.containsKey("elecequipment") && "bd".equalsIgnoreCase(jsonObject.getString("elecequipment"))){
                    bdSet.add(mmsi);
                }
            }
        }
    }

    /**
     * channelReadComplete channel 通道 Read 读取 Complete 完成
     * 在通道读取完成后会在这个方法里通知，对应可以做刷新操作 ctx.flush()
     */
    public void channelReadComplete(ChannelHandlerContext ctx)
            throws Exception {
        ctx.flush();
    }

    /**
     * exceptionCaught exception 异常 Caught 抓住
     * 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     */
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (!CollectionUtils.isEmpty(clientmap)) {
            for (Map.Entry<String, ChannelHandlerContext> entry : clientmap.entrySet()) {
                if (ctx.channel().id() == entry.getValue().channel().id()) {
                    clientmap.remove(entry.getKey());
                }
            }
        }

        cause.printStackTrace();
        ctx.close();
        log.info("异常信息：\r\n" + cause.getMessage());
    }



    public static Map<String, JSONObject> getShipMap() {
        return shipMap;
    }

    public static Set getHashSet() {
        return bdSet;
    }



}
