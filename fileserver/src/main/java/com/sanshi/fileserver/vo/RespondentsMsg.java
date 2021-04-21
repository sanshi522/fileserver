package com.sanshi.fileserver.vo;

import com.sanshi.fileserver.bean.Assess;

public class RespondentsMsg {

    //考核对象
    private Assess  assess;
     //学科名字
    private  String  subName;
    //总分数
    private Double  score;
    //考核人
    private  String  userName;

    public RespondentsMsg() {
    }


    public RespondentsMsg(Assess assess, String subName, Double score) {
        this.assess = assess;
        this.subName = subName;
        this.score = score;
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


    @Override
    public String toString() {
        return "RespondentsMsg{" +
                "assess=" + assess +
                ", subName='" + subName + '\'' +
                ", score=" + score +
                '}';
    }
}
