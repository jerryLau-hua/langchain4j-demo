package com.jerry.langchain4jspringbootdemo.store;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static dev.langchain4j.data.message.ChatMessageDeserializer.messagesFromJson;
import static dev.langchain4j.data.message.ChatMessageSerializer.messagesToJson;
import static org.mapdb.Serializer.INTEGER;
import static org.mapdb.Serializer.STRING;

/***
 * @version 1.0
 * @Author jerryLau
 * @Date 2025/3/26 13:33
 * @注释 持久化内存 mapdb 实现
 */
public class PersistentChatMemoryStore implements ChatMemoryStore {


    // 创建数据库
    DB db = DBMaker.fileDB("langchain4j-springBoot-demo/src/main/java/com/jerry/langchain4jspringbootdemo/store/db/chat-memory.db")
            .transactionEnable()
            .make();
    private final Map<Integer, String> map = db.hashMap("messages", INTEGER, STRING).createOrOpen();

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        String json = map.get((int) memoryId);
        return messagesFromJson(json);
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        String json = messagesToJson(messages);
        map.put((int) memoryId, json);
        db.commit();
    }

    @Override
    public void deleteMessages(Object memoryId) {
        map.remove((int) memoryId);
        db.commit();
    }
}