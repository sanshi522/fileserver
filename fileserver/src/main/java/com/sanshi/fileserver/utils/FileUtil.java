package com.sanshi.fileserver.utils;

public class FileUtil {
    private  String  name ;
    private  int     type;


    public FileUtil() {
    }

    public FileUtil(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "FileUtil{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
