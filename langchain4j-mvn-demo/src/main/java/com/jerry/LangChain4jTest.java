package com.jerry;

import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;

/**
 * Hello world!
 */
public class LangChain4jTest {
    public static void main(String[] args) {
        String s = ollamaDemo();
        System.out.println(s);
    }


    /***
     * 使用条件：默认体验的使用条件，但是要在langchain4j 1.0.0-beta1中可以调用
     * 1.0.0-beta2中已经修复，会抛出运行时异常
     * @return
     */
    public static String defaultDemo() {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .build();
        String hello = model.chat("你是谁？");
//        System.out.println(hello);
        return hello;
    }


    /***
     * 使用条件：需要使用deepseek的apiKey
     * @return
     */
    public static String deepSeekDemo() {
        String apiKey = "your deepseekapi key";
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("https://api.deepseek.com/v1")
                .apiKey(apiKey)
                .modelName("deepseek-chat")
                .build();

        String hello = model.chat("你是谁？");
        return hello;
    }

    /***
     * 使用条件：需要使用dashscope的apiKey
     * @return
     */
    public static String QwqDemo() {
        String apiKey = "your dashscope key";
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .apiKey(apiKey)
                .modelName("qwen-max")
                .build();

        String hello = model.chat("你是谁？");
        return hello;
    }

    /***
     * 使用条件：需要使用dashscope的apiKey
     * you can also use the following code to use the deepseek model
     * @return
     */
    public static String Qwq_deepseekDemo() {
        String apiKey = "your dashscope key";
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .apiKey(apiKey)
                .modelName("deepseek-r1")
                .build();

        String hello = model.chat("你是谁？");
        return hello;
    }


    /***
     * 使用条件：需要使用dashscope的apiKey
     * 文生图
     * @return
     */
    public static String Qwq_WordToPicDemo() {
        String apiKey = "your dashscope key";
        WanxImageModel build = WanxImageModel.builder()
                .apiKey(apiKey)
                .modelName("wanx2.1-t2i-plus")
                .build();

        Response<Image> response = build.generate("草地");
        return response.content().url().toString();
    }


    /***
     * 使用条件：自己部署ollama本地大模型
     * 文生图
     * @return
     */
    public static String ollamaDemo() {

        OllamaChatModel build = OllamaChatModel.builder()
                .baseUrl("http://XXXX:11434")
                .modelName("qwen2.5-coder:1.5b")
                .build();

        String resp = build.chat("你好 你是谁");
        return resp;
    }

}
