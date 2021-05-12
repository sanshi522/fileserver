package com.sanshi.fileserver.vo;

import com.sanshi.fileserver.bean.Choice;

public class ChoiceVo {
    private Choice choice;
    private Double score;
    private Integer indexNum;

    public ChoiceVo() {
    }

    public ChoiceVo(Choice choice, Double score, Integer indexNum) {
        this.choice = choice;
        this.score = score;
        this.indexNum = indexNum;
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(Integer indexNum) {
        this.indexNum = indexNum;
    }


    @Override
    public String toString() {
        return "ChoiceVo{" +
                "choice=" + choice +
                ", score=" + score +
                ", indexNum=" + indexNum +
                '}';
    }
}
