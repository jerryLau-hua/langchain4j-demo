package com.jerry.langchain4jspringbootdemo.example.embedding;

import dev.langchain4j.community.model.dashscope.QwenEmbeddingModel;
import dev.langchain4j.community.store.embedding.duckdb.DuckDBEmbeddingStore;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2025/3/31 8:35
 * @注释 向量化示例
 */
public class EmBeddingExample2 {

    public static void main(String[] args) {

        DuckDBEmbeddingStore store = DuckDBEmbeddingStore.builder()
                .filePath("langchain4j-springBoot-demo/src/main/java/com/jerry/langchain4jspringbootdemo/store/db/aiAssitant.db")
                .tableName("message")
                .build();


        QwenEmbeddingModel embeddingModel = QwenEmbeddingModel.builder()
                .apiKey("sk-xxxxxxxx")
//                .modelName("text-embedding-v3")
//                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();

        TextSegment segment = TextSegment.from("""
                线上预约：
                1. 选择预约时间段：在预约页面，您可以选择适合您的时间段进行预约。
                2. 选择服务项目：根据您的需求，选择适合您的服务项目。
                3. 填写预约信息：在预约页面，您需要填写您的姓名、联系方式、预约日期和预约时间等信息。
                4. 提交预约：在填写完预约信息后，您需要点击提交按钮，提交您的预约信息。
                5. 支付预约：在提交预约后，您需要支付预约费用15元人民币。
                6. 预约成功：在支付预约成功后，您的预约将被成功提交。
                """);

        TextSegment segment2 = TextSegment.from("""
                线上解除预约：
                1. 选择预约记录：在预约记录页面，您可以选择需要解除预约的记录。
                2. 确认取消：在确认取消页面，您需要确认是否取消预约，最晚取消时间在活动开始时间前12小时。
                3. 在活动开始时间前12小时取消不收取手续费，否则按照预约费用的12%收取手续费。
                4. 取消成功：在确认取消后，您的预约将被成功取消，费用将于7个工作日内原路返回至您的支付账户。
                """);

        Embedding content = embeddingModel.embed(segment).content();
        Embedding content2 = embeddingModel.embed(segment2).content();

        store.add(content, segment);
        store.add(content2, segment2);

        var queryEmbedding = embeddingModel.embed("取消预约").content();
        var request = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .maxResults(1)
                .build();

        var relevant = store.search(request);
        EmbeddingMatch<TextSegment> embeddingMatch = relevant.matches().get(0);

        // Show results
        System.out.println(embeddingMatch.score());
        System.out.println(embeddingMatch.embedded().text());

    }


}
