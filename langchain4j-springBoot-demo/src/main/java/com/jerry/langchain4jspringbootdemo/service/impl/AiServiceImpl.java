package com.jerry.langchain4jspringbootdemo.service.impl;

import com.jerry.langchain4jspringbootdemo.annotation.SystemPrompt;
import com.jerry.langchain4jspringbootdemo.service.AiService;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.QwenStreamingChatModel;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2025/3/24 10:46
 * @注释
 */
@Service
public class AiServiceImpl implements AiService {

    @Resource
    private QwenChatModel qwenChatModel;


    @Resource
    private QwenStreamingChatModel qwenStreamingChatModel;


    /***
     * 提问 ai 方法 同步方法
     * @param question 问题
     * @return
     * @throws NoSuchMethodException
     */
    @Override
    public ChatResponse doAsk(String question) throws NoSuchMethodException {

        //获取注释中的内容
        Method doAsk = AiService.class.getMethod("doAsk", String.class);
        SystemPrompt annotation = doAsk.getAnnotation(SystemPrompt.class);
        if (annotation != null) {
            String s = annotation.systemPrompt();
            List<ChatMessage> messages = new ArrayList<>();
            messages.add(SystemMessage.from(s));
            messages.add(UserMessage.from(question));
            ChatResponse chat = qwenChatModel.chat(messages);
            return chat;
        } else {
            throw new NoSuchMethodException("no annotation");
        }
    }

    /***
     * 提问 ai 方法 sse方法
     * @param question
     * @return
     * @throws NoSuchMethodException
     */
    @Override
    public Flux<String> doAskSSE(String question)  {

        Flux<String> flux = Flux.create(emitter -> {

            qwenStreamingChatModel.chat(question, new StreamingChatResponseHandler() {
                @Override
                public void onPartialResponse(String partialResponse) {
                    emitter.next(partialResponse);
                }

                @Override
                public void onCompleteResponse(ChatResponse completeResponse) {
                    emitter.complete();
                }

                @Override
                public void onError(Throwable error) {
                    emitter.error(error);
                }
            });


        });
        return flux;
    }
}
