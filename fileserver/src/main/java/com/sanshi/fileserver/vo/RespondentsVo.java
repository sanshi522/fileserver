package com.sanshi.fileserver.vo;

import com.sanshi.fileserver.bean.Answer;
import com.sanshi.fileserver.bean.Respondents;

import java.util.List;

public class RespondentsVo {
    private Respondents  respondents;
    private List<Answer>  answerList;

    public RespondentsVo(Respondents respondents, List<Answer> answerList) {
        this.respondents = respondents;
        this.answerList = answerList;
    }

    public RespondentsVo() {
    }


    public Respondents getRespondents() {
        return respondents;
    }

    public void setRespondents(Respondents respondents) {
        this.respondents = respondents;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }


    @Override
    public String toString() {
        return "RespondentsVo{" +
                "respondents=" + respondents +
                ", answerList=" + answerList +
                '}';
    }


}
