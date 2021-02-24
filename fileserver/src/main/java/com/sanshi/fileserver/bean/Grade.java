package com.sanshi.fileserver.bean;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 学院
 */
@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer year;
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date uapdateTime;

    public Grade() {
    }

    public Grade(Integer id, String name, Integer year, Date createTime, Date uapdateTime) {
        this.id = id;
        this.name = name;
        this.year = year;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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
        return "Grade{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", createTime=" + createTime +
                ", uapdateTime=" + uapdateTime +
                '}';
    }
}
