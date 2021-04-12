package com.sanshi.fileserver.vo;

import lombok.Data;

@Data
public class AssessMsg {
    /**
     * 发布人
     */
    private  String  name;
    /**
     * 试题数量
     */
    Integer choiceNum;
    /**
     * 试卷总分
     */
    Double choiceScoreNum;
    /**
     * 学科
     */
    String subName;


    public AssessMsg() {
    }

    public AssessMsg(String name, Integer choiceNum, Double choiceScoreNum, String subName) {
        this.name = name;
        this.choiceNum = choiceNum;
        this.choiceScoreNum = choiceScoreNum;
        this.subName = subName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getChoiceNum() {
        return choiceNum;
    }

    public void setChoiceNum(Integer choiceNum) {
        this.choiceNum = choiceNum;
    }

    public Double getChoiceScoreNum() {
        return choiceScoreNum;
    }

    public void setChoiceScoreNum(Double choiceScoreNum) {
        this.choiceScoreNum = choiceScoreNum;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }




}
