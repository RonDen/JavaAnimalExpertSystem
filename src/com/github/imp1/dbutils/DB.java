package com.github.imp1.dbutils;

import java.sql.*;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class DB {
    private Statement statement = null;

    public DB() throws ClassNotFoundException, SQLException {
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        String url = "jdbc:mysql://localhost:3306/animalsys?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String username = "root";
        String password = "password";
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
    }

    public HashSet<String> convertToSet(String string) {
        HashSet<String> set = new HashSet<String>();
        String[] strings = string.split(" ");
        for (String s : strings) {
            set.add(s.trim());
        }
        return set;
    }

    public LinkedHashMap<String, String> getMap() throws SQLException {
        if (statement == null) {
            System.out.println("未成功连接数据库和初始化状态");
            return null;
        }

        String sql = "select * from animalsys.rules where is_use=1;";
        ResultSet resultSet = statement.executeQuery(sql);

        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        while (resultSet.next()) {
            String have = resultSet.getString(2);
            String conclusion = resultSet.getString(4);

            result.put(have, conclusion);
        }
        return result;
    }

    public ResultSet getResultSet() throws SQLException {
        if (statement == null) {
            System.out.println("未成功连接数据库和初始化状态");
            return null;
        }
        String sql = "select * from animalsys.rules where is_use=1;";
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    public boolean deleteRule(int idrules) throws SQLException {
        if (statement == null) {
            System.out.println("未成功连接数据库和初始化状态");
            return false;
        }
        String sql = "delete from animalsys.rules where idrules=" + idrules + ";";
        statement.executeUpdate(sql);
        System.out.println("删除成功");
        return true;
    }
}
