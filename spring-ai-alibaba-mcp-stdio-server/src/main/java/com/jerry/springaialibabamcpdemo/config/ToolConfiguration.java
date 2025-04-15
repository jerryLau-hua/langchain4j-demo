package com.jerry.springaialibabamcpdemo.config;

import com.jerry.springaialibabamcpdemo.service.aboutWeather.AirQualityService;
import com.jerry.springaialibabamcpdemo.service.aboutWeather.WeatherService;
import com.jerry.springaialibabamcpdemo.service.cyberfortune.CyberFortuneService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/***
 * @author jerryLau
 * @version 1.0
 * @Date 2025/4/10 16:24
 * @注释 工具配置类
 * 用于配置工具回调提供者，将工具方法注册为可调用的工具
 */

@Configuration
public class ToolConfiguration {

    @Bean
    public ToolCallbackProvider weatherTools(
            AirQualityService airQualityService,
            WeatherService weatherService,
            CyberFortuneService cyberFortuneService) {
        return MethodToolCallbackProvider
                .builder()
                .toolObjects(airQualityService, weatherService, cyberFortuneService)
                .build();
    }
}