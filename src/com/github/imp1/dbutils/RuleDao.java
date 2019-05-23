package com.github.imp1.dbutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class RuleDao {
    private static Connection conn = null;

    public static void saveRule(Rule rule) throws IOException, SQLException, ClassNotFoundException {
        try
        {
            conn=getConnection();
            Statement stat = conn.createStatement();
            String sql="insert into rules(have, conclution,is_use) values ((?), (?), (?))";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, rule.getHava());
            preparedStatement.setString(2, rule.getConclusion());
            preparedStatement.setInt(3, rule.getIsUse());
            preparedStatement.executeUpdate();
        }
        finally
        {
            conn.close();
        }
    }

    /**
     * 获取数据库中的所有规则信息
     */
    public static ArrayList<Rule> getAllUsedRule() throws SQLException, IOException, ClassNotFoundException {
        ArrayList<Rule> array = new ArrayList<Rule>();
        try {
            conn = getConnection();
            Statement stat = conn.createStatement();
            String sql = "select * from animalsys.rules where is_use=1;";
            ResultSet result = stat.executeQuery(sql);

            while (result.next()) {
                Rule rule = new Rule();
                rule.setRuleid(Integer.parseInt(result.getString(1)));
                rule.setHava(result.getString(2));
                rule.setNotHave(result.getString(3));
                rule.setConclusion(result.getString(4));
                rule.setIsUse(Integer.parseInt(result.getString(5)));
                array.add(rule);
            }
        } finally {
            conn.close();
        }
        return array;
    }

    public static ArrayList<Rule> getAllRule() throws SQLException, IOException, ClassNotFoundException {
        ArrayList<Rule> array = new ArrayList<Rule>();
        try {
            conn = getConnection();
            Statement stat = conn.createStatement();
            String sql = "select * from animalsys.rules;";
            ResultSet result = stat.executeQuery(sql);

            while (result.next()) {
                Rule rule = new Rule();
                rule.setRuleid(Integer.parseInt(result.getString(1)));
                rule.setHava(result.getString(2));
                rule.setNotHave(result.getString(3));
                rule.setConclusion(result.getString(4));
                rule.setIsUse(Integer.parseInt(result.getString(5)));
                array.add(rule);
            }
        } finally {
            conn.close();
        }
        return array;
    }


    /**更新数据库中的规则信息*/
    public static void updateRule(Rule rule) throws SQLException, IOException, ClassNotFoundException {
        try {
            conn = getConnection();
            String sql = "update animalsys.rules set animalsys.rules.have=(?),animalsys.rules.conclution=(?) where idrules=(?);";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(3, rule.getRuleid());
            preparedStatement.setString(1, rule.getHava());
            preparedStatement.setString(2, rule.getConclusion());
            preparedStatement.executeUpdate();
        } finally {
            conn.close();
        }
    }

    /**删除数据库中的规则信息*/
    public static void deleteById(final int idrules) throws SQLException, IOException, ClassNotFoundException {
        try
        {	  conn=getConnection();
            String sql="update rules set is_use=0 where idrules= (?);";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, idrules);
            statement.executeUpdate();
        }
        finally
        {
            conn.close();
        }
    }

    /**
     * 根据结论获取规则
     */
    public static ArrayList<Rule> getAllRuleAllConclusion(String conclution) throws IOException, SQLException, ClassNotFoundException {
        ArrayList<Rule> rules = null;
        try
        {	  conn=getConnection();
            String sql="select *\n" +
                    "from animalsys.rules where is_use=1 and conclution=(?);";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, conclution);
            ResultSet resultSet = statement.executeQuery();
            rules = new ArrayList<Rule>();
            while (resultSet.next()){
                Rule rule = new Rule();
                rule.setRuleid(Integer.parseInt(resultSet.getString(1)));
                rule.setHava(resultSet.getString(2));
                rule.setNotHave(resultSet.getString(3));
                rule.setConclusion(resultSet.getString(4));
                rule.setIsUse(Integer.parseInt(resultSet.getString(5)));
                rules.add(rule);
            }
        }
        finally
        {
            conn.close();
        }
        return rules;
    }

    public static boolean isConclusion(String condition) throws SQLException, IOException, ClassNotFoundException {
        return getAllRuleAllConclusion(condition).size() > 0;
    }

    /**
     * 建立与数据库的连接
     */
    public static Connection getConnection()
            throws SQLException, IOException, ClassNotFoundException {
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        String url = "jdbc:mysql://localhost:3306/animalsys?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String username = "root";
        String password = "password";
        Connection connection = DriverManager.getConnection(url, username, password);

        return connection;
    }
}
/*
select *
from animalsys.rules where is_use=1 and conclution='哺乳动物';
* */