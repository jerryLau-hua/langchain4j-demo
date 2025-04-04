package com.jerry.langchain4jspringbootdemo.service;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2025/3/27 13:57
 * @注释 Function-Call 工具服务类   用于提供工具服务
 */
@Service
public class ToolService {

    @Resource
    private MailService mailService;

    @Resource
    private QwenChatModel qwenChatModel;

    @Tool("打个招呼")
    public String welcome(@P("姓名") String name) {
        System.out.println("输入：" + name);
        return "Welcome " + name + " to Function-Call!";
    }

    @Tool("Sums 2 given numbers")
    double sum(double a, double b) {
        System.out.println("输入：" + a + " " + b);
        return a + b;
    }

    @Tool("Returns a square root of a given number")
    double squareRoot(double x) {
        return Math.sqrt(x);
    }

    @Tool("Send the short recent news in any field to the user.")
    public String generateAI(@P("mailAddr") String mailAddress, @P("filed") String filed) throws MessagingException {
        System.out.println("输入：" + mailAddress);
        System.out.println("输入：" + filed);

        UserMessage userMessage = new UserMessage("generate the short recent news in " + filed + " field");
        ChatResponse chat1Resp = qwenChatModel.chat(userMessage);
        String text = chat1Resp.aiMessage().text();

        boolean b = mailService.sendEmail(
                "xxxxxx@qq.com", "xxxxxxx", mailAddress
                , "Short recent news in " + filed + " filed", text);
//mailAddress
        if (b) {
            System.out.println("邮件发送成功！");
        }
        return "邮件发送成功！";
    }

//    My friend wants to know recent news in the AI field. Send the short summary to friend@email.com,

}
