package com.sanshi.fileserver.bean;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 试卷试题绑定
 */
@Entity
public class TestPaperBindChoice {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer index;
    private Integer choiceId;//试题id
    private Integer TestPaperId;//试卷id
    private Double score;//分值
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date uapdateTime;

    public TestPaperBindChoice() {
    }

    public TestPaperBindChoice(Integer id, Integer index, Integer choiceId, Integer testPaperId, Double score, Date createTime, Date uapdateTime) {
        this.id = id;
        this.index = index;
        this.choiceId = choiceId;
        TestPaperId = testPaperId;
        this.score = score;
        this.createTime = createTime;
        this.uapdateTime = uapdateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Integer choiceId) {
        this.choiceId = choiceId;
    }

    public Integer getTestPaperId() {
        return TestPaperId;
    }

    public void setTestPaperId(Integer testPaperId) {
        TestPaperId = testPaperId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
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
        return "TestPaperBindChoice{" +
                "id=" + id +
                ", index=" + index +
                ", choiceId=" + choiceId +
                ", TestPaperId=" + TestPaperId +
                ", score=" + score +
                ", createTime=" + createTime +
                ", uapdateTime=" + uapdateTime +
                '}';
    }
}
