package com.sanshi.fileserver.bean;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 答题
 */
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer choiceId;//试题id
    private Integer respondentsId;//答卷id
    private String answer;//答案
    private String fileIds;//附件id集合
    private Double score;//得分
    private Integer correct;//批改状态 //0未批改1已批改
    private Integer correctUserid;//0为系统批改1为老师批改
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date uapdateTime;

    public Answer() {
    }

    public Answer(Integer id, Integer choiceId, Integer respondentsId, String answer, String fileIds, Double score, Integer correct, Integer correctUserid, Date createTime, Date uapdateTime) {
        this.id = id;
        this.choiceId = choiceId;
        this.respondentsId = respondentsId;
        this.answer = answer;
        this.fileIds = fileIds;
        this.score = score;
        this.correct = correct;
        this.correctUserid = correctUserid;
        this.createTime = createTime;
        this.uapdateTime = uapdateTime;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Integer choiceId) {
        this.choiceId = choiceId;
    }

    public Integer getRespondentsId() {
        return respondentsId;
    }

    public void setRespondentsId(Integer respondentsId) {
        this.respondentsId = respondentsId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public Integer getCorrectUserid() {
        return correctUserid;
    }

    public void setCorrectUserid(Integer correctUserid) {
        this.correctUserid = correctUserid;
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
        return "Answer{" +
                "id=" + id +
                ", choiceId=" + choiceId +
                ", respondentsId=" + respondentsId +
                ", answer='" + answer + '\'' +
                ", fileIds='" + fileIds + '\'' +
                ", score=" + score +
                ", correct=" + correct +
                ", correctUserid=" + correctUserid +
                ", createTime=" + createTime +
                ", uapdateTime=" + uapdateTime +
                '}';
    }


}
