package com.sanshi.fileserver.vo;

public class SamplePage {
    private   int    pageIndex;
    private   int   pageNumber;
    private   String likeName;
    private  Integer  type;

    public SamplePage() {
    }

    public SamplePage(int pageIndex, int pageNumber, String likeName, Integer type) {
        this.pageIndex = pageIndex;
        this.pageNumber = pageNumber;
        this.likeName = likeName;
        this.type = type;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getLikeName() {
        return likeName;
    }

    public void setLikeName(String likeName) {
        this.likeName = likeName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
