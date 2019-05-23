package com.github.imp1.dbutils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Engine {
    public static void addConclutionCondition(HashSet<String> facts, String conclusion) throws SQLException, IOException, ClassNotFoundException {
        ArrayList<Rule> ruleArrays = RuleDao.getAllRuleAllConclusion(conclusion);
        for(Rule r: ruleArrays){
            String[] ruleHave = r.getHava().split(" ");
            for(String condition: ruleHave){
                System.out.println(condition);
            }
            facts.addAll(Arrays.asList(ruleHave));
        }
    }

    public static void addConclutionConditionRecurse(HashSet<String> facts, String conclusion) throws SQLException, IOException, ClassNotFoundException {
        ArrayList<String> conclusionSet = new ArrayList<String>();
        String s = conclusion;
        ArrayList<Rule> rules = RuleDao.getAllRuleAllConclusion(s);
        while (rules!=null){

        }
    }

    public static double matchLevle1(Rule rule, HashSet<String> facts) throws SQLException, IOException, ClassNotFoundException {
        String[] conditions = rule.getHava().split(" ");
        if(facts.contains(rule.getConclusion())){
            facts.addAll(Arrays.asList(conditions));
            return 1;
        }else {
            double rate = rule.matchRate(facts);
            if (rate == 1){
                facts.add(rule.getConclusion());
            }
            return rate;
        }
    }

    public static double matchLevel2(Rule rule, HashSet<String> facts) throws SQLException, IOException, ClassNotFoundException {
        String[] conditions = rule.getHava().split(" ");
        if(facts.contains(rule.getConclusion())){
            addConclutionCondition(facts, rule.getConclusion());
            facts.addAll(Arrays.asList(conditions));
            return 1;
        }else {
            double rate = rule.matchRate(facts);
            if (rate == 1){
                facts.add(rule.getConclusion());
                addConclutionCondition(facts, rule.getConclusion());
            }
            return rate;
        }
    }

    public static void recursion(String conclusion) throws SQLException, IOException, ClassNotFoundException {
        ArrayList<Rule> rules = RuleDao.getAllRuleAllConclusion(conclusion);
        for(Rule rule: rules){
            for(String condition: rule.getHava().split(" ")){
                if(RuleDao.isConclusion(condition)){
                    System.out.println(condition);
                    recursion(condition);
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            recursion("虎");
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
