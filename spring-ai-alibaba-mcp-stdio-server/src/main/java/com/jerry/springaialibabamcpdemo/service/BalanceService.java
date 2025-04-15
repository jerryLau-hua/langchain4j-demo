package com.jerry.springaialibabamcpdemo.service;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2025/4/11 17:19
 * @注释
 */

@Service
public class BalanceService {
    private static final String BASE_URL = "https://api.deepseek.com";

    private final RestClient restClient;

    public BalanceService() {

        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Authorization", "Bearer sk-xxxxxxxxxxxxxxxx")
                .build();
    }


    @Tool(description = "获取我的深度求索账户余额")
    public String getBalance() {

        try {
            JSONObject body = restClient.get()
                    .uri("/user/balance")
                    .retrieve().body(JSONObject.class);

            return body.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "获取余额失败";
        }
    }


    public static void main(String[] args) {
        BalanceService balanceService = new BalanceService();
        String balance = balanceService.getBalance();
        System.out.println(balance);
    }

}
