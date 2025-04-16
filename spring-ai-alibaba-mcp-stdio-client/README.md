# Spring AI Alibaba MCP 标准输入输出客户端
搭配 spring-ai-alibaba-mcp-stdio-server 使用。

基于 Spring AI 和阿里云 Model Context Protocol (MCP) 构建的标准输入输出客户端模块。

## 目录结构
```text
spring-ai-alibaba-mcp-stdio-client/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/jerry/springaialibabamcpstdioclient/  # 客户端核心代码
│   │   │    └── SpringAiAlibabaMcpStdioClientApplication.java # 启动类
│   │   └── resources/
│   │       ├── application.yml     # 主配置文件
│   │       └── mcp-servers-config/ # MCP服务端配置
│   └── test/
│       └── java/                  # 单元测试
├── target/                        # 构建输出
├── logs/                          # 运行时日志
├── pom.xml                        # Maven依赖配置
└── README.md                      # 项目文档
```
## 核心配置
application.yml
```yml
server:
  port: 8089
spring:
  ai:
    dashscope:
      api-key: sk-xxxxxxxxxxxxxxx  # 替换实际API密钥
    mcp:
      client:
        stdio:
          servers-configuration: classpath:/mcp-servers-config.json
```
mcp-servers-config.json
```json
{
    "mcpServers": {
        "aaa": {
            "command": "java",
            "args": [
                "-Dspring.ai.mcp.server.stdio=true",
                "-Dspring.main.web-application-type=none",
                "-Dlogging.pattern.console=",
                "-jar",
                "xxxxxxx-server-0.0.1-SNAPSHOT.jar"  # 替换实际server jar包路径
            ],
            "env": {}
        }
    }
}
```

## 运行
```shell
mvn spring-boot:run
```
## 访问
命令行访问

