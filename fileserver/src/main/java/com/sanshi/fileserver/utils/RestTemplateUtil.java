package com.sanshi.fileserver.utils;

import com.github.pagehelper.PageInfo;
import com.sanshi.fileserver.bean.Sample;
import com.sanshi.fileserver.vo.Page;
import com.sanshi.fileserver.vo.PageGet;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import java.util.Map;

public class RestTemplateUtil {
    private static String ip= PropertiesUtil.getProperty("application.properties", "ip");
    //SpringBoot项目访问别的SpringBoot项目的接口
    private static String sampleIp= PropertiesUtil.getProperty("application.properties", "sampleIp");
    //学生文件上传保存地址
    private static String path= PropertiesUtil.getProperty("application.properties", "path");


    public static PageInfo executePost(String url, Page page){
        String URL=ip+url;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Page> request = new HttpEntity<>(page, headers);

        ResponseEntity<PageInfo> response = restTemplate.postForEntity(URL, request , PageInfo.class );

        PageInfo data=response.getBody();

        return data;

    }
    public static Sample findByIdPost(String url, Integer id){
        String URL=ip+url;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Integer> request = new HttpEntity<>(id, headers);

        ResponseEntity<Sample> response = restTemplate.postForEntity(URL, request , Sample.class );

        Sample data=response.getBody();

        return data;

    }

    public static Sample findFile(String url, String fileName){
        String URL=ip+url;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> request = new HttpEntity<>(fileName, headers);

        ResponseEntity<Sample> response = restTemplate.postForEntity(URL, request , Sample.class );

        Sample data=response.getBody();

        return data;

    }

    public static String sampleFileName(){
        return sampleIp;

    }

    public static String getPath(){
        return path;

    }








}
