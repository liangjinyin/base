package com.kaiwen.base.modles.websocket;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    @Resource
    private BigScreen_WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/webSocket").addInterceptors(new HandshakeInterceptor()).setAllowedOrigins("*");
        registry.addHandler(webSocketHandler, "/webSockJs").addInterceptors(new HandshakeInterceptor()).withSockJS();
    }


}
