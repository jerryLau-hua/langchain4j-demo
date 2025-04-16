# Spring AI Alibaba MCP SSE 服务

基于 Spring WebFlux 实现的 Server-Sent Events (SSE) 服务端，集成阿里云 Model Context Protocol (MCP) 的服务模块。

## 目录结构
```text
spring-ai-alibaba-mcp-sse-server/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/jerry/springaialibabamcpsseserver/  # SSE 服务核心代码
│   │   │       ├── config/       # WebFlux 配置
│   │   │       ├── BalanceService/      # SSE 服务实现
│   │   │       └── SpringAiAlibabaMcpSseServerApplication.java # 启动类
│   │   └── resources/
│   │       ├── application.yml    # 主配置文件
│   └── test/
│       └── java/                # 响应式测试
├── target/                      # 构建输出
├── pom.xml                      # Maven 依赖配置
└── README.md                    # 项目文档
```
## 配置
### 阿里云 MCP 配置
在 `application.yml` 中配置阿里云 MCP 的相关信息：
```yaml
spring:
  ai:
    dashscope:
      api-key: sk-xxxxxxxxxxxxxxx  # 替换实际API密钥
server:
  port: 9093  # SSE 服务专用端口
```

## 运行

1. 运行服务：
```text
mvn spring-boot:run -Dserver.port=9093
```