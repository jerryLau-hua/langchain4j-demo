package com.jerry.springaialibabamcpdemo.service.aboutWeather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2025/4/10 14:27
 * @注释 天气服务
 * <p>
 * * 利用OpenMeteo的免费天气API提供空气质量服务
 * * 该API无需API密钥，可以直接使用
 */
@Service
public class AirQualityService {
    // OpenMeteo免费天气API基础URL
    private static final String BASE_URL = "https://air-quality-api.open-meteo.com/v1";


    private final RestClient restClient;

    public AirQualityService() {
        this.restClient = RestClient.builder().baseUrl(BASE_URL).defaultHeader("Accept", "application/json").defaultHeader("User-Agent", "OpenMeteoClient/1.0").build();
    }


    /**
     * 获取指定位置的空气质量信息
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return 空气质量信息
     */
    @Tool(description = "获取指定位置的空气质量信息")
    public String getAirQuality(@ToolParam(description = "纬度") double latitude, @ToolParam(description = "经度") double longitude) {

        try {
            // 从空气数据中获取基本信息
            var airData = restClient.get().uri("/air-quality?latitude=52.52&longitude=13.41&hourly=pm10,pm2_5&current=european_aqi,us_aqi,pm10,pm2_5,carbon_monoxide,sulphur_dioxide,ozone,nitrogen_dioxide&timezone=Asia/Shanghai&forecast_hours=24&temporal_resolution=hourly_3", latitude, longitude).retrieve().body(AirData.class);


            int europeanAqi = airData.current().europeanAqi();
            int usAqi = airData.current().usAqi();


            // 根据AQI评估空气质量等级
            String europeanAqiLevel = getAqiLevel(europeanAqi);
            String usAqiLevel = getUsAqiLevel(usAqi);
            StringBuilder sb = new StringBuilder();

            String format = String.format("""
                    空气质量信息:

                    位置: 纬度 %.4f, 经度 %.4f
                    欧洲空气质量指数: %d (%s)
                    美国空气质量指数: %d (%s)
                    PM10: %.1f μg/m³
                    PM2.5: %.1f μg/m³
                    一氧化碳(CO): %.1f μg/m³
                    二氧化氮(NO2): %.1f μg/m³
                    二氧化硫(SO2): %.1f μg/m³
                    臭氧(O3): %.1f μg/m³

                    数据更新时间: %s
                    """, latitude, longitude, europeanAqi, europeanAqiLevel, usAqi, usAqiLevel, airData.current().pm10, airData.current().pm2_5, airData.current().co, airData.current().no2, airData.current().so2, airData.current().o3, airData.current().time());
            sb.append(format);

            //解析forcast
            sb.append("未来24H空气中的相关成分变化情况:\n");

            if (airData.hourly() != null) {
                AirData.HourlyForecast hourly = airData.hourly();
                for (int i = 0; i < hourly.time.size(); i++) {
                    String date = hourly.time().get(i);
                    Double pm10 = hourly.pm10().get(i);
                    Double pm25 = hourly.pm2_5().get(i);
                    // 格式化日期
                    LocalDateTime localDate = LocalDateTime.parse(date);
                    String formattedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                    sb.append(String.format("""
                            %s:
                            PM10: %.1f μg/m³
                            PM2.5: %.1f μg/m³

                            """, formattedDate, pm10, pm25));
                }
            }

            return sb.toString();


        } catch (Exception e) {
//            e.printStackTrace();
            // 如果获取基本天气数据失败，返回完全模拟的数据
            return String.format("""
                    空气质量信息获取失败:

                    位置: 纬度 %.4f, 经度 %.4f
                    请稍后重试~。
                    """, latitude, longitude);
        }
    }


    /**
     * 获取欧洲空气质量指数等级
     */
    private String getAqiLevel(Integer aqi) {
        if (aqi == null) return "未知";

        if (aqi <= 20) return "优";
        else if (aqi <= 40) return "良";
        else if (aqi <= 60) return "中等";
        else if (aqi <= 80) return "较差";
        else if (aqi <= 100) return "差";
        else return "极差";
    }

    /**
     * 获取美国空气质量指数等级
     */
    private String getUsAqiLevel(Integer aqi) {
        if (aqi == null) return "未知";

        if (aqi <= 50) return "优";
        else if (aqi <= 100) return "中等";
        else if (aqi <= 150) return "对敏感人群不健康";
        else if (aqi <= 200) return "不健康";
        else if (aqi <= 300) return "非常不健康";
        else return "危险";
    }


    // OpenMeteo天气数据模型
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record AirData(@JsonProperty("latitude") Double latitude, @JsonProperty("longitude") Double longitude,
                          @JsonProperty("timezone") String timezone, @JsonProperty("current") CurrentAir current,
                          @JsonProperty("current_units") CurrentUnits currentUnits,
                          @JsonProperty("hourly") HourlyForecast hourly) {

        @JsonIgnoreProperties(ignoreUnknown = true)
        public record CurrentAir(@JsonProperty("time") String time, @JsonProperty("interval") int interval,
                                 @JsonProperty("european_aqi") int europeanAqi, @JsonProperty("us_aqi") int usAqi,
                                 @JsonProperty("pm10") double pm10, @JsonProperty("pm2_5") double pm2_5,
                                 @JsonProperty("carbon_monoxide") double co,
                                 @JsonProperty("sulphur_dioxide") double so2, @JsonProperty("ozone") double o3,
                                 @JsonProperty("nitrogen_dioxide") double no2) {
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public record CurrentUnits(@JsonProperty("time") String timeUnit, @JsonProperty("interval") String intervalUnit,
                                   @JsonProperty("european_aqi") String europeanAqiUnit,
                                   @JsonProperty("us_aqi") String usAqiUnit, @JsonProperty("pm10") String pm10Unit,
                                   @JsonProperty("pm2_5") String pm2_5Unit,
                                   @JsonProperty("carbon_monoxide") String coUnit,
                                   @JsonProperty("sulphur_dioxide") String so2Unit,
                                   @JsonProperty("ozone") String o3Unit,
                                   @JsonProperty("nitrogen_dioxide") String no2Unit) {
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public record HourlyForecast(@JsonProperty("time") List<String> time, @JsonProperty("pm10") List<Double> pm10,
                                     @JsonProperty("pm2_5") List<Double> pm2_5) {
        }
    }


    public static void main(String[] args) {
        AirQualityService client = new AirQualityService();
        // 北京空气质量（模拟数据）
        System.out.println(client.getAirQuality(39.9042, 116.4074));
    }

}
