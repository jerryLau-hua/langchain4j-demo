package com.jerry.springaialibabademo.demos.web;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@RestController
@RequestMapping("/chat-memory")
public class ChatMemoryController {

    private final ChatClient chatClient;

    public ChatMemoryController(ChatModel chatModel) {

        this.chatClient = ChatClient.builder(chatModel).build();
    }

    @GetMapping("/in-memory")
    public Flux<String> memory(
            @RequestParam("prompt") String prompt,
            @RequestParam("chatId") String chatId,
            HttpServletResponse response
    ) {

        response.setCharacterEncoding("UTF-8");

        return chatClient.prompt(prompt).advisors(
                new MessageChatMemoryAdvisor(
                        new InMemoryChatMemory())
        ).advisors(
                a -> a
                        .param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100)
        ).stream().content();
    }



//	@GetMapping("/redis")
//	public Flux<String> redis(
//			@RequestParam("prompt") String prompt,
//			@RequestParam("chatId") String chatId,
//			HttpServletResponse response
//	) {
//
//		response.setCharacterEncoding("UTF-8");
//
//		return chatClient.prompt(prompt).advisors(
//				new MessageChatMemoryAdvisor(new RedisChatMemory(
//						"127.0.0.1",
//						6379,
//						"springaialibaba123456"
//				))
//		).advisors(
//				a -> a
//						.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
//						.param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100)
//		).stream().content();
//	}


}