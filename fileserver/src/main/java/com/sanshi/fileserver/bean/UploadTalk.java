package com.sanshi.fileserver.bean;

import javax.persistence.*;

@Entity
public class UploadTalk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer talkId;
    private Integer talkUserIdent;
    private Integer talkUserId;
    /**
     * 设备id
     */
    private String deviceId;
    @Column(length = 500)
    private String devicePath;
    private Integer talkState;

    public UploadTalk(Integer talkId, Integer talkUserIdent, Integer talkUserId, String deviceId, String devicePath, Integer talkState) {
        this.talkId = talkId;
        this.talkUserIdent = talkUserIdent;
        this.talkUserId = talkUserId;
        this.deviceId = deviceId;
        this.devicePath = devicePath;
        this.talkState = talkState;
    }


    public void setTalkId(Integer talkId) {
        this.talkId = talkId;
    }

    public void setTalkUserIdent(Integer talkUserIdent) {
        this.talkUserIdent = talkUserIdent;
    }

    public void setTalkUserId(Integer talkUserId) {
        this.talkUserId = talkUserId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setDevicePath(String devicePath) {
        this.devicePath = devicePath;
    }

    public void setTalkState(Integer talkState) {
        this.talkState = talkState;
    }

    public Integer getTalkId() {
        return talkId;
    }

    public Integer getTalkUserIdent() {
        return talkUserIdent;
    }

    public Integer getTalkUserId() {
        return talkUserId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDevicePath() {
        return devicePath;
    }

    public Integer getTalkState() {
        return talkState;
    }

    @Override
    public String toString() {
        return "UploadTalk{" +
                "talkId=" + talkId +
                ", talkUserIdent=" + talkUserIdent +
                ", talkUserId=" + talkUserId +
                ", deviceId='" + deviceId + '\'' +
                ", devicePath='" + devicePath + '\'' +
                ", talkState=" + talkState +
                '}';
    }
}
