package com.sanshi.fileserver.bean;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;//学科名称
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date uapdateTime;

    public Subject() {
    }

    public Subject(Integer id, String name, Date createTime, Date uapdateTime) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.uapdateTime = uapdateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", uapdateTime=" + uapdateTime +
                '}';
    }
}
