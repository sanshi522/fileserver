package com.sanshi.fileserver.utils;

public class FileUtil {
    private  String  name ;
    private  int     type;
    private  String  fileName;

    public FileUtil() {

    }

    public FileUtil(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public FileUtil(String name, int type, String fileName) {
        this.name = name;
        this.type = type;
        this.fileName = fileName;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "FileUtil{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
