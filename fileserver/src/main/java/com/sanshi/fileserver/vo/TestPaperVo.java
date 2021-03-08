package com.sanshi.fileserver.vo;

import com.sanshi.fileserver.bean.Assess;
import com.sanshi.fileserver.bean.Respondents;
import com.sanshi.fileserver.bean.TestPaper;
import lombok.Data;

import java.util.List;

@Data
public class TestPaperVo{
    private Assess assess;//考核信息
    private TestPaper testPaper;//试卷信息
    private Respondents respondents;//答卷
    private List<MakeChoice> makeChoiceList;

    public TestPaperVo() {
    }

    public TestPaperVo(Assess assess, TestPaper testPaper, Respondents respondents, List<MakeChoice> makeChoiceList) {
        this.assess = assess;
        this.testPaper = testPaper;
        this.respondents = respondents;
        this.makeChoiceList = makeChoiceList;
    }

    public Assess getAssess() {
        return assess;
    }

    public void setAssess(Assess assess) {
        this.assess = assess;
    }

    public TestPaper getTestPaper() {
        return testPaper;
    }

    public void setTestPaper(TestPaper testPaper) {
        this.testPaper = testPaper;
    }

    public Respondents getRespondents() {
        return respondents;
    }

    public void setRespondents(Respondents respondents) {
        this.respondents = respondents;
    }

    public List<MakeChoice> getMakeChoiceList() {
        return makeChoiceList;
    }

    public void setMakeChoiceList(List<MakeChoice> makeChoiceList) {
        this.makeChoiceList = makeChoiceList;
    }

    @Override
    public String toString() {
        return "TestPaperVo{" +
                "assess=" + assess +
                ", testPaper=" + testPaper +
                ", respondents=" + respondents +
                ", makeChoiceList=" + makeChoiceList +
                '}';
    }

}
