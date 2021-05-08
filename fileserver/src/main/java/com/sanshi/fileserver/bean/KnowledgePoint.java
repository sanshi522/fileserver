package com.sanshi.fileserver.bean;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class KnowledgePoint {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer subId;//学科id
    private String name;//知识点名字
    private String analysis;//解析
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date uapdateTime;

    public KnowledgePoint() {
    }

    public KnowledgePoint(Integer id, Integer subId, String name, String analysis, Date createTime, Date uapdateTime) {
        this.id = id;
        this.subId = subId;
        this.name = name;
        this.analysis = analysis;
        this.createTime = createTime;
        this.uapdateTime = uapdateTime;
    }

    public KnowledgePoint(Integer id, Integer subId, String name, String analysis) {
        this.id = id;
        this.subId = subId;
        this.name = name;
        this.analysis = analysis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
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
        return "KnowledgePoint{" +
                "id=" + id +
                ", subId=" + subId +
                ", name='" + name + '\'' +
                ", analysis='" + analysis + '\'' +
                ", createTime=" + createTime +
                ", uapdateTime=" + uapdateTime +
                '}';
    }
}
