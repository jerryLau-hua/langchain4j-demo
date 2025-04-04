package com.jerry.langchain4jspringbootdemo.example.rag;

import dev.langchain4j.community.model.dashscope.QwenEmbeddingModel;
import dev.langchain4j.community.store.embedding.duckdb.DuckDBEmbeddingStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByRegexSplitter;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2025/3/31 15:55
 * @注释 文本向量化 例子
 */

public class RAGExample {
    public static void main(String[] args) throws IOException {
//        saveMessage();
        getMessage();

    }


    public static void saveMessage() {
        // 解析PDF文件
        InputStream inputStream = RAGExample.class.getClassLoader().getResourceAsStream("java.pdf");
        DocumentParser parser = new ApachePdfBoxDocumentParser();
        Document document = parser.parse(inputStream);
//        System.out.println(document.text());

        //分割文本
        // 1. 按单词无符号分割 DocumentByCharacterSplitter

        //2. 按照句子分割 DocumentByLineSplitter

        //3. 按照段落分割 DocumentByParagraphSplitter

        //4. 按照正则 DocumentByRegexSplitter

        //5. 按照句子  DocumentBySentenceSplitter


        // 预处理：统一换行符并清理空行
        String preprocessedText = document.text()
                .replace("\r\n", "\n")          // 统一换行符
                .replaceAll("(\\n\\s*)+", "\n") // 合并连续空行
                .trim();

        // 构造正则分割器（演示增强版正则）
        DocumentByRegexSplitter splitter = new DocumentByRegexSplitter(
                "(\\n\\d+、)",     // 核心匹配模式
                "\n",              // 拼接保留换行
                220,               // 根据平均题目+答案长度调整
                20                 // 小幅度重叠避免切断句子
        );

        // 执行分割
        String[] rawSegments = splitter.split(preprocessedText);

        // 合并过小片段（如独立存在的答案行）
//        List<TextSegment> merged = mergeSmallSegments(rawSegments, 100);
        List<TextSegment> merged = new ArrayList<>();
        for (int i = 0; i < rawSegments.length; i++) {
            merged.add(TextSegment.from(rawSegments[i]));
        }

        QwenEmbeddingModel embeddingModel = QwenEmbeddingModel.builder()
                .apiKey("sk-xxxxxxxx")
                .build();

        List<Embedding> content = embeddingModel.embedAll(merged).content();

        DuckDBEmbeddingStore store = DuckDBEmbeddingStore.builder()
                .filePath("langchain4j-springBoot-demo/src/main/java/com/jerry/langchain4jspringbootdemo/store/db/javaInfo.db")
                .tableName("message")
                .build();
        store.addAll(content, merged);

        // 3.生成向量
        var queryEmbedding = embeddingModel.embed("java == 和 equals的区别").content();
        var request = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .maxResults(1)
                .build();


        var relevant = store.search(request);
        EmbeddingMatch<TextSegment> embeddingMatch = relevant.matches().get(0);

        // Show results
        System.out.println(embeddingMatch.score());
        System.out.println(embeddingMatch.embedded().text());

//系统打印输出
//        for (int i = 0; i < rawSegments.length; i++) {
//            System.out.println("====== Segment ======");
//            System.out.println(rawSegments[i]);
//        }

//系统打印输出
//        merged.forEach(seg -> {
//            System.out.println("====== Segment ======");
//            System.out.println(seg.text());
//        });
    }


    public static List<TextSegment> mergeSmallSegments(String[] segments, int minSize) {
        List<TextSegment> merged = new ArrayList<>();
        if (segments.length == 0) return merged;

        TextSegment current = TextSegment.from(segments[0]);

        for (int i = 1; i < segments.length; i++) {
            TextSegment next = TextSegment.from(segments[i]);

            // 如果当前段或下一段过小，则尝试合并
            if (current.text().length() < minSize || next.text().length() < minSize) {
                String mergedText = current.text() + "\n" + next.text();
                current = TextSegment.from(mergedText, current.metadata());
            } else {
                merged.add(current);
                current = next;
            }
        }

        // 添加最后一个段
        merged.add(current);
        return merged;
    }

    public static void getMessage() {
        QwenEmbeddingModel embeddingModel = QwenEmbeddingModel.builder()
                .apiKey("sk-xxxxxxx")
                .build();

        DuckDBEmbeddingStore store = DuckDBEmbeddingStore.builder()
                .filePath("langchain4j-springBoot-demo/src/main/java/com/jerry/langchain4jspringbootdemo/store/db/javaInfo.db")
                .tableName("message")
                .build();
        var queryEmbedding = embeddingModel.embed("帮我理解一下java 的 代理模式").content();
        var request = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
//                .maxResults(1)
                .minScore(0.8)
                .build();


        var relevant = store.search(request);
        List<EmbeddingMatch<TextSegment>> matches = relevant.matches();
        matches.forEach(embeddingMatch -> {
            System.out.println(embeddingMatch.score());
            System.out.println(embeddingMatch.embedded().text());
        });
    }
}
