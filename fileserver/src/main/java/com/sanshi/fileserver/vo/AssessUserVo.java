package com.sanshi.fileserver.vo;

import com.sanshi.fileserver.bean.Assess;
import com.sanshi.fileserver.bean.AssessUser;
import com.sanshi.fileserver.bean.TestPaper;

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


    public AssessUserVo(){}

    public AssessUserVo(Assess assess, List<AssessUser> listAssessUser) {
        this.assess = assess;
        this.listAssessUser = listAssessUser;
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

    @Override
    public String toString() {
        return "AssessUserVo{" +
                "assess=" + assess +
                ", listAssessUser=" + listAssessUser +
                '}';
    }
}
