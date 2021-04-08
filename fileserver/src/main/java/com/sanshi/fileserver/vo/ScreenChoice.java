package com.sanshi.fileserver.vo;

import lombok.Data;

@Data
public class ScreenChoice {
    /**
     * 单页显示数目
     */
    private Integer pageNumber;
    /**
     * 页码
     */
    private Integer pageIndex;
    /**
     * 学科id
     */
    private Integer subId;


    /**
     * 类型
     */
    private Integer type;
    /**
     * 查询名字
     */
    private String name;
    /**
     * 排序方式
     */
    String sort;
    /**
     * 排序字段名
     */
    String sortName;

    /**
     * 知识点ID
     */
    private String abilityIds;

    /**
     * 难度级别
     */
    private Integer difficultyLevel;


    public ScreenChoice() {
    }

    public ScreenChoice(Integer pageNumber, Integer pageIndex, Integer subId, Integer type, String name, String sort, String sortName) {
        this.pageNumber = pageNumber;
        this.pageIndex = pageIndex;
        this.subId = subId;
        this.type = type;
        this.name = name;
        this.sort = sort;
        this.sortName = sortName;
    }

    public ScreenChoice(Integer pageNumber, Integer pageIndex, Integer subId, Integer type, String name, String sort, String sortName, String abilityIds, Integer difficultyLevel) {
        this.pageNumber = pageNumber;
        this.pageIndex = pageIndex;
        this.subId = subId;
        this.name = name;
        this.sort = sort;
        this.sortName = sortName;
        this.abilityIds=abilityIds;
        this.difficultyLevel=difficultyLevel;
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
    public String getAbilityIds() {
        return abilityIds;
    }

    public Integer getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setAbilityIds(String abilityIds) {
        this.abilityIds = abilityIds;
    }

    public void setDifficultyLevel(Integer difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "ScreenChoice{" +
                "pageNumber=" + pageNumber +
                ", pageIndex=" + pageIndex +
                ", subId=" + subId +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", sort='" + sort + '\'' +
                ", sortName='" + sortName + '\'' +
                '}';
    }
}
