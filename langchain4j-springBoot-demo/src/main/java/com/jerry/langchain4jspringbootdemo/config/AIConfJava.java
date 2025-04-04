package com.jerry.langchain4jspringbootdemo.config;


import com.jerry.langchain4jspringbootdemo.service.ToolService;
import dev.langchain4j.community.model.dashscope.QwenEmbeddingModel;
import dev.langchain4j.community.store.embedding.duckdb.DuckDBEmbeddingStore;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2025/3/26 13:33
 * @注释 ai配置类 java 助手
 */
@Configuration
public class AIConfJava {

    /***
     * java助手接口
     */
    public interface ChatAssistantJava {

        /***
         * 普通聊天 非隔离上下文
         * @param question
         * @return
         */
        String chat(String question);

        /***
         * 流式输出 非隔离上下文
         * @param question
         * @return
         */
        TokenStream streamChat(String question);

    }

    @Bean
    public EmbeddingStore embeddingStore() {
        DuckDBEmbeddingStore store = DuckDBEmbeddingStore.builder()
                .filePath("langchain4j-springBoot-demo/src/main/java/com/jerry/langchain4jspringbootdemo/store/db/javaInfo.db")
                .tableName("message")
                .build();
        return store;
    }

    @Bean
    public QwenEmbeddingModel qwenEmbeddingModel() {
        QwenEmbeddingModel embeddingModel = QwenEmbeddingModel.builder()
                .apiKey("sk-49255d73eb2c43a9a2a07c7f93afbd55")
                .build();
        return embeddingModel;
    }

    /***
     * 注入聊天服务  非隔离上下文
     * @param chatLanguageModel
     * @param streamingChatLanguageModel
     * @return
     */
    @Bean
    public ChatAssistantJava chatAssistantJava(ChatLanguageModel chatLanguageModel,
                                               StreamingChatLanguageModel streamingChatLanguageModel,
                                               EmbeddingStore embeddingStore,
                                               QwenEmbeddingModel qwenEmbeddingModel,
                                               ToolService toolService) {

        //使用简单的ChatMemory  - MessageWindowChatMemory来保存聊天记录
        MessageWindowChatMemory messageWindowChatMemory = MessageWindowChatMemory.builder()
                .maxMessages(10).build(); //最多保存10条记录

        //设置向量库
        EmbeddingStoreContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore) //向量库 duckdb
                .embeddingModel(qwenEmbeddingModel)//向量化模型
                .minScore(0.8) //匹配度80%以上
                .build();


        ChatAssistantJava build = AiServices.builder(ChatAssistantJava.class)
                .chatLanguageModel(chatLanguageModel)
                .streamingChatLanguageModel(streamingChatLanguageModel)
                .chatMemory(messageWindowChatMemory)
//                .tools(toolService)
                .contentRetriever(retriever)
                .build();

        return build;
    }


}
