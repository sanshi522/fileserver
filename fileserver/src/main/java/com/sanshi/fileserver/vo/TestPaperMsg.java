package com.sanshi.fileserver.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class TestPaperMsg {
    Integer choiceNum;
    Double choiceScoreNum;
    String subName;
    String creatUserName;

    public TestPaperMsg() {
    }

    public TestPaperMsg(Integer choiceNum, Double choiceScoreNum, String subName, String creatUserName) {
        this.choiceNum = choiceNum;
        this.choiceScoreNum = choiceScoreNum;
        this.subName = subName;
        this.creatUserName = creatUserName;
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

    public String getCreatUserName() {
        return creatUserName;
    }

    public void setCreatUserName(String creatUserName) {
        this.creatUserName = creatUserName;
    }


    @Override
    public String toString() {
        return "TestPaperMsg{" +
                "choiceNum=" + choiceNum +
                ", choiceScoreNum=" + choiceScoreNum +
                ", subName='" + subName + '\'' +
                ", creatUserName='" + creatUserName + '\'' +
                '}';
    }
}
