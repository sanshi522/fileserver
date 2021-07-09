package com.sanshi.fileserver.utils;

import com.github.pagehelper.PageInfo;
import com.sanshi.fileserver.bean.Sample;
import com.sanshi.fileserver.vo.Page;
import com.sanshi.fileserver.vo.PageGet;
import com.sanshi.fileserver.vo.SamplePage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.util.Map;

@Component
public class RestTemplateUtil {


    public static PageInfo executePost(String url, SamplePage page){
        String URL=url;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<SamplePage> request = new HttpEntity<>(page, headers);

        ResponseEntity<PageInfo> response = restTemplate.postForEntity(URL, request , PageInfo.class );

        PageInfo data=response.getBody();

        return data;

    }
    public static Sample findByIdPost(String url, Integer id){
        String URL=url;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Integer> request = new HttpEntity<>(id, headers);

        ResponseEntity<Sample> response = restTemplate.postForEntity(URL, request , Sample.class );

        Sample data=response.getBody();

        return data;

    }

    public static Sample findFile(String url, String fileName){
        String URL=url;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> request = new HttpEntity<>(fileName, headers);

        ResponseEntity<Sample> response = restTemplate.postForEntity(URL, request , Sample.class );

        Sample data=response.getBody();

        return data;

    }










}
