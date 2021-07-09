package com.sanshi.fileserver.utils;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import java.util.Date;
public class ChoiceExcel {
    @Excel(name = "学科", orderNum = "0", width = 30)
    private String subId;
    /**
     * 题目
     */

    @Excel(name = "题目(填空题需要填写答案区域用_表示)", orderNum = "1", width = 100)
    private String topic;
    /**
     * 类型（单选，多选，判断题，简答题）
     */
    @Excel(name = "类型", orderNum = "2", width = 20, replace = {"单选题_1", "多选题_2", "判断题_3", "简答题_4","填空题_5"})
    private Integer type;

    /**
     * 选项个数
     */
    @Excel(name = "选项个数（简答题,判断题，填空题无需填写,多选和单选填对应的选项个数,）", orderNum = "3", width = 50)
    private Integer optionNum;
    /**
     * 选项A
     */
    @Excel(name = "选项A", orderNum = "4", width = 50)
    private String optionA;
    /**
     * 选项B
     */
    @Excel(name = "选项B", orderNum = "5", width = 50)
    private String optionB;
    /**
     * 选项C
     */
    @Excel(name = "选项c", orderNum = "6", width = 50)
    private String optionC;
    /**
     * 选项D
     */
    @Excel(name = "选项D", orderNum = "7", width = 50)
    private String optionD;
    /**
     * 选项E
     */
    @Excel(name = "选项E", orderNum = "8", width = 50)
    private String optionE;
    /**
     * 选项F
     */
    @Excel(name = "选项F", orderNum = "9", width = 50)
    private String optionF;
    /**
     * 正确选项
     */
    @Excel(name = "答案(多选用逗号隔开,问答题(1表示正确,0表示错误))", orderNum = "10", width = 100)
    private String correct;
    /**
     * 解析
     */
    @Excel(name = "解析", orderNum = "11", width = 100)
    private String analysis;

    /**
     * 难易程度
     */
    @Excel(name = "难度星级", orderNum = "12", width = 10)
    private Integer difficultyLevel;
    /**
     * 评分标准(0:全自动相等;1:少选百分比多选不得分；2：人工)
     */
    @Excel(name = "评分标准", orderNum = "13", width = 10, replace = {"全自动相等_0", "少选百分比多选不得分_1", "人工_2"})
    private Integer scaleRule;
    /**
     * 知识点id集合
     */
    @Excel(name = "知识点", orderNum = "14", width = 60)
    private String abilityIds;


    public ChoiceExcel() {
    }

    public ChoiceExcel(String subId, String topic, Integer type, Integer optionNum, String optionA, String optionB, String optionC, String optionD, String optionE, String optionF, String correct, String analysis, Integer difficultyLevel, Integer scaleRule, String abilityIds) {
        this.subId = subId;
        this.topic = topic;
        this.type = type;
        this.optionNum = optionNum;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.optionE = optionE;
        this.optionF = optionF;
        this.correct = correct;
        this.analysis = analysis;
        this.abilityIds = abilityIds;
        this.difficultyLevel = difficultyLevel;
        this.scaleRule = scaleRule;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOptionNum() {
        return optionNum;
    }

    public void setOptionNum(Integer optionNum) {
        this.optionNum = optionNum;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getOptionE() {
        return optionE;
    }

    public void setOptionE(String optionE) {
        this.optionE = optionE;
    }

    public String getOptionF() {
        return optionF;
    }

    public void setOptionF(String optionF) {
        this.optionF = optionF;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getAbilityIds() {
        return abilityIds;
    }

    public void setAbilityIds(String abilityIds) {
        this.abilityIds = abilityIds;
    }

    public Integer getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(Integer difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Integer getScaleRule() {
        return scaleRule;
    }

    public void setScaleRule(Integer scaleRule) {
        this.scaleRule = scaleRule;
    }

    @Override
    public String toString() {
        return "ChoiceExcel{" +
                "subId='" + subId + '\'' +
                ", topic='" + topic + '\'' +
                ", type=" + type +
                ", optionNum=" + optionNum +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", optionE='" + optionE + '\'' +
                ", optionF='" + optionF + '\'' +
                ", correct='" + correct + '\'' +
                ", analysis='" + analysis + '\'' +
                ", abilityIds='" + abilityIds + '\'' +
                ", difficultyLevel=" + difficultyLevel +
                ", scaleRule=" + scaleRule +
                '}';
    }
}
