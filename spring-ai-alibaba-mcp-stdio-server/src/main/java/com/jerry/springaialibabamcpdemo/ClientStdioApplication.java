package com.jerry.springaialibabamcpdemo;

import com.jerry.springaialibabamcpdemo.config.ToolConfiguration;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Scanner;

/**
 * @author jerryLau
 * @date 2025/04/10
 */
@SpringBootApplication
@Import(ToolConfiguration.class)
public class ClientStdioApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientStdioApplication.class, args);
    }

}