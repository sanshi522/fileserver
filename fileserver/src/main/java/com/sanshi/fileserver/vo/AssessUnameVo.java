package com.sanshi.fileserver.vo;

import com.sanshi.fileserver.bean.AssessUser;

public class AssessUnameVo {
    private AssessUser assessUser;
    private String name;

    public AssessUnameVo() {
    }

    public AssessUser getAssessUser() {
        return assessUser;
    }

    public void setAssessUser(AssessUser assessUser) {
        this.assessUser = assessUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AssessUnameVo{" +
                "assessUser=" + assessUser +
                ", name='" + name + '\'' +
                '}';
    }
}
