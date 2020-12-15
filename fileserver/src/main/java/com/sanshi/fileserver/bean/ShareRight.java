package com.sanshi.fileserver.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 共享权限表
 */
@Entity
public class ShareRight {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer shareIdent;//权限人身份
    private Integer shareId;//权限人id
    private Integer shareFileId;//共享文件id
    private Date allottedTime;//到期时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShareIdent() {
        return shareIdent;
    }

    public void setShareIdent(Integer shareIdent) {
        this.shareIdent = shareIdent;
    }

    public Integer getShareId() {
        return shareId;
    }

    public void setShareId(Integer shareId) {
        this.shareId = shareId;
    }

    public Integer getShareFileId() {
        return shareFileId;
    }

    public void setShareFileId(Integer shareFileId) {
        this.shareFileId = shareFileId;
    }

    public Date getAllottedTime() {
        return allottedTime;
    }

    public void setAllottedTime(Date allottedTime) {
        this.allottedTime = allottedTime;
    }

    public ShareRight(Integer id, Integer shareIdent, Integer shareId, Integer shareFileId, Date allottedTime) {
        this.id = id;
        this.shareIdent = shareIdent;
        this.shareId = shareId;
        this.shareFileId = shareFileId;
        this.allottedTime = allottedTime;
    }

    @Override
    public String toString() {
        return "ShareRight{" +
                "id=" + id +
                ", shareIdent=" + shareIdent +
                ", shareId=" + shareId +
                ", shareFileId=" + shareFileId +
                ", allottedTime=" + allottedTime +
                '}';
    }
}
