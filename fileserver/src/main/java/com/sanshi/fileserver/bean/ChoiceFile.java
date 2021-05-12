package com.sanshi.fileserver.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class ChoiceFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer choiceId;

    private Integer type;

    private  Integer fileId;

    public ChoiceFile() {
    }

    public ChoiceFile(Integer id, Integer choiceId, Integer type, Integer fileId) {
        this.id = id;
        this.choiceId = choiceId;
        this.type = type;
        this.fileId = fileId;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "ChoiceFile{" +
                "id=" + id +
                ", choiceId=" + choiceId +
                ", type=" + type +
                '}';
    }
}
