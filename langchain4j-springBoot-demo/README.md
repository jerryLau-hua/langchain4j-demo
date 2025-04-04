# LangChain4j Spring Boot 集成示例

## 项目概述
基于Spring Boot的LangChain4j集成示例，实现以下功能：
- 🤖 多模型支持（阿里通义、DeepSeek、Ollama等）
- 📚 RAG（检索增强生成）实现
- 🛠️ 工具函数调用
- 💬 流式对话接口
- 🧠 持久化聊天记忆

## 技术栈
- **框架**: Spring Boot 3.1.5
- **AI核心**: LangChain4j 1.0.0-beta2
- **向量存储**: DuckDB
- **模型服务**:
    - 阿里云 DashScope
    - DeepSeek
    - Ollama（本地部署）

## 快速开始

### 环境要求
- JDK 17+
- Maven 3.8+

### 启动步骤
1. 克隆仓库
```bash
git clone https://github.com/your-repo/langchain4j-demo.git
```
2. 安装依赖
mvn clean install
3. 配置API密钥
- 在application.properties中配置API密钥
- 在QwenEmbeddingModel.builder()
  .apiKey("sk-xxxxx") // 替换真实API Key
  .build();
4. 启动应用
```bash
 mvn spring-boot:run 
```

## 接口文档



| 端点                | 方法 | 说明                                     |
| ------------------- | ---- | ---------------------------------------- |
| /ai/simple/doAsk    | GET  | 基础问答接口                             |
| /ai/simple/doAskSSE | GET  | 流式输出/记忆化接口（text/event-stream） |
| /ai/rag/doAsk       | GET  | RAG增强问答接口                          |

