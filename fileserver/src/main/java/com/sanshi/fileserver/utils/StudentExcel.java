package com.sanshi.fileserver.utils;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class StudentExcel {
    @Excel(name = "学号",width = 20,orderNum="1")
    private String stuNumber;
    @Excel(name = "姓名",width = 10,orderNum="2")
    private String stuName;
    @Excel(name = "性别",width = 10,orderNum="3")
    private String stuGender;
    @Excel(name = "密码",width = 10,orderNum="4")
    private String stuPass;
    @Excel(name = "小组",width = 10,orderNum="5")
    private String stuGroup;
    @Excel(name = "状态",width = 10,orderNum="6",replace={"在读_1","毕业_0"})
    private Integer stuState;
    @Excel(name = "备注",width = 10,orderNum="7")
    private String stuRemake;


    public String getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuGender() {
        return stuGender;
    }

    public void setStuGender(String stuGender) {
        this.stuGender = stuGender;
    }

    public String getStuPass() {
        return stuPass;
    }

    public void setStuPass(String stuPass) {
        this.stuPass = stuPass;
    }


    public Integer getStuState() {
        return stuState;
    }

    public void setStuState(Integer stuState) {
        this.stuState = stuState;
    }

    public String getStuRemake() {
        return stuRemake;
    }

    public void setStuRemake(String stuRemake) {
        this.stuRemake = stuRemake;
    }


    public String getStuGroup() {
        return stuGroup;
    }

    public void setStuGroup(String stuGroup) {
        this.stuGroup = stuGroup;
    }

    public StudentExcel() {
    }

    public StudentExcel(String stuNumber, String stuName, String stuGender, String stuPass, String stuGroup, Integer stuState, String stuRemake) {
        this.stuNumber = stuNumber;
        this.stuName = stuName;
        this.stuGender = stuGender;
        this.stuPass = stuPass;
        this.stuGroup = stuGroup;
        this.stuState = stuState;
        this.stuRemake = stuRemake;
    }
}
