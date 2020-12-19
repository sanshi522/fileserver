package com.sanshi.fileserver.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 老师与班级绑定表
 */
@Entity
public class TeacherBindCclass {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;
    Integer teeaId;
    Integer cclassId;

    public TeacherBindCclass() {
    }

    public TeacherBindCclass(Integer id, Integer teeaId, Integer cclassId) {
        this.id = id;
        this.teeaId = teeaId;
        this.cclassId = cclassId;
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

    @Override
    public String toString() {
        return "TeacherBindCclass{" +
                "id=" + id +
                ", teeaId=" + teeaId +
                ", cclassId=" + cclassId +
                '}';
    }
}
