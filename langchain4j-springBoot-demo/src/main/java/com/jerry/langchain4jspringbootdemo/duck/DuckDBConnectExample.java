package com.jerry.langchain4jspringbootdemo.duck;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2025/3/31 10:00
 * @注释 DuckDB 连接测试
 */
public class DuckDBConnectExample {
    public static void main(String[] args) {
        String url="jdbc:duckdb:/duck.db";
        try(Connection connection = DriverManager.getConnection(url)){
            if (connection.isValid(1000)) {
                System.out.println("DuckDB 连接成功");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
