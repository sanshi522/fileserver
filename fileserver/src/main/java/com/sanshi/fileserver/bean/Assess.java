package com.sanshi.fileserver.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
//考核数据库
@Entity
public class Assess {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer testPaperId;//试卷id
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date startTime;//开始时间
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date endTime;//结束时间
    private Integer testObject;//考试级别
    private Integer testObjectId;//考试对象id
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date uapdateTime;

    public Assess() {
    }

    public Assess(Integer id, Integer testPaperId, Date startTime, Date endTime, Integer testObject, Integer testObjectId, Date createTime, Date uapdateTime) {
        this.id = id;
        this.testPaperId = testPaperId;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public Integer getTestPaperId() {
        return testPaperId;
    }

    public void setTestPaperId(Integer testPaperId) {
        this.testPaperId = testPaperId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
        return "Assess{" +
                "id=" + id +
                ", testPaperId=" + testPaperId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", testObject=" + testObject +
                ", testObjectId=" + testObjectId +
                ", createTime=" + createTime +
                ", uapdateTime=" + uapdateTime +
                '}';
    }
}
