package com.sanshi.fileserver.vo;

import com.sanshi.fileserver.bean.Assess;
import com.sanshi.fileserver.bean.AssessUser;

import java.util.List;

public class AssessUserVo {
    /**
     * 考核
     */
  private   Assess assess ;
    /**
     * 考核对象
     */
  private List<AssessUser>  listAssessUser;

    /**
     * 单页显示数目
     */
    private Integer pageNumber;
    /**
     * 页码
     */
    private Integer pageIndex;

    public AssessUserVo(){}

    public AssessUserVo(Assess assess, List<AssessUser> listAssessUser, Integer pageNumber, Integer pageIndex) {
        this.assess = assess;
        this.listAssessUser = listAssessUser;
        this.pageNumber = pageNumber;
        this.pageIndex = pageIndex;
    }

    public Assess getAssess() {
        return assess;
    }

    public void setAssess(Assess assess) {
        this.assess = assess;
    }

    public List<AssessUser> getListAssessUser() {
        return listAssessUser;
    }

    public void setListAssessUser(List<AssessUser> listAssessUser) {
        this.listAssessUser = listAssessUser;
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


    @Override
    public String toString() {
        return "AssessUserVo{" +
                "assess=" + assess +
                ", listAssessUser=" + listAssessUser +
                ", pageNumber=" + pageNumber +
                ", pageIndex=" + pageIndex +
                '}';
    }
}
