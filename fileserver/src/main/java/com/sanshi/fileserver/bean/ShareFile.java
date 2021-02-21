package com.sanshi.fileserver.bean;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

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
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date uapdateTime;

    public ShareFile() {
    }

    public ShareFile(Integer id, Integer ownerIdent, Integer ownerId, Integer fileId, Date createTime, Date uapdateTime) {
        this.id = id;
        this.ownerIdent = ownerIdent;
        this.ownerId = ownerId;
        this.fileId = fileId;
        this.createTime = createTime;
        this.uapdateTime = uapdateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwnerIdent() {
        return ownerIdent;
    }

    public void setOwnerIdent(Integer ownerIdent) {
        this.ownerIdent = ownerIdent;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
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
        return "ShareFile{" +
                "id=" + id +
                ", ownerIdent=" + ownerIdent +
                ", ownerId=" + ownerId +
                ", fileId=" + fileId +
                ", createTime=" + createTime +
                ", uapdateTime=" + uapdateTime +
                '}';
    }
}
