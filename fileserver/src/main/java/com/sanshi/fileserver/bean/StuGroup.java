package com.sanshi.fileserver.bean;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 小组
 */
@Entity
public class StuGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    Integer cclassId;
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date uapdateTime;

    public StuGroup() {
    }

    public StuGroup(Integer id, String name, Integer cclassId) {
        this.id = id;
        this.name = name;
        this.cclassId = cclassId;
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

    public Integer getCclassId() {
        return cclassId;
    }

    public void setCclassId(Integer cclassId) {
        this.cclassId = cclassId;
    }

    @Override
    public String toString() {
        return "StuGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cclassId=" + cclassId +
                '}';
    }
}
