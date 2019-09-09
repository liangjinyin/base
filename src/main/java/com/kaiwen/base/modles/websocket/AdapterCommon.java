package com.kaiwen.base.modles.websocket;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiwen.base.modles.websocket.redis.RedisUtils;
import com.kaiwen.base.modles.websocket.service.AisService;
import com.kaiwen.base.modles.websocket.service.CorsService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

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
    private AisService aisService;

    @Resource
    private CorsService corsService;

    @Resource
    private JedisPool jedisPool;

    @Resource(name = "ValueOperationsBean")
    private ValueOperations valueOperations;


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
        //服务器接收数据时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String receivetime = sdf.format(new Date());

        //注意此处已经不需要手工解码了
        String message = msg.toString();
        log.info("【" + ctx.channel().id() + "】" + receivetime + "==>>>" + message);
        if (StringUtils.isEmpty(message)) {
            return;
        }

        /**
         * 成都天奥 推送船舶数据
         * “$AISORG,MMSI号，IMO编号，船舶名称，呼号，AIS设备类型，航行状态，对地航速，对地航向，艏向，经度，纬度，船长，船宽，船货类型，预计到达时间，最大吃水深度，目的地，船载人数，转向率\r\n”
         * $AISORG,413556872,56546654,CHANGJIANGYIHAO,CHSDDD,ClASS-A,1,7.5,231.3,231,130.555444,24.015612,25.5,8.4,1,07-12/15:32,5.6,WUHANG,12,+165.0\r\n
         */
        if (message.contains("$AISORG,")) {
            JSONObject jsonObject = aisService.aisShipParser(message);
            if (jsonObject != null && !jsonObject.isEmpty() && jsonObject.containsKey("mmsi")
                    && jsonObject.containsKey("longitude") && jsonObject.containsKey("latitude")) {
                String mmsi = jsonObject.getString("mmsi"); //船舶唯一标识
                String longitude = jsonObject.getString("longitude");
                String latitude = jsonObject.getString("latitude");
                if (!StringUtils.isEmpty(mmsi) && !StringUtils.isEmpty(longitude) && !StringUtils.isEmpty(latitude)) {
                    //delRedisDataVague("AIS:" + mmsi + "_*");
                    //Time.sleep(6);
                    jsonObject.put("receiveTime", receivetime); //服务器接收数据时间
                    valueOperations.set("AIS:" + mmsi + "_" + jsonObject.getString("positionType"), jsonObject.toJSONString());
                }
            }
        }

        /**
         *  导航 推送高精度用户
         * $CORS,id编号,用户名,密码,在线时长,登录次数,过期时间戳,差分模式,基站编码,更新时间戳,基站或三角网编码,在线状态,最新的位置纬度,最新的位置经度,最新的位置高程,最新的位置GEO,GGA的最新解算状态,
         *   最后一次在线时间,GGA中当前基站索引号,流动站上发数据字节总数,下发数据到流动站字节数,上传的GGA总数,固定解GGA总数,下发差分改正数次数,GGA中差分延时,下发差分卫星数信息,使用的接入点名称,用户类型,定位类型
         *
         * $CORS,849cdd31314646c9829be1b9df46250d,test,123,20,0,1582905599,2,S031-S032-S033vrs_23.181416_113.416692_15.274148,1562058957,S031-S032-S033,0,23.1815052,
         *      113.416779,51.6100006,010100000037AAD381AC5A5C407C0F971C772E3740,4,1562058957,,12021,234,3,3,3,0,,,0,0
         */
        else if (message.contains("$CORS,")) {
            // 回传信息
            ctx.writeAndFlush("$CORS_" + System.currentTimeMillis());

            JSONObject jsonObject = corsService.corsUserParser(message);
            if (jsonObject != null && !jsonObject.isEmpty() && jsonObject.containsKey("id")
                    && jsonObject.containsKey("latestPositionB") && jsonObject.containsKey("latestPositionL")) {
                String id = jsonObject.getString("id");
                String latestPositionB = jsonObject.getString("latestPositionB");
                String latestPositionL = jsonObject.getString("latestPositionL");
                if (!StringUtils.isEmpty(id) && !StringUtils.isEmpty(latestPositionL) && !StringUtils.isEmpty(latestPositionB)) {
                    //存入redis：先删后增
                    delRedisDataVague("CORS:" + id + "_*");
                    jsonObject.put("receiveTime", receivetime); //服务器接收数据时间
                    valueOperations.set("CORS:" + id + "_" + jsonObject.getString("positionType"), jsonObject.toJSONString());
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

    /**
     * redis中的数据  根据模糊key 批量 or 单个 删除
     */
    public void delRedisDataVague(String key) throws Exception{
        if(!StringUtils.isEmpty(key)){
            Jedis jedis = null;
            Pipeline pl = null;//管道
            try {
                if(jedisPool==null){
                    return;
                }
                jedis = jedisPool.getResource();
                pl = jedis.pipelined();
                Response<Set<String>> keysResponse = pl.keys(key);
                pl.sync();
                Set<String> keys = new HashSet<>();
                if (keysResponse != null) {
                    keys = keysResponse.get();
                }

                if (keys.size() > 0) {
                    for (String k : keys) {
                        pl.del(k);
                    }
                    pl.sync();
                }

            } finally {
                if (jedis != null) {
                    jedis.close();
                }
                if (pl != null) {
                    pl.close();
                }
            }
        }
    }
}
