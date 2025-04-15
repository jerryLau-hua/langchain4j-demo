package com.jerry.springaialibabamcpsseserver.config;


import com.jerry.springaialibabamcpsseserver.BalanceService.BalanceService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ToolConfiguration {

    @Bean
    public ToolCallbackProvider weatherTools(BalanceService balanceService) {
        return MethodToolCallbackProvider.builder().toolObjects(balanceService).build();
    }
}