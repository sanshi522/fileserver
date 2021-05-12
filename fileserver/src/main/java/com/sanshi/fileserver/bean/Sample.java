package com.sanshi.fileserver.bean;


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

    private  int    carrierNum;

    private  int  referenceTime;

    private  String fileName;

    private  int  fileNumbe;

    private  String filePath;

    private  int  fileSize;

    private String thumbnailPath;

    private int   playRate;

    public Sample(Integer id, Double referenceFreq, Double referencePower, String modulationType, String codType, String protoType, String sourceType, String systemType, String tranModel, int carrierNum, int referenceTime, String fileName, int fileNumbe, String filePath, int fileSize, String thumbnailPath, int playRate) {
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
        this.fileNumbe = fileNumbe;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.thumbnailPath = thumbnailPath;
        this.playRate = playRate;
    }

    public Sample() {
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

    public int getCarrierNum() {
        return carrierNum;
    }

    public void setCarrierNum(int carrierNum) {
        this.carrierNum = carrierNum;
    }

    public int getReferenceTime() {
        return referenceTime;
    }

    public void setReferenceTime(int referenceTime) {
        this.referenceTime = referenceTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileNumbe() {
        return fileNumbe;
    }

    public void setFileNumbe(int fileNumbe) {
        this.fileNumbe = fileNumbe;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public int getPlayRate() {
        return playRate;
    }

    public void setPlayRate(int playRate) {
        this.playRate = playRate;
    }
}
