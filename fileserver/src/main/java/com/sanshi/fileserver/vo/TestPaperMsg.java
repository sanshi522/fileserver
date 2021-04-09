package com.sanshi.fileserver.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class TestPaperMsg {
    Integer choiceNum;
    Double choiceScoreNum;
    String subName;
    String CreatUserName;

    public TestPaperMsg(Integer choiceNum, Double choiceScoreNum, String subName, String creatUserName) {
        this.choiceNum = choiceNum;
        this.choiceScoreNum = choiceScoreNum;
        this.subName = subName;
        CreatUserName = creatUserName;
    }

    public TestPaperMsg() {
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
        return CreatUserName;
    }

    public void setCreatUserName(String creatUserName) {
        CreatUserName = creatUserName;
    }

    @Override
    public String toString() {
        return "TestPaperMsg{" +
                "choiceNum=" + choiceNum +
                ", choiceScoreNum=" + choiceScoreNum +
                ", subName='" + subName + '\'' +
                ", CreatUserName='" + CreatUserName + '\'' +
                '}';
    }
}
