package com.sanshi.fileserver.vo;

import com.sanshi.fileserver.bean.Choice;
import lombok.Data;

/**
 * 试卷vo
 */
@Data
public class ChoiceDetails implements Comparable<ChoiceDetails>{
    /**
     * 试题下标
     */
    Integer index;
    /**
     * 分值
     */
    Double score;
    /**
     * 试题
     */
    Choice choice;

    public ChoiceDetails() {
    }

    public ChoiceDetails(Integer index, Double score, Choice choice) {
        this.index = index;
        this.score = score;
        this.choice = choice;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    @Override
    public String toString() {
        return "ChoiceDetails{" +
                "index=" + index +
                ", score=" + score +
                ", choice=" + choice +
                '}';
    }

    @Override
    public int compareTo(ChoiceDetails o) {
        return this.getIndex()-o.getIndex();
    }
}
