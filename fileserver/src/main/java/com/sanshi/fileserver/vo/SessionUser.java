package com.sanshi.fileserver.vo;

import com.sanshi.fileserver.bean.Student;
import com.sanshi.fileserver.bean.Teacher;

public class SessionUser {
    private Boolean isLogin;
    private Integer logintype;
    private Student student;
    private Teacher teacher;

    public SessionUser() {
    }

    public SessionUser(Boolean isLogin, Integer logintype, Student student, Teacher teacher) {
        this.isLogin = isLogin;
        this.logintype = logintype;
        this.student = student;
        this.teacher = teacher;
    }

    public Boolean getLogin() {
        return isLogin;
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }

    public Integer getLogintype() {
        return logintype;
    }

    public void setLogintype(Integer logintype) {
        this.logintype = logintype;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "SessionUser{" +
                "isLogin=" + isLogin +
                ", logintype=" + logintype +
                ", student=" + student +
                ", teacher=" + teacher +
                '}';
    }
}
