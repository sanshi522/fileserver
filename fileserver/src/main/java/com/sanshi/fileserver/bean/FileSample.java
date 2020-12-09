package com.sanshi.fileserver.bean;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 文件碎片实体类
 */

@Data
@Entity
public class FileSample {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(length = 50)
    private Integer id;
    @Column(length = 10)
    private Integer patchIndex;
    @Column(length = 50)
    private Integer parent;
    @Column(length = 200)
    private String name;
    @Column(length = 500)
    private String path;
    @Column(length = 50,nullable = false)
    private String md5;
    private Long size;
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Date createTime;

    public FileSample() {

    }

    public FileSample(Integer patchIndex, Integer parent, String name, String path, String md5, Long size,Date createTime) {
        this.patchIndex = patchIndex;
        this.parent = parent;
        this.name = name;
        this.path = path;
        this.md5 = md5;
        this.size = size;
        this.createTime=createTime;
    }

    public FileSample(Integer id, Integer patchIndex, Integer parent, String name, String path, String md5, Long size, Date createTime) {
        this.id = id;
        this.patchIndex = patchIndex;
        this.parent = parent;
        this.name = name;
        this.path = path;
        this.md5 = md5;
        this.size = size;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "FileSample{" +
                "id=" + id +
                ", patchIndex=" + patchIndex +
                ", parent=" + parent +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", md5='" + md5 + '\'' +
                ", size=" + size +
                ", createTime=" + createTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatchIndex() {
        return patchIndex;
    }

    public void setPatchIndex(Integer patchIndex) {
        this.patchIndex = patchIndex;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
