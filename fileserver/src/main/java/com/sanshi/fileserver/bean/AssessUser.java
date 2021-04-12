package com.sanshi.fileserver.bean;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class AssessUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer AssessId;//考核id
    /**
     * (0表示学年 1 表示学院 2表示班级 3表示小组 4学生)
     */
    private Integer testObject;//考试级别
    private Integer testObjectId;//考试对象id
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date uapdateTime;

    public AssessUser() {
    }

    public AssessUser(Integer id, Integer assessId, Integer testObject, Integer testObjectId, Date createTime, Date uapdateTime) {
        this.id = id;
        AssessId = assessId;
        this.testObject = testObject;
        this.testObjectId = testObjectId;
        this.createTime = createTime;
        this.uapdateTime = uapdateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAssessId() {
        return AssessId;
    }

    public void setAssessId(Integer assessId) {
        AssessId = assessId;
    }

    public Integer getTestObject() {
        return testObject;
    }

    public void setTestObject(Integer testObject) {
        this.testObject = testObject;
    }

    public Integer getTestObjectId() {
        return testObjectId;
    }

    public void setTestObjectId(Integer testObjectId) {
        this.testObjectId = testObjectId;
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
        return "AssessUser{" +
                "id=" + id +
                ", AssessId=" + AssessId +
                ", testObject=" + testObject +
                ", testObjectId=" + testObjectId +
                ", createTime=" + createTime +
                ", uapdateTime=" + uapdateTime +
                '}';
    }
}
