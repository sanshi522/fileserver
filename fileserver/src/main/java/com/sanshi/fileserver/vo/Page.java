package com.sanshi.fileserver.vo;

public class Page {
    private   int    pageIndex;
    private   int   pageNumber;
    private  String  likeName;


    public Page() {
    }

    public Page(int pageIndex, int pageNumber, String likeName) {
        this.pageIndex = pageIndex;
        this.pageNumber = pageNumber;
        this.likeName = likeName;
    }

    public String getLikeName() {
        return likeName;
    }

    public void setLikeName(String likeName) {
        this.likeName = likeName;
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




}
