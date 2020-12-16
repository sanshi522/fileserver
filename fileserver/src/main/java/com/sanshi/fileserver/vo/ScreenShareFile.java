package com.sanshi.fileserver.vo;

import com.sanshi.fileserver.bean.FileSample;
import com.sanshi.fileserver.bean.ShareFile;
import lombok.Data;

/**
 * 获取共享实例
 */
@Data
public class ScreenShareFile {
    /**
     * 单页显示数目
     */
    Integer pageNumber;
    /**
     * 页码
     */
    Integer pageIndex;
    /**
     * Query level查询级别
     */
    Integer queryLevel;
    /**
     * 级别id(0所有)
     */
    Integer issistId;
    /**
     * 文件条件
     */
    String likeName;
    /**
     * 排序方式
     */
    String sort;
    /**
     * 排序字段名
     */
    String sortName;

    public ScreenShareFile() {
    }

    public ScreenShareFile(Integer pageNumber, Integer pageIndex, Integer queryLevel, Integer issistId, String likeName, String sort, String sortName) {
        this.pageNumber = pageNumber;
        this.pageIndex = pageIndex;
        this.queryLevel = queryLevel;
        this.issistId = issistId;
        this.likeName = likeName;
        this.sort = sort;
        this.sortName = sortName;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getQueryLevel() {
        return queryLevel;
    }

    public void setQueryLevel(Integer queryLevel) {
        this.queryLevel = queryLevel;
    }

    public Integer getIssistId() {
        return issistId;
    }

    public void setIssistId(Integer issistId) {
        this.issistId = issistId;
    }

    public String getLikeName() {
        return likeName;
    }

    public void setLikeName(String likeName) {
        this.likeName = likeName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    @Override
    public String toString() {
        return "ScreenShareFile{" +
                "pageNumber=" + pageNumber +
                ", pageIndex=" + pageIndex +
                ", queryLevel=" + queryLevel +
                ", issistId=" + issistId +
                ", likeName='" + likeName + '\'' +
                ", sort='" + sort + '\'' +
                ", sortName='" + sortName + '\'' +
                '}';
    }
}
