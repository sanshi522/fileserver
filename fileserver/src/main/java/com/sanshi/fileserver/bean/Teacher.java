package com.sanshi.fileserver.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer teaId;
    private String teaNumber;
    private String teaName;
    private String teaGender;
    private String teaPass;
    private String teaHead;
    private Integer teaIdentity;
    private Integer teaState;
    private String teaRemake;

    @Override
    public String toString() {
        return "Teacher{" +
                "teaId=" + teaId +
                ", teaNumber='" + teaNumber + '\'' +
                ", teaName='" + teaName + '\'' +
                ", teaGender='" + teaGender + '\'' +
                ", teaPass='" + teaPass + '\'' +
                ", teaHead='" + teaHead + '\'' +
                ", teaIdentity=" + teaIdentity +
                ", teaState=" + teaState +
                ", teaRemake='" + teaRemake + '\'' +
                '}';
    }

    public Integer getTeaId() {
        return teaId;
    }

    public void setTeaId(Integer teaId) {
        this.teaId = teaId;
    }

    public String getTeaNumber() {
        return teaNumber;
    }

    public void setTeaNumber(String teaNumber) {
        this.teaNumber = teaNumber;
    }

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    public String getTeaGender() {
        return teaGender;
    }

    public void setTeaGender(String teaGender) {
        this.teaGender = teaGender;
    }

    public String getTeaPass() {
        return teaPass;
    }

    public void setTeaPass(String teaPass) {
        this.teaPass = teaPass;
    }

    public String getTeaHead() {
        return teaHead;
    }

    public void setTeaHead(String teaHead) {
        this.teaHead = teaHead;
    }

    public Integer getTeaIdentity() {
        return teaIdentity;
    }

    public void setTeaIdentity(Integer teaIdentity) {
        this.teaIdentity = teaIdentity;
    }

    public Integer getTeaState() {
        return teaState;
    }

    public void setTeaState(Integer teaState) {
        this.teaState = teaState;
    }

    public String getTeaRemake() {
        return teaRemake;
    }

    public void setTeaRemake(String teaRemake) {
        this.teaRemake = teaRemake;
    }
}
