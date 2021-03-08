package com.sanshi.fileserver.vo;

import com.sanshi.fileserver.bean.Assess;
import lombok.Data;

@Data
public class AssessVo {
    //登陆身份、试卷名、进行中数量、已完成数量、考核人总数、科目名、总分、发布人、考核对象、对象名称、用时、提交状态、考核
    Integer loginType;
    String TestPaperName;
    Integer underway;
    Integer finish;
    Integer assessSum;
    String subjectName;
    Double totalPoints;
    String issueName;
    String testObject;
    String testObjectName;
    Integer makeTime;
    Integer submit;
    Assess assess;

    public AssessVo() {
    }

    public AssessVo(Integer loginType, String testPaperName, Integer underway, Integer finish, Integer assessSum, String subjectName, Double totalPoints, String issueName, String testObject, String testObjectName, Integer makeTime, Integer submit, Assess assess) {
        this.loginType = loginType;
        TestPaperName = testPaperName;
        this.underway = underway;
        this.finish = finish;
        this.assessSum = assessSum;
        this.subjectName = subjectName;
        this.totalPoints = totalPoints;
        this.issueName = issueName;
        this.testObject = testObject;
        this.testObjectName = testObjectName;
        this.makeTime = makeTime;
        this.submit = submit;
        this.assess = assess;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public String getTestPaperName() {
        return TestPaperName;
    }

    public void setTestPaperName(String testPaperName) {
        TestPaperName = testPaperName;
    }

    public Integer getUnderway() {
        return underway;
    }

    public void setUnderway(Integer underway) {
        this.underway = underway;
    }

    public Integer getFinish() {
        return finish;
    }

    public void setFinish(Integer finish) {
        this.finish = finish;
    }

    public Integer getAssessSum() {
        return assessSum;
    }

    public void setAssessSum(Integer assessSum) {
        this.assessSum = assessSum;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public String getTestObject() {
        return testObject;
    }

    public void setTestObject(String testObject) {
        this.testObject = testObject;
    }

    public String getTestObjectName() {
        return testObjectName;
    }

    public void setTestObjectName(String testObjectName) {
        this.testObjectName = testObjectName;
    }

    public Integer getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(Integer makeTime) {
        this.makeTime = makeTime;
    }

    public Integer getSubmit() {
        return submit;
    }

    public void setSubmit(Integer submit) {
        this.submit = submit;
    }

    public Assess getAssess() {
        return assess;
    }

    public void setAssess(Assess assess) {
        this.assess = assess;
    }

    @Override
    public String toString() {
        return "AssessVo{" +
                "loginType=" + loginType +
                ", TestPaperName='" + TestPaperName + '\'' +
                ", underway=" + underway +
                ", finish=" + finish +
                ", assessSum=" + assessSum +
                ", subjectName='" + subjectName + '\'' +
                ", totalPoints=" + totalPoints +
                ", issueName='" + issueName + '\'' +
                ", testObject='" + testObject + '\'' +
                ", testObjectName='" + testObjectName + '\'' +
                ", makeTime=" + makeTime +
                ", submit=" + submit +
                ", assess=" + assess +
                '}';
    }
}
