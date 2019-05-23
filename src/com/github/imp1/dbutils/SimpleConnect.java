package com.github.imp1.dbutils;

import java.sql.*;
import java.util.HashSet;

/**
 *
 */
public class SimpleConnect {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        String url = "jdbc:mysql://localhost:3306/animalsys?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String username = "root";
        String password = "password";
        Connection connection = DriverManager.getConnection(url, username, password);

        Statement statement = connection.createStatement();

        String sql = "select * from animalsys.rules where is_use=1;";

        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            int idrule = Integer.parseInt(resultSet.getString(1));
            String have = resultSet.getString(2);
            String conclusion = resultSet.getString(4);

            HashSet<String> set = new HashSet<String>();
            String[] strings = have.split(" ");

            for(String s: strings){
                set.add(s.trim());
            }

            for(String s: set){
                System.out.println(s);
            }
        }

        connection.close();
    }
}

/*
jdbc:mysql://localhost:3306/animalsys?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
* */