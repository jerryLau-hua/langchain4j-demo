package com.jerry.langchain4jspringbootdemo.controller;

import com.jerry.langchain4jspringbootdemo.config.AIConf;
import com.jerry.langchain4jspringbootdemo.config.AIConfJava;
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
 * rag 例子 接口
 * @注释
 */

@RestController
@RequestMapping("/ai/rag")
public class LangChain4jRagController {

    @Resource
    private AIConfJava.ChatAssistantJava chatAssistantJava;

    @GetMapping("/doAsk")
    public String langChain4j(@RequestParam(defaultValue = "帮我理解一下 代理模式") String question) {
        return chatAssistantJava.chat(question);
    }



}
