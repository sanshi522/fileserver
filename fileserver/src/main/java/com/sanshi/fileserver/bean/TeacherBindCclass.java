package com.sanshi.fileserver.bean;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 老师与班级绑定表
 */
@Entity
public class TeacherBindCclass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer teeaId;
    private Integer cclassId;
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date uapdateTime;

    public TeacherBindCclass() {
    }

    public TeacherBindCclass(Integer id, Integer teeaId, Integer cclassId, Date createTime, Date uapdateTime) {
        this.id = id;
        this.teeaId = teeaId;
        this.cclassId = cclassId;
        this.createTime = createTime;
        this.uapdateTime = uapdateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeeaId() {
        return teeaId;
    }

    public void setTeeaId(Integer teeaId) {
        this.teeaId = teeaId;
    }

    public Integer getCclassId() {
        return cclassId;
    }

    public void setCclassId(Integer cclassId) {
        this.cclassId = cclassId;
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
        return "TeacherBindCclass{" +
                "id=" + id +
                ", teeaId=" + teeaId +
                ", cclassId=" + cclassId +
                ", createTime=" + createTime +
                ", uapdateTime=" + uapdateTime +
                '}';
    }
}
