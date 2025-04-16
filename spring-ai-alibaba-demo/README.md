# Spring AI 阿里云集成示例

## 技术栈
- **核心框架**: Spring Boot 3.4.0
- **AI引擎**:
    - Spring AI 1.0.0-M6
    - Alibaba Cloud AI SDK 1.0.0-M6.1
- **模型服务**: 阿里云 DashScope
- **辅助工具**:
    - Micrometer 观测框架
    - Jackson 数据绑定
    - Tomcat 嵌入式容器

## 项目结构

```text
spring-ai-alibaba-demo/
├── src/main/
│   ├── java/
│   │   └── com/jerry/springaialibabademo/demos/
│   │       ├── web/            # 整体程序入口
│   │       │   └── BasicController.java  # 基础控制器 健康检查
│   │       │   └── DashScopeChatClientController.java  # DashScope 聊天控制器 
│   │       │   └── ChatMemoryController.java  # DashScope 记忆聊天控制器
│   └── resources/
│       └── application.yml       # 应用配置
├── pom.xml                       # Maven 依赖配置
└── README.md                     # 项目说明文档
```
## 功能介绍
### 基础功能
- **健康检查**: 提供 `/hello` 接口，用于检查服务是否正常运行。
### DashScope 聊天功能
- **聊天**: 提供 `/client/simple/chat` 和 `/client/stream/chat`接口，支持与 DashScope 模型进行对话。
### DashScope 记忆聊天功能
- **记忆聊天**: 提供 `/chat-memory/in-memory` 接口，支持与 DashScope 模型进行记忆对话。

## 快速开始
1. **环境准备**:
    - JDK 17 或更高版本
    - Maven 3.8.0 或更高版本
    - 阿里云账号
    - DashScope 模型服务
2. **配置**:
    - 在 `src/main/resources/application.yml` 中配置阿里云和 DashScope 的访问密钥，将sk-xxxxxxxx替换成你自己的百炼api-key。
3. **构建和运行**:
    - 使用 Maven 构建项目：`mvn clean package`
    - 运行应用：`java -jar target/spring-ai-alibaba-demo-1.0.0-SNAPSHOT.jar`
4. **访问**:
    - 使用 Maven 构建项目：`mvn clean package`
    - 运行应用：`java -jar target/spring-ai-alibaba-demo-1.0.0-SNAPSHOT.jar`
4. **访问**:
    - 访问 `URL_ADDRESS    
    - 访问 `http://localhost:8080/hello` 检查服务是否正常。