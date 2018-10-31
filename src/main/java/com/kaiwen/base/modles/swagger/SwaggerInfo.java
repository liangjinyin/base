package com.kaiwen.base.modles.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * @author: liangjinyin
 * @date:  2018/10/31 14:04
 * @description: swagger 配置信息
 */
@Component
@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerInfo {

    private String groupName ="controller";

    private String basePackage;

    private String antPath;

    private String title = "HTTP API";

    private String description = "Swagger 自动生成接口文档";

    private String license = "Apache License Version 2.0";
}
