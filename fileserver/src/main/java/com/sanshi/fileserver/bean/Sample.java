package com.sanshi.fileserver.bean;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Sample {

    private  Integer  id;

    private  Double referenceFreq ;


    private  Double  referencePower  ;

    private String   modulationType  ;

    private String  codType;

    private String protoType;

    private  String sourceType;

    private  String systemType;

    private  String tranModel;

    private  Integer    carrierNum;

    private  Integer  referenceTime;

    private  String fileName;

    private  Integer  fileNumber;

    private  String filePath;

    private  Long  fileSize;

    private Integer   playRate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date getData;

    private  String  name;

    public Sample() {
    }


    public Sample(Integer id, Double referenceFreq, Double referencePower, String modulationType, String codType, String protoType, String sourceType, String systemType, String tranModel, Integer carrierNum, Integer referenceTime, String fileName, Integer fileNumber, String filePath, Long fileSize, Integer playRate, Date getData, String name) {
        this.id = id;
        this.referenceFreq = referenceFreq;
        this.referencePower = referencePower;
        this.modulationType = modulationType;
        this.codType = codType;
        this.protoType = protoType;
        this.sourceType = sourceType;
        this.systemType = systemType;
        this.tranModel = tranModel;
        this.carrierNum = carrierNum;
        this.referenceTime = referenceTime;
        this.fileName = fileName;
        this.fileNumber = fileNumber;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.playRate = playRate;
        this.getData = getData;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getReferenceFreq() {
        return referenceFreq;
    }

    public void setReferenceFreq(Double referenceFreq) {
        this.referenceFreq = referenceFreq;
    }

    public Double getReferencePower() {
        return referencePower;
    }

    public void setReferencePower(Double referencePower) {
        this.referencePower = referencePower;
    }

    public String getModulationType() {
        return modulationType;
    }

    public void setModulationType(String modulationType) {
        this.modulationType = modulationType;
    }

    public String getCodType() {
        return codType;
    }

    public void setCodType(String codType) {
        this.codType = codType;
    }

    public String getProtoType() {
        return protoType;
    }

    public void setProtoType(String protoType) {
        this.protoType = protoType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getTranModel() {
        return tranModel;
    }

    public void setTranModel(String tranModel) {
        this.tranModel = tranModel;
    }

    public Integer getCarrierNum() {
        return carrierNum;
    }

    public void setCarrierNum(Integer carrierNum) {
        this.carrierNum = carrierNum;
    }

    public Integer getReferenceTime() {
        return referenceTime;
    }

    public void setReferenceTime(Integer referenceTime) {
        this.referenceTime = referenceTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(Integer fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getPlayRate() {
        return playRate;
    }

    public void setPlayRate(Integer playRate) {
        this.playRate = playRate;
    }

    public Date getGetData() {
        return getData;
    }

    public void setGetData(Date getData) {
        this.getData = getData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "id=" + id +
                ", referenceFreq=" + referenceFreq +
                ", referencePower=" + referencePower +
                ", modulationType='" + modulationType + '\'' +
                ", codType='" + codType + '\'' +
                ", protoType='" + protoType + '\'' +
                ", sourceType='" + sourceType + '\'' +
                ", systemType='" + systemType + '\'' +
                ", tranModel='" + tranModel + '\'' +
                ", carrierNum=" + carrierNum +
                ", referenceTime=" + referenceTime +
                ", fileName='" + fileName + '\'' +
                ", fileNumber=" + fileNumber +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", playRate=" + playRate +
                ", getData=" + getData +
                ", name='" + name + '\'' +
                '}';
    }
}
