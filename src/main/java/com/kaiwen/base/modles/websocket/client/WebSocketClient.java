package com.kaiwen.base.modles.websocket.client;

import lombok.extern.slf4j.Slf4j;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;


/**
 * @author: liangjinyin
 * @Date: 2019-08-09
 * @Description:
 */
@Slf4j
public class WebSocketClient extends org.java_websocket.client.WebSocketClient {


    public WebSocketClient(String serverUri) throws URISyntaxException{
        super(new URI(serverUri));
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("握手...");
        for(Iterator<String> it = serverHandshake.iterateHttpFields(); it.hasNext();) {
            String key = it.next();
            log.info(key+":"+serverHandshake.getFieldValue(key));
        }
    }

    @Override
    public void onMessage(String s) {
        log.info("接收到服务端回传的信息：{}", s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.info("关闭 webSocket!");
    }

    @Override
    public void onError(Exception e) {
        log.info("出现了异常 异常信息为:{}", e.getMessage());
    }

    public static void main(String[] args) {
        WebSocketClient client = null;
        try {
            client = new WebSocketClient("ws://127.0.0.1:8987/base/webSocket");
            client.connect();
            while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                System.out.println("还没有打开");
            }
            log.info("建立webSocket连接!");
            client.send("$AISORG,423556f03,56546654,CHANGJIANGYIHAO,CHSDDD,ClASS-A,0,7.5,231.3,231,130.565444,24.045612,25.5,8.4,0,07-12/15:32,5.6,WUHANG,12,+165.0,10");
            client.send("$CORS,849cdd31314646c9829be1b9df46250k,test,123,20,0,1582905599,2,S031-S032-S033vrs_23.181416_113.416692_15.274148,1562058957,S031-S032-S033,1,23.1815052,113.416779,51.6100006,010100000037AAD381AC5A5C407C0F971C772E3740,4,1562058957,,12021,234,3,3,3,0,,,0,0");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }finally {
            client.close();
        }
    }
}
