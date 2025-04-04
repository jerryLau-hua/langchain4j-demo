package com.jerry.langchain4jspringbootdemo.core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;

public class BaiduWebScraper {

    public static void search(String query) {
        try {
            String encodedQuery = java.net.URLEncoder.encode(query, "UTF-8");
            String url = "https://www.baidu.com/s?wd=" + encodedQuery;

            // 发送 HTTP 请求（模拟浏览器）
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .get();

            // 解析搜索结果
            Elements results = doc.select("div.result.c-container");
            for (Element result : results) {
                String title = result.select("h3.t").text();
                String link = result.select("a").attr("href");
                String snippet = result.select("div.c-abstract").text();

                System.out.println("标题: " + title);
                System.out.println("链接: " + link);
                System.out.println("摘要: " + snippet);
                System.out.println("---------------------");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        search("Java编程教程");
    }
}