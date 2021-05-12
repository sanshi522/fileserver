package com.sanshi.fileserver.vo;

import com.sanshi.fileserver.bean.Assess;
import com.sanshi.fileserver.bean.Choice;
import com.sanshi.fileserver.bean.Subject;
import com.sanshi.fileserver.bean.TestPaper;

import java.util.List;

public class StudentAssessVo {
    //学生Id
    private Integer stuId;

    /**
     * 单页显示数目
     */
    private Integer pageNumber;
    /**
     * 页码
     */
    private Integer pageIndex;

    /**
     * 考核ID
     */
    private Integer assessId;
    /**
     * 学科对象
     */
    private Subject subject;

    /**
     * 考核对象
     */
    private Assess assess;

    /**
     * 试卷对象
     */
    private TestPaper testPaper;

    /**
     * 试题集合
     */

    private List<ChoiceVo> choiceList;


    public StudentAssessVo() {
    }

    public StudentAssessVo(Integer stuId, Integer pageNumber, Integer pageIndex, Integer assessId, Assess assess, TestPaper testPaper, List<ChoiceVo> choiceList, Subject subject) {
        this.stuId = stuId;
        this.pageNumber = pageNumber;
        this.pageIndex = pageIndex;
        this.assessId = assessId;
        this.assess = assess;
        this.testPaper = testPaper;
        this.choiceList = choiceList;
        this.subject = subject;
    }

    public StudentAssessVo(Subject subject, Assess assess, TestPaper testPaper, List<ChoiceVo> choiceList) {
        this.subject = subject;
        this.assess = assess;
        this.testPaper = testPaper;
        this.choiceList = choiceList;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getAssessId() {
        return assessId;
    }

    public void setAssessId(Integer assessId) {
        this.assessId = assessId;
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

    public List<ChoiceVo> getChoiceList() {
        return choiceList;
    }

    public void setChoiceList(List<ChoiceVo> choiceList) {
        this.choiceList = choiceList;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "StudentAssessVo{" +
                "stuId=" + stuId +
                ", pageNumber=" + pageNumber +
                ", pageIndex=" + pageIndex +
                ", assessId=" + assessId +
                ", subject=" + subject +
                ", assess=" + assess +
                ", testPaper=" + testPaper +
                ", choiceList=" + choiceList +
                '}';
    }
}
