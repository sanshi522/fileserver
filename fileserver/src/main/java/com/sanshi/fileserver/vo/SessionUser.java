package com.sanshi.fileserver.vo;

public class SessionUser {
    private Boolean isLogin;
    private Integer ident;
    private Integer userId;
    private String userName;

    @Override
    public String toString() {
        return "SessionUser{" +
                "isLogin=" + isLogin +
                ", ident=" + ident +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }

    public Boolean getLogin() {
        return isLogin;
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }

    public Integer getIdent() {
        return ident;
    }

    public void setIdent(Integer ident) {
        this.ident = ident;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
