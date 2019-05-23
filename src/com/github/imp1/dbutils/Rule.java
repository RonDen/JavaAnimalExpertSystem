package com.github.imp1.dbutils;

import java.util.HashSet;

public class Rule {
    private int ruleid;
    private String hava;
    private String notHave;
    private String conclusion;
    private int isUse = 0;

    public Rule() {
    }

    public Rule(int ruleid, String hava, String notHave, String conclusion, int isUse) {
        this.ruleid = ruleid;
        this.hava = hava;
        this.notHave = notHave;
        this.conclusion = conclusion;
        this.isUse = isUse;
    }

    public Rule(int ruleid, String hava, String conclusion) {
        this.ruleid = ruleid;
        this.hava = hava;
        this.conclusion = conclusion;
    }

    public Rule(String hava, String conclusion, int isUse) {
        this.hava = hava;
        this.conclusion = conclusion;
        this.isUse = isUse;
    }

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public int getRuleid() {
        return ruleid;
    }

    public String stringId(){
        return "规则：" + String.valueOf(ruleid);
    }

    public void setRuleid(int ruleid) {
        this.ruleid = ruleid;
    }


    public String getHava() {
        return hava;
    }

    public void setHava(String hava) {
        this.hava = hava;
    }

    public String getNotHave() {
        return notHave;
    }

    public void setNotHave(String notHave) {
        this.notHave = notHave;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(ruleid);
        stringBuffer.append(": ");
        stringBuffer.append(hava);
        stringBuffer.append('\t');
        if(notHave != null){
            stringBuffer.append(notHave);
            stringBuffer.append('\t');
        }
        stringBuffer.append(conclusion);
        stringBuffer.append('\t');
        if (this.isUse == 0){
            stringBuffer.append("废弃\n");
        } else {
            stringBuffer.append("在用\n");
        }
        return stringBuffer.toString();
    }

    public boolean isMatch(HashSet<String> facts){
        String[] conditions = hava.split(" ");
        int cnt = 0;
        for(String f: facts){
            for(String c: conditions){
                if (f.equals(c)){
                    cnt ++;
                }
            }
        }
        return cnt == conditions.length;
    }

    public double matchRate(HashSet<String> facts){
        String[] conditions = hava.split(" ");
        int cnt = 0;
        for(String item: facts){
            for (int i = 0; i < conditions.length; i++) {
                if(item.equals(conditions[i])){
                    cnt ++;
                }
            }
        }
        return (double)cnt / conditions.length;
    }
}
