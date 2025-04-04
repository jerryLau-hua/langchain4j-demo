package com.jerry.langchain4jspringbootdemo.service;

import com.jerry.langchain4jspringbootdemo.annotation.SystemPrompt;
import dev.langchain4j.model.chat.response.ChatResponse;
import reactor.core.publisher.Flux;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2025/3/24 8:41
 * @注释 aiService 接口
 */

public interface AiService {

    /***
     * 提问 ai 方法 同步方法
     * @param question
     * @return
     * @throws NoSuchMethodException
     */
    @SystemPrompt()
    ChatResponse doAsk(String question) throws NoSuchMethodException;


    /***
     * 提问 ai 方法 sse方法
     * @param question
     * @return
     * @throws NoSuchMethodException
     */

    Flux<String> doAskSSE(String question) ;
}
