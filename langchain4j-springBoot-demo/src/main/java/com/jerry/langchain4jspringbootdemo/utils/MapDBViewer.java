package com.jerry.langchain4jspringbootdemo.utils;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2025/3/26 17:12
 * @注释 读取mapdb文件
 */



public class MapDBViewer {
    public static void main(String[] args) {
        // 打开数据库文件（只读模式，避免意外修改）
        DB db = DBMaker
                .fileDB("langchain4j-springBoot-demo/src/main/java/com/jerry/langchain4jspringbootdemo/store/db/chat-memory.db")
                .readOnly()
                .make();

        // 获取存储的Map（假设数据存储在名为"myMap"的HTreeMap中）
        HTreeMap<Integer, String> map = db.hashMap("messages")
                .keySerializer(org.mapdb.Serializer.INTEGER)
                .valueSerializer(org.mapdb.Serializer.STRING)
                .createOrOpen();


        // 遍历并打印所有键值对
        map.forEach((key, value) -> System.out.println("Key: " + key.toString() + ", Value: " + value));

        // 关闭数据库
        db.close();
    }
}