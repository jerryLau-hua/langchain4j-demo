package com.jerry.langchain4jspringbootdemo;

import org.junit.jupiter.api.Test;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Langchain4jSpringBootDemoApplicationTests {

    @Test
    public void testMapDb() throws IOException {
        // 清理旧文件（如果存在）
        Path dbPath = Paths.get("file.db");
        if (Files.exists(dbPath)) {
            Files.delete(dbPath);
        }

        // 创建禁用事务的数据库
        DB db = DBMaker.fileDB("file.db")
                .transactionEnable()
                .make();

        String welcomeMessageKey = "Welcome Message";
        String welcomeMessageString = "Hello Baeldung!";
        HTreeMap myMap = db.hashMap("myMap").createOrOpen();
        myMap.put(welcomeMessageKey, welcomeMessageString);
        String welcomeMessageFromDB = (String) myMap.get(welcomeMessageKey);
        assertEquals(welcomeMessageString, welcomeMessageFromDB);
        db.close();
    }

}
