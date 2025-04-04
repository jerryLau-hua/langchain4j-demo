package com.jerry.langchain4jspringbootdemo.example.embedding;

import dev.langchain4j.community.model.dashscope.QwenEmbeddingModel;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.output.Response;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2025/3/31 8:35
 * @注释 简单的向量化示例
 */
public class EmBeddingExample {

    public static void main(String[] args) {

        QwenEmbeddingModel build = QwenEmbeddingModel.builder()
                .apiKey("sk-xxxxxxx")
                .build();

        String text = "我是一个测试文本";
        Response<Embedding> embed = build.embed(text);

        System.out.println(embed.content().toString());
        System.out.println(embed.content().vectorAsList().size());

    }
}
