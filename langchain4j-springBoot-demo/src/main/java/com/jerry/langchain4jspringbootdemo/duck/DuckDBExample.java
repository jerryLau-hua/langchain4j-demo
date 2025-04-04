package com.jerry.langchain4jspringbootdemo.duck;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2025/3/31 10:00
 * @注释 DuckDB 操作测试
 */
public class DuckDBExample {
    public static void main(String[] args) throws SQLException {
        queryTable();
    }

    public static void createTable() throws SQLException {
        String url = "jdbc:duckdb:langchain4j-springBoot-demo/src/main/java/com/jerry/langchain4jspringbootdemo/store/db/duck.db";
        try (Connection connection = DriverManager.getConnection(url)) {
            if (connection.isValid(1000)) {
                System.out.println("DuckDB 连接成功");
                connection.createStatement().execute("CREATE TABLE IF NOT EXISTS test (id INTEGER, name VARCHAR)");

                System.out.println("创建表成功");

                int rowsInserted = connection.createStatement()
                        .executeUpdate("INSERT INTO test VALUES (1, 'Jerry')");
                System.out.println("插入数据行数: " + rowsInserted);

                if (rowsInserted > 0) {
                    System.out.println("插入数据成功");

                } else {
                    System.out.println("插入数据失败");
                }


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void queryTable() throws SQLException {
        String url = "jdbc:duckdb:langchain4j-springBoot-demo/src/main/java/com/jerry/langchain4jspringbootdemo/store/db/aiassitant.db";
        try (Connection connection = DriverManager.getConnection(url)) {
            if (connection.isValid(1000)) {
                System.out.println("DuckDB 连接成功");

                ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM message");
                while (rs.next()) {
                    System.out.println(rs.getString("id") + "\t" + rs.getString("embedding")+ "\t" + rs.getString("text"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
