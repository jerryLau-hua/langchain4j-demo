package com.jerry.langchain4jspringbootdemo.config;


import com.jerry.langchain4jspringbootdemo.service.ToolService;
import com.jerry.langchain4jspringbootdemo.store.PersistentChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2025/3/26 13:33
 * @注释 ai配置类
 */
@Configuration
public class AIConf {

    /***
     * 聊天助手接口
     */
    public interface ChatAssistant {

        /***
         * 普通聊天 非隔离上下文
         * @param question
         * @return
         */
        @SystemMessage("""
                    您好，现在是{{time}}，我是机器人小小，很高兴为您服务。
                    请问有什么可以帮助您？
                """)
        String chat(@UserMessage String question,@V("time") Date time);

        /***
         * 流式输出 非隔离上下文
         * @param question
         * @return
         */
        TokenStream streamChat(String question);

        /***
         * 普通聊天 隔离上下文 通过id 进行上下文隔离 达到不通用户聊天互不影响的效果
         * @param id 上下文id
         * @param question 用户问题
         * @return
         */
//        String chat(@MemoryId int id, @UserMessage String question);

        /***
         * 流式输出 隔离上下文 通过id 进行上下文隔离 达到不通用户聊天互不影响的效果
         * @param id 上下文id
         * @param question 用户问题
         * @return
         */
//        TokenStream streamChat(@MemoryId int id, @UserMessage String question);

    }


    /***
     * 注入聊天服务  非隔离上下文
     * @param chatLanguageModel
     * @param streamingChatLanguageModel
     * @return
     */
    @Bean
    public ChatAssistant chatAssistant(ChatLanguageModel chatLanguageModel,
                                       StreamingChatLanguageModel streamingChatLanguageModel,
                                       ToolService toolService) {

        //使用简单的ChatMemory  - MessageWindowChatMemory来保存聊天记录
        MessageWindowChatMemory messageWindowChatMemory = MessageWindowChatMemory.builder()
                .maxMessages(10).build(); //最多保存10条记录

        ChatAssistant build = AiServices.builder(ChatAssistant.class)
                .chatLanguageModel(chatLanguageModel)
                .streamingChatLanguageModel(streamingChatLanguageModel)
                .chatMemory(messageWindowChatMemory)
//                .tools(toolService)
                .build();

        return build;
    }


    /***
     * 注入聊天服务  隔离上下文
     * @param chatLanguageModel
     * @param streamingChatLanguageModel
     * @return
     */
//    @Bean
//    public ChatAssistant chatAssistant(ChatLanguageModel chatLanguageModel, StreamingChatLanguageModel streamingChatLanguageModel) {
//
//        //使用简单的ChatMemory  - MessageWindowChatMemory来保存聊天记录
////        MessageWindowChatMemory.builder().maxMessages(10).id(memoryId).build()
//        ChatAssistant build = AiServices.builder(ChatAssistant.class)
//                .chatLanguageModel(chatLanguageModel)
//                .streamingChatLanguageModel(streamingChatLanguageModel)
//                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.builder().maxMessages(10).id(memoryId).build())
//                .build();
//
//        return build;
//    }


    /***
     * 注入聊天服务  隔离上下文 - 持久化聊天上下文记录-mapdb
     * @param chatLanguageModel
     * @param streamingChatLanguageModel
     * @return
     */
//    @Bean
//    public ChatAssistant chatAssistantWithStore(ChatLanguageModel chatLanguageModel, StreamingChatLanguageModel streamingChatLanguageModel) {
//
//        //使用mapdb 来保存聊天记录
//
//        PersistentChatMemoryStore persistentChatMemoryStore = new PersistentChatMemoryStore();
//
//        ChatMemoryProvider chatMemoryProvider = (memoryId) -> {
//            return MessageWindowChatMemory.builder()
//                    .maxMessages(10)
//                    .id(memoryId)
//                    .chatMemoryStore(persistentChatMemoryStore)
//                    .build();
//        };
//
//        ChatAssistant build = AiServices.builder(ChatAssistant.class)
//                .chatLanguageModel(chatLanguageModel)
//                .streamingChatLanguageModel(streamingChatLanguageModel)
//                .chatMemoryProvider(chatMemoryProvider)
//                .build();
//
//        return build;
//    }


}
