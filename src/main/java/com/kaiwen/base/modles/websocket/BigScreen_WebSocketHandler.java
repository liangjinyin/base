package com.kaiwen.base.modles.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Component
public class BigScreen_WebSocketHandler implements WebSocketHandler {
    
    private static final Logger log = LoggerFactory.getLogger(BigScreen_WebSocketHandler.class);


    /**
     * 保存客户端连接
     */
    private static final List<WebSocketSession> list; //所有连接
    static {
        list = Collections.synchronizedList(new ArrayList<>());
    }


    /**
     * 关闭
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("connect websocket closed......." + session.getId());
        if (!CollectionUtils.isEmpty(list)) {
            list.remove(session);
        }
    }

    
    /**
     * 连接 就绪时 连接成功时，会触发页面上onopen方法
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("connect websocket success......." + session.getId());

        // ws://10.28.3.215:8089/bigscr/webSocket
        URI uri = session.getUri();
        if (uri == null || StringUtils.isEmpty(uri.toString())) {
            return;
        }
        list.add(session);
    }


    /**
     * 接收socket信息 js调用websocket.send时候，会调用该方法
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message.getPayloadLength() == 0) {
            return;
        }
        String json = message.getPayload().toString();
        log.info(json);
    }

    
    /**
     * 连接出错时
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        log.info("WebSocketSession 连接出错...");
        if (!CollectionUtils.isEmpty(list)) {
            list.remove(session);
        }
    }


    /**
     * 支持部分消息
     */
    @Override
    public boolean supportsPartialMessages() {
        // TODO Auto-generated method stub
        return false;
    }

    
    //**********************方法*****************************
    /**
     * 推送给所有用户
     */
    public void sendMsgToAll(WebSocketMessage<?> message) throws Exception {
        if (!CollectionUtils.isEmpty(list)) {
            for (WebSocketSession session :list) {
                if (session != null && session.isOpen()) {
                    synchronized(session){
                        session.sendMessage(message);
                    }
                }
            }
        }
    }



}
