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
  private  Integer choiceNum;
    /**
     * 试卷总分
     */
    private Double choiceScoreNum;
    /**
     * 学科
     */
    private String subName;

    /**
     * 未批阅数量
     */
    private int  notredNumber;

    /***
     * 已批阅数量
     */
    private  int  redNumber;

    /**
     * 是否已提交答卷
     */
    private  int  submit;


    public AssessMsg() {
    }


    public AssessMsg(String name, Integer choiceNum, Double choiceScoreNum, String subName, int notredNumber, int redNumber) {
        this.name = name;
        this.choiceNum = choiceNum;
        this.choiceScoreNum = choiceScoreNum;
        this.subName = subName;
        this.notredNumber = notredNumber;
        this.redNumber = redNumber;
    }


    public AssessMsg(String name, Integer choiceNum, Double choiceScoreNum, String subName, int notredNumber, int redNumber, int submit) {
        this.name = name;
        this.choiceNum = choiceNum;
        this.choiceScoreNum = choiceScoreNum;
        this.subName = subName;
        this.notredNumber = notredNumber;
        this.redNumber = redNumber;
        this.submit = submit;
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

    public int getNotredNumber() {
        return notredNumber;
    }

    public void setNotredNumber(int notredNumber) {
        this.notredNumber = notredNumber;
    }

    public int getRedNumber() {
        return redNumber;
    }

    public void setRedNumber(int redNumber) {
        this.redNumber = redNumber;
    }

    public int getSubmit() {
        return submit;
    }

    public void setSubmit(int submit) {
        this.submit = submit;
    }

    @Override
    public String toString() {
        return "AssessMsg{" +
                "name='" + name + '\'' +
                ", choiceNum=" + choiceNum +
                ", choiceScoreNum=" + choiceScoreNum +
                ", subName='" + subName + '\'' +
                ", notredNumber=" + notredNumber +
                ", redNumber=" + redNumber +
                ", submit=" + submit +
                '}';
    }
}
