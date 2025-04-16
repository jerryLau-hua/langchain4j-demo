# java-ai  Spring Boot 集成示例

## 项目概述
基于Spring Boot的LangChain4j集成示例，实现以下功能：
- 🤖 多模型支持（阿里通义、DeepSeek、Ollama等）
- 📚 RAG（检索增强生成）实现
- 🛠️ 工具函数调用
- 💬 流式对话接口
- 🧠 持久化聊天记忆
参照：langchain4j-mvn-demo
  langchain4j-springBoot-demo

基于Spring-ai-alibaba ，实现以下功能：
- 🤖 简单对话实现
- 📚 基于mcp 实现 标准输入输出服务端与客户端
- 🛠️ 基于mcp 实现 流式调用服务端

## 技术栈
### 基于Spring Boot的LangChain4j集成技术栈
- **框架**: Spring Boot 3.1.5
- **AI核心**: LangChain4j 1.0.0-beta2
- **向量存储**: DuckDB
- **模型服务**:
    - 阿里云 DashScope
    - DeepSeek
    - Ollama（本地部署）

### 基于Spring-ai-alibaba 的技术栈
- **框架**: Spring Boot 3.4.3
- **AI核心**:   spring-ai-alibaba 1.0.0-M6.1
- **模型服务**:
    - 阿里云 DashScope

## 快速开始

### 环境要求
- JDK 17+
- Maven 3.8+

### 启动步骤
1. 克隆仓库
```bash
git clone https://github.com/jerryLau-hua/langchain4j-demo.git
```
2. 参照每个模块的README.md进行配置和启动


