package com.sanshi.fileserver.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 答卷
 */
@Entity
public class Respondents {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer assessId; //答卷ID
    private Integer stuId;//学生id
    private Integer makeTime;//答题时间
    private Integer submit;//提交状态 0:无；1:未提交；2：已提交
    private Integer correct;//批改状态 0:未完成 1：已完成
    private Integer correctId;//批阅人id
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date uapdateTime;

    public Respondents() {
    }



    public Respondents(Integer id, Integer assessId, Integer stuId, Integer makeTime, Integer submit, Integer correct, Integer correctId, Date createTime, Date uapdateTime) {
        this.id = id;
        this.assessId = assessId;
        this.stuId = stuId;
        this.makeTime = makeTime;
        this.submit = submit;
        this.correct = correct;
        this.correctId = correctId;
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

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public Integer getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(Integer makeTime) {
        this.makeTime = makeTime;
    }

    public Integer getSubmit() {
        return submit;
    }

    public void setSubmit(Integer submit) {
        this.submit = submit;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public Integer getCorrectId() {
        return correctId;
    }

    public void setCorrectId(Integer correctId) {
        this.correctId = correctId;
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
        return "Respondents{" +
                "id=" + id +
                ", assessId=" + assessId +
                ", stuId=" + stuId +
                ", makeTime=" + makeTime +
                ", submit=" + submit +
                ", correct=" + correct +
                ", correctId=" + correctId +
                ", createTime=" + createTime +
                ", uapdateTime=" + uapdateTime +
                '}';
    }
}
