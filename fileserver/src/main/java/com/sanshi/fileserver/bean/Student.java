package com.sanshi.fileserver.bean;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

//@Table(name = "student")//用来命名当前实体类对应数据库的名字
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer stuId;
    private String stuNumber;
    private String stuName;
    private String stuGender;
    private String stuPass;
    private String stuHead;
    private Integer stuGroup;
    private Integer stuState;
    private String stuRemake;
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
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
