package com.kaiwen.base.modles.websocket.config;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取配置文件 数据
 */
@Component
@ConfigurationProperties(prefix = "netty")
public class NettyServerConfig implements ApplicationContextAware {
    
    private Integer port;
    
    private Integer maxthread;
    
    private Integer maxframelength;
    
    private static ApplicationContext context;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
    
    public Integer getPort() {
        return port;
    }
    
    public void setPort(Integer port) {
        this.port = port;
    }
    
    public Integer getMaxthread() {
        return maxthread;
    }
    
    public void setMaxthread(Integer maxthread) {
        this.maxthread = maxthread;
    }
    
    public Integer getMaxframelength() {
        return maxframelength;
    }
    
    public void setMaxframelength(Integer maxframelength) {
        this.maxframelength = maxframelength;
    }
    
}
