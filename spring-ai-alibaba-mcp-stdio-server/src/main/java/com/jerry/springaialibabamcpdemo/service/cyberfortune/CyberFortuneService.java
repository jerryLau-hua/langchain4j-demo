package com.jerry.springaialibabamcpdemo.service.cyberfortune;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2025/4/10 16:07
 * @注释 赛博算命
 * 星座运势 调用聚合数据 数据接口
 */
@Service
public class CyberFortuneService {

    private static final Logger logger = LoggerFactory.getLogger(CyberFortuneService.class);

    private static final String BASE_URL = "http://web.juhe.cn";

    private final RestClient restClient;

    public CyberFortuneService() {

        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("Accept", "application/json")
                .build();
    }

    /***
     * 获取星座运势
     * 星座运势查询接口
     * @param name 星座名称
     * @param type 运势类型 today,tomorrow,week,month,year
     * @return
     */
    @Tool(description = "获取指定星座指定时间的星座运势")
    public String getCyberFortune(
            @ToolParam(description = "星座") String name,
            @ToolParam(description = "时间") String type) throws IOException {
        if (name != null && type != null) {

//            // 创建文件输出流（追加模式）
//            PrintStream fileOut = new PrintStream(new FileOutputStream("getCyberFortune.log", true));
//
////            // 创建分流输出流
//            PrintStream tee = new PrintStream(fileOut) {
//                public void println(String x) {
//                    super.println(x);    // 写入文件
//                }
//            };
//
//            System.setOut(tee);

            name = new String(name.getBytes("GBK"), StandardCharsets.UTF_8);
            String typeNew = new String(type.getBytes("GBK"), StandardCharsets.UTF_8);

            String nameNew = name.substring(0, 2);
//            System.out.println("转码后的请求参数 前两位：[" + nameNew + "] [" + typeNew + "]\n");

            String inputType = getInputType(type);

            String apiKey = "xxxxxxxxxxxxxxxx"; // 替换为你的API Key

//            String body = "123456";
            String body =
                    restClient.get()
                            .uri("/constellation/getAll?key={key}&consName={consName}&type={inputType}",
                                    apiKey, nameNew + "座", inputType)
                            .retrieve().body(String.class);

//            System.out.println("星座运势查询接口返回的数据 ： " + body + "\n");

            String cyberData = getCyberData(nameNew, inputType, body);

//            System.out.println("星座运势字符串拼接后的结果 ： " + cyberData + "\n");


            return cyberData;
        } else {
            return "星座名称和运势类型不能为空";
        }

    }

    /***
     * 传入的星座类型转换为接口需要的名称
     * @param type
     * @return
     */
    public String getInputType(String type) {

        switch (type) {
            case "今天":
                type = "today";
                break;
            case "明天":
                type = "tomorrow";
                break;
            case "本周":
                type = "week";
                break;
            case "本月":
                type = "month";
                break;
            case "今年":
                type = "year";
                break;
            default:
                type = "today";
                break;
        }
        return type;
    }

    /***
     * 获取星座运势数据
     * @param type
     * @return
     * @throws IOException
     */
    public String getCyberData(String name,
                               String type,
                               String body) throws IOException {
        switch (type) {
            case "today":
            case "tomorrow":
                CyberData_Day cyberData_day = JSON.parseObject(body, CyberData_Day.class);

                String format = String.format("""
                                %s %s 星座运势如下:
                                ----------------
                                星座名称: %s
                                日期：%s
                                综合指数：%s
                                幸运色：%s
                                健康质数: %s
                                爱情指数: %s
                                财运指数: %s
                                幸运数字: %s
                                速配星座: %s
                                -----------
                                今日总结: %s       
                                """, name, type, cyberData_day.name(),
                        cyberData_day.datetime(),
                        cyberData_day.all(),
                        cyberData_day.color(),
                        cyberData_day.health(),
                        cyberData_day.love(),
                        cyberData_day.money(),
                        cyberData_day.number(),
                        cyberData_day.QFriend(),
                        cyberData_day.summary());
                return format;

            default:
                return "暂未实现";
        }
    }

    /***
     * 星座运势查询接口返回的数据模型 今天/明天
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CyberData_Day(
            @JsonProperty("date") int date,
            @JsonProperty("name") String name,
            @JsonProperty("QFriend") String QFriend,
            @JsonProperty("all") String all,
            @JsonProperty("color") String color,
            @JsonProperty("datetime") String datetime,
            @JsonProperty("health") String health,
            @JsonProperty("love") String love,
            @JsonProperty("money") String money,
            @JsonProperty("number") int number,
            @JsonProperty("summary") String summary,
            @JsonProperty("work") String work
    ) {
    }

    /***
     * 星座运势查询接口返回的数据模型 本周
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CyberData_Week(
            @JsonProperty("name") String name,
            @JsonProperty("date") String date,
            @JsonProperty("weekth") int weekth,
            @JsonProperty("health") String health,
            @JsonProperty("job") String job,
            @JsonProperty("love") String love,
            @JsonProperty("money") String money,
            @JsonProperty("work") String work
    ) {
    }

    /***
     * 星座运势查询接口返回的数据模型 本月
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CyberData_Month(
            @JsonProperty("date") String date,
            @JsonProperty("name") String name,
            @JsonProperty("all") String all,
            @JsonProperty("happyMagic") String happyMagic,
            @JsonProperty("health") String health,
            @JsonProperty("love") String love,
            @JsonProperty("money") String money,
            @JsonProperty("month") int month,
            @JsonProperty("work") String work

    ) {
    }

    /***
     * 星座运势查询接口返回的数据模型 今年
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CyberData_Year(
            @JsonProperty("name") String name,
            @JsonProperty("year") int year,
            @JsonProperty("career") List<String> career,
            @JsonProperty("date") String date,
            @JsonProperty("finance") List<String> finance,
            @JsonProperty("future") String future,
            @JsonProperty("health") List<String> health,
            @JsonProperty("love") List<String> love,
            @JsonProperty("luckyStone") String luckyStone,
            @JsonProperty("mima") CyberData_Year_Mima mima

    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record CyberData_Year_Mima(
                @JsonProperty("info") String info,
                @JsonProperty("text") List<String> text
        ) {
        }
    }

    public static void main(String[] args) throws Exception {
        CyberFortuneService cyberFortuneService = new CyberFortuneService();
        String response = cyberFortuneService.getCyberFortune("金牛座", "今天");
        System.out.println(response);
    }
}