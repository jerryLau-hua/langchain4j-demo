package com.jerry.springaialibabamcpstdioclient;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@SpringBootApplication
public class SpringAiAlibabaMcpStdioClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiAlibabaMcpStdioClientApplication.class, args);
    }

    @Bean
    public CommandLineRunner predefinedQuestions(ChatClient.Builder chatClientBuilder, ToolCallbackProvider tools,
                                                 ConfigurableApplicationContext context) {

        return args -> {
            var chatClient = chatClientBuilder
                    .defaultTools(tools)
                    .build();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("\n>>> QUESTION: ");
                String userInput = scanner.nextLine();
                if (userInput.equalsIgnoreCase("exit")) {
                    break;
                }
                String content = chatClient.prompt(userInput).call().content();
                byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
                String str = new String(bytes, StandardCharsets.UTF_8);
                System.out.println("\n>>> ASSISTANT: " + str);
            }
            scanner.close();
            context.close();
        };
    }

}
