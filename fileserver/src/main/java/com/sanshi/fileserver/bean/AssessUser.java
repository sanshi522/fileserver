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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer assessId;//考核id
    /**
     * (1表示学年 2 表示学院 3表示班级 4表示小组 5学生)
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
        this.assessId = assessId;
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
        return assessId;
    }

    public void setAssessId(Integer assessId) {
        this.assessId = assessId;
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
                ", assessId=" + assessId +
                ", testObject=" + testObject +
                ", testObjectId=" + testObjectId +
                ", createTime=" + createTime +
                ", uapdateTime=" + uapdateTime +
                '}';
    }
}
