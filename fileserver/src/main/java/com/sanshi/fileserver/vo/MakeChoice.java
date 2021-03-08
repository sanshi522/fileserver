package com.sanshi.fileserver.vo;

import com.sanshi.fileserver.bean.Answer;
import com.sanshi.fileserver.bean.Choice;
import lombok.Data;

@Data
public class MakeChoice implements Comparable<MakeChoice>{
    private Integer index;
    private Double score;
    private Choice choice;
    private Answer answer;

    public MakeChoice() {
    }

    public MakeChoice(Integer index, Double score, Choice choice, Answer answer) {
        this.index = index;
        this.score = score;
        this.choice = choice;
        this.answer = answer;
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

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
    @Override
    public int compareTo(MakeChoice o) {
        return this.getIndex()-o.getIndex();
    }

    @Override
    public String toString() {
        return "MakeChoice{" +
                "index=" + index +
                ", score=" + score +
                ", choice=" + choice +
                ", answer=" + answer +
                '}';
    }
}
