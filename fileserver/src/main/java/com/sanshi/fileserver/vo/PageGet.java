package com.sanshi.fileserver.vo;

import lombok.Data;

@Data
public class PageGet {
    private Integer pageNumber;
    private Integer pageIndex;
    private Integer issistId;
    private String likeName;

    public PageGet() {
    }

    public PageGet(Integer pageNumber, Integer pageIndex, Integer issistId, String likeName) {
        this.pageNumber = pageNumber;
        this.pageIndex = pageIndex;
        this.issistId = issistId;
        this.likeName = likeName;
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

    @Override
    public String toString() {
        return "PageGet{" +
                "pageNumber=" + pageNumber +
                ", pageIndex=" + pageIndex +
                ", issistId=" + issistId +
                ", likeName='" + likeName + '\'' +
                '}';
    }
}
