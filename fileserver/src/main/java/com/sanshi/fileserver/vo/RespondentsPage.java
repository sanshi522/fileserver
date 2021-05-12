package com.sanshi.fileserver.vo;

public class RespondentsPage {
    /**
     * 单页显示数目
     */
    private Integer pageNumber;
    /**
     * 页码
     */
    private Integer pageIndex;
    /***
     * 状态
     */
    private Integer type;

    /**
     * 考核Id
     */
    private Integer assessId;


    public RespondentsPage() {
    }


    public RespondentsPage(Integer pageNumber, Integer pageIndex, Integer type, Integer assessId) {
        this.pageNumber = pageNumber;
        this.pageIndex = pageIndex;
        this.type = type;
        this.assessId = assessId;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAssessId() {
        return assessId;
    }

    public void setAssessId(Integer assessId) {
        this.assessId = assessId;
    }

    @Override
    public String toString() {
        return "RespondentsPage{" +
                "pageNumber=" + pageNumber +
                ", pageIndex=" + pageIndex +
                ", type=" + type +
                ", assessId=" + assessId +
                '}';
    }
}

