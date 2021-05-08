package com.sanshi.fileserver.vo;

import com.sanshi.fileserver.bean.Assess;
import com.sanshi.fileserver.bean.Student;

public class RespondentsMsg {

    //考核对象
    private Assess  assess;
     //学科名字
    private  String  subName;
    //总分数
    private Double  score;
    //考核人
    private Student student;
    //状态
    private int  type;

    public RespondentsMsg() {
    }

    public RespondentsMsg(Assess assess, String subName, Double score, Student student, int type) {
        this.assess = assess;
        this.subName = subName;
        this.score = score;
        this.student = student;
        this.type = type;
    }

    public RespondentsMsg(Assess assess, String subName, Double score, Student student) {
        this.assess = assess;
        this.subName = subName;
        this.score = score;
        this.student = student;

    }

    public Assess getAssess() {
        return assess;
    }

    public void setAssess(Assess assess) {
        this.assess = assess;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RespondentsMsg{" +
                "assess=" + assess +
                ", subName='" + subName + '\'' +
                ", score=" + score +
                '}';
    }
}
