package com.sanshi.fileserver.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 共享文件
 */
@Entity
public class ShareFile {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer ownerIdent;//所属人身份
    private Integer ownerId;//所属人id
    private Integer fileId;//文件id

    @Override
    public String toString() {
        return "ShareFile{" +
                "id=" + id +
                ", ownerIdent=" + ownerIdent +
                ", ownerId=" + ownerId +
                ", fileId=" + fileId +
                '}';
    }

    public ShareFile() {
    }

    public ShareFile(Integer id, Integer ownerIdent, Integer ownerId, Integer fileId) {
        this.id = id;
        this.ownerIdent = ownerIdent;
        this.ownerId = ownerId;
        this.fileId = fileId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getOwnerIdent() {
        return ownerIdent;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOwnerIdent(Integer ownerIdent) {
        this.ownerIdent = ownerIdent;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }
}
