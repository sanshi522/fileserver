package com.sanshi.fileserver.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
//@Table(name = "student")//用来命名当前实体类对应数据库的名字
@Entity
public class Student implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer stuId;
    @Excel(name = "学号",width = 20,orderNum="1")
    private String stuNumber;
    @Excel(name = "姓名",width = 10,orderNum="2")
    private String stuName;
    @Excel(name = "性别",width = 10,orderNum="3")
    private String stuGender;
    @Excel(name = "密码",width = 10,orderNum="4")
    private String stuPass;
    @Excel(name = "头部",width = 10,orderNum="5")
    private String stuHead;
    @Excel(name = "小组",width = 10,orderNum="6")
    private Integer stuGroup;
    @Excel(name = "。。",width = 10,orderNum="7")
    private Integer stuState;
    @Excel(name = "备注",width = 10,orderNum="8")
    private String stuRemake;
    @CreationTimestamp
    @Excel(name = "创建时间",width = 10,exportFormat = "yyyy-MM-dd",orderNum="9")
    private Date createTime;
    @UpdateTimestamp
    @Excel(name = "修改时间",width = 10,exportFormat = "yyyy-MM-dd",orderNum="10")
    private Date uapdateTime;

    public Student() {
    }

    public Student(Integer stuId, String stuNumber, String stuName, String stuGender, String stuPass, String stuHead, Integer stuGroup, Integer stuState, String stuRemake) {
        this.stuId = stuId;
        this.stuNumber = stuNumber;
        this.stuName = stuName;
        this.stuGender = stuGender;
        this.stuPass = stuPass;
        this.stuHead = stuHead;
        this.stuGroup = stuGroup;
        this.stuState = stuState;
        this.stuRemake = stuRemake;
    }

    public Student(Integer stuId, String stuNumber, String stuName, String stuGender, String stuPass, String stuHead, Integer stuGroup, Integer stuState, String stuRemake, Date createTime, Date uapdateTime) {
        this.stuId = stuId;
        this.stuNumber = stuNumber;
        this.stuName = stuName;
        this.stuGender = stuGender;
        this.stuPass = stuPass;
        this.stuHead = stuHead;
        this.stuGroup = stuGroup;
        this.stuState = stuState;
        this.stuRemake = stuRemake;
        this.createTime = createTime;
        this.uapdateTime = uapdateTime;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

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

    public String getStuHead() {
        return stuHead;
    }

    public void setStuHead(String stuHead) {
        this.stuHead = stuHead;
    }

    public Integer getStuGroup() {
        return stuGroup;
    }

    public void setStuGroup(Integer stuGroup) {
        this.stuGroup = stuGroup;
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


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUapdateTime() {
        return uapdateTime;
    }

    public void setUapdateTime(Date uapdateTime) {
        this.uapdateTime = uapdateTime;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuId=" + stuId +
                ", stuNumber='" + stuNumber + '\'' +
                ", stuName='" + stuName + '\'' +
                ", stuGender='" + stuGender + '\'' +
                ", stuPass='" + stuPass + '\'' +
                ", stuHead='" + stuHead + '\'' +
                ", stuGroup=" + stuGroup +
                ", stuState=" + stuState +
                ", stuRemake='" + stuRemake + '\'' +
                '}';
    }
}
