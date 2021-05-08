package com.sanshi.fileserver.vo;

import com.sanshi.fileserver.bean.Answer;
import com.sanshi.fileserver.bean.Assess;
import com.sanshi.fileserver.bean.Choice;

public class AnswerVo {
    /**
     * 题目
     */
    private Choice  choice;

    /**
     * 答题
     */
    private Answer answer;

    /**
     * 分数
     */
    private Double   score;


    public AnswerVo() {
    }

    public AnswerVo(Choice choice, Answer answer, Double score) {
        this.choice = choice;
        this.answer = answer;
        this.score = score;
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
}
