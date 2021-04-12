package com.sanshi.fileserver.vo;

import lombok.Data;

@Data
public class ScreenAssess {
    /**
     * 考核名称
     */
    private  String  name;

    /**
     * 考核学年
     */
    private  String  year;

    /**
     * 考核学科
     */
    private  Integer subId;

    /**
     * 考核学院
     */
  private  Integer  gradeId;

    /**
     * 考核班级
     */

    private Integer classId;

    /**
     * 单页显示数目
     */
    private Integer pageNumber;
    /**
     * 页码
     */
    private Integer pageIndex;


    public ScreenAssess() {

    }
    public ScreenAssess(String name, Integer subId, Integer gradeId, Integer classId, Integer pageNumber, Integer pageIndex) {
        this.name = name;
        this.subId = subId;
        this.gradeId = gradeId;
        this.classId = classId;
        this.pageNumber = pageNumber;
        this.pageIndex = pageIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
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
        return "ScreenAssess{" +
                "name='" + name + '\'' +
                ", subId=" + subId +
                ", gradeId=" + gradeId +
                ", classId=" + classId +
                ", pageNumber=" + pageNumber +
                ", pageIndex=" + pageIndex +
                '}';
    }
}
