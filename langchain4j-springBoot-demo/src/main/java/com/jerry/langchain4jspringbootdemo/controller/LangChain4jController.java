package com.jerry.langchain4jspringbootdemo.controller;

import com.jerry.langchain4jspringbootdemo.service.AiService;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2025/3/20 10:54
 * 简单的对话问答请求controller
 * @注释
 */

@RestController
@RequestMapping("/ai/simple")
public class LangChain4jController {

    @Resource
    private QwenChatModel qwenChatModel;


    @Resource
    private AiService aiService;

    @GetMapping("/doAsk")
    public String langChain4j(@RequestParam(defaultValue = "你是谁？") String question) {
        return qwenChatModel.chat(question);
    }


    @GetMapping("/doAsk2")
    public String langChain4j2(@RequestParam(defaultValue = "你是谁？") String question) throws NoSuchMethodException {
        System.out.println("进入doAsk2方法");
        ChatResponse aiMessageResponse = aiService.doAsk(question);
        System.out.println(aiMessageResponse);
        return aiMessageResponse.aiMessage().text();

    }


    @RequestMapping(value = "/doAskSSE", produces = "text/stream;charset=UTF-8")
    public Flux<String> langChain4j2SSE(@RequestParam(defaultValue = "你是谁？") String question) throws NoSuchMethodException {
        System.out.println("进入doAskSEE方法");
        return aiService.doAskSSE(question);

    }


}
