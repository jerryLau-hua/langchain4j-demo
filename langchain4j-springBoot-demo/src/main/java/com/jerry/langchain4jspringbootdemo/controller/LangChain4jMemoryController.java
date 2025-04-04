package com.jerry.langchain4jspringbootdemo.controller;

import com.jerry.langchain4jspringbootdemo.config.AIConf;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.TokenStream;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Date;


/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2025/3/20 10:54
 * @注释 记忆化测试 controller
 */

@RestController
@RequestMapping("/ai/memoryChart")
public class LangChain4jMemoryController {

    @Resource
    private QwenChatModel qwenChatModel;


    private int count = 0;

    /***
     * 普通调用
     * @param question
     * @return
     */
    @GetMapping("/doAsk")
    public String langChain4j(@RequestParam String question) {
//        System.out.println("进入doAsk方法,第" + count + "次调用");
        count++;
        return "doAsk方法,第" + count + "次调用; \n 模型回答：" + qwenChatModel.chat(question);
    }


    /***
     * 记忆调用 1
     * 自己记录上下文
     * @param
     * @return
     */
    @GetMapping("/doAskWithMemory1")
    public String langChain4j_memory() {
        UserMessage userMessage = new UserMessage("halo 我是 jerry");
        ChatResponse chat1Resp = qwenChatModel.chat(userMessage);
        AiMessage aiMessage = chat1Resp.aiMessage();

        System.out.println("第一次调用返回：" + aiMessage.text());

        System.out.println("--------------------");

        String question2 = "我是谁？";

        ChatResponse chat2Resp = qwenChatModel.chat(userMessage, aiMessage, UserMessage.from(question2));

        System.out.println("第二次调用返回：" + chat2Resp.aiMessage().text());


        return "langChain4j_memory方法结束";
    }


    @Resource
    private AIConf.ChatAssistant chatAssistant;


    /***
     * 记忆调用 2
     * 使用langchain4j 自带的记忆化工具进行上下文记忆
     * @param
     * @return
     */
    @GetMapping("/doAskWithMemory2")
    public String langChain4j_memory2(@RequestParam(defaultValue = "halo 我是jerry") String question) {
        String chat = chatAssistant.chat(question,new Date());
        return chat;
    }


    @GetMapping(value = "/doAskWithMemoryStream", produces = "text/stream;charset=UTF-8")
    public Flux<String> langChain4j_memoryStream(@RequestParam(defaultValue = "halo 我是jerry") String question) {
        TokenStream tokenStream = chatAssistant.streamChat(question);

        return Flux.create(sink -> {
            tokenStream.onPartialResponse(c -> sink.next(c))
                    .onCompleteResponse(s -> sink.complete())
                    .onError(sink::error)
                    .start();
        });
    }


//    @GetMapping(value = "/doAskWithMemoryStreamUnique", produces = "text/stream;charset=UTF-8")
//    public Flux<String> langChain4j_memoryStreamUnique(@RequestParam(defaultValue = "halo 我是jerry") String question, @RequestParam int id) {
//        TokenStream tokenStream = chatAssistant.streamChat(id,question);
//
//        return Flux.create(sink -> {
//            tokenStream.onPartialResponse(c -> sink.next(c))
//                    .onCompleteResponse(s -> sink.complete())
//                    .onError(sink::error)
//                    .start();
//        });
//    }


}
