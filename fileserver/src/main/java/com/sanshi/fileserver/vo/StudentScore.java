package com.sanshi.fileserver.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class StudentScore {


    @Excel(name = "姓名", width = 20, orderNum = "1")
    private  String   stuName;
    @Excel(name = "学号", width = 20, orderNum = "2")
    private  String  stuNumber;
    @Excel(name = "学院", width = 20, orderNum = "3")
    private  String   gradeName;
    @Excel(name = "班级", width = 20, orderNum = "4")
    private  String   className;
    @Excel(name = "考核名", width = 20, orderNum = "5")
    private  String   assessName;
    @Excel(name = "状态", width = 20, orderNum = "6")
    private  String    state;
    @Excel(name = "成绩", width = 20, orderNum = "7")
    private  double    score;


    public StudentScore(String stuName, String stuNumber, String gradeName, String className, String assessName, String state, double score) {
        this.stuName = stuName;
        this.stuNumber = stuNumber;
        this.gradeName = gradeName;
        this.className = className;
        this.assessName = assessName;
        this.state = state;
        this.score = score;
    }

    public StudentScore() {
    }


    public String getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAssessName() {
        return assessName;
    }

    public void setAssessName(String assessName) {
        this.assessName = assessName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }


    @Override
    public String toString() {
        return "StudentScore{" +
                "stuNumber='" + stuNumber + '\'' +
                ", stuName='" + stuName + '\'' +
                ", gradeName='" + gradeName + '\'' +
                ", className='" + className + '\'' +
                ", assessName='" + assessName + '\'' +
                ", state='" + state + '\'' +
                ", score=" + score +
                '}';
    }
}
