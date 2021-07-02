package com.sanshi.fileserver.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sampleurl")
public class SampleUrl {

    private   String  ip;

    private  String  sampleIp;

    private  String  path;


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSampleIp() {
        return sampleIp;
    }

    public void setSampleIp(String sampleIp) {
        this.sampleIp = sampleIp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    @Override
    public String toString() {
        return "SampleUrl{" +
                "ip='" + ip + '\'' +
                ", sampleIp='" + sampleIp + '\'' +
                ", path='" + path + '\'' +
                '}';
    }


}
