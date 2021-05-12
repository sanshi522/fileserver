package com.sanshi.fileserver.bean;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teaId;
    private String teaNumber;
    private String teaName;
    private String teaGender;
    private String teaPass;
    private String teaHead;
    private Integer teaIdentity;
    private Integer teaState;
    private String teaRemake;
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date uapdateTime;


    public Teacher() {
    }

    public Teacher(Integer teaId, String teaNumber, String teaName, String teaGender, String teaPass, String teaHead, Integer teaIdentity, Integer teaState, String teaRemake, Date createTime, Date uapdateTime) {
        this.teaId = teaId;
        this.teaNumber = teaNumber;
        this.teaName = teaName;
        this.teaGender = teaGender;
        this.teaPass = teaPass;
        this.teaHead = teaHead;
        this.teaIdentity = teaIdentity;
        this.teaState = teaState;
        this.teaRemake = teaRemake;
        this.createTime = createTime;
        this.uapdateTime = uapdateTime;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUapdateTime() {
        return uapdateTime;
    }

    public void setUapdateTime(Date uapdateTime) {
        this.uapdateTime = uapdateTime;
    }

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
                ", createTime=" + createTime +
                ", uapdateTime=" + uapdateTime +
                '}';
    }
}
