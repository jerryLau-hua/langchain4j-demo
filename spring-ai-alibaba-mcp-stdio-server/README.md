# Spring AI Alibaba MCP 标准输入输出服务

基于 Spring AI 和阿里云 Model Context Protocol (MCP) 构建的标准输入输出服务模块。

## 功能特性

- ✅ 集成阿里云 DashScope 大模型服务
- ✅ 标准输入输出 mcp服务器 交互
- ✅ 支持请求追踪和日志监控
- ✅ 完善的访问日志记录

## 目录结构

```text
spring-ai-alibaba-mcp-stdio-server/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/jerry/springaialibabamcpdemo/  # 核心业务代码目录
│   │   │       ├── config/    # 配置类
│   │   │       ├── service/  # 服务层 基础mcp服务提供
│   │   │       ├── controller/  # 健康检查 可以忽略
│   │   │       └── ClientStdioApplication.java  # 启动类
│   │   └── resources/
│   │       ├── application.yml       # 主配置文件
│   │       └── log4j.properties   # 日志配置文件
│   └── test/
│       └── java/            # 单元测试
├── target/                  # 构建输出目录
├── logs/                    # 运行时日志（需手动创建）
├── pom.xml                  # Maven依赖管理
└── README.md                # 项目说明文档
```
## 快速开始

### 前置条件
- JDK 17+
- Maven 3.6+
- 阿里云百炼 API Key（配置于`application.yml`）

### 配置说明

```yaml
spring:
  ai:
    dashscope:
      api-key: sk-xxxxxxxxxxxxxxx  # 替换为实际API密钥
    mcp:
      server:
        name: my-server
        version: 0.0.1
```
### 运行项目
1. 编译项目：`mvn clean package`
2. 运行应用：`java -jar target/spring-ai-alibaba-mcp-stdio-server-0.0.1-SNAPSHOT.jar`