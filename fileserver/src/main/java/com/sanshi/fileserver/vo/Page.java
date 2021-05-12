package com.sanshi.fileserver.vo;

public class Page {
    private   int    pageIndex;
    private   int   pageNumber;


    public Page() {
    }

    public Page(int pageIndex, int pageNumber) {
        this.pageIndex = pageIndex;
        this.pageNumber = pageNumber;
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
