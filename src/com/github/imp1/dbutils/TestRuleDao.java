package com.github.imp1.dbutils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

public class TestRuleDao {
    @Test
    void testGetAllRule(){
        try {
            for(Rule rule: RuleDao.getAllUsedRule()){
                System.out.println(rule);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
