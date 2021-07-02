package com.sanshi.fileserver.controller;

import com.github.pagehelper.PageInfo;
import com.sanshi.fileserver.bean.Sample;
import com.sanshi.fileserver.bean.SampleUrl;
import com.sanshi.fileserver.utils.RestTemplateUtil;
import com.sanshi.fileserver.vo.Page;
import com.sanshi.fileserver.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;


@Controller
@RequestMapping("/Sample")
public class SampleController {

    @Value("${sampleurl.ip}")
    private   String ip;

    @Value("${sampleurl.sampleIp}")
    private  String sampleIp;


 @RequestMapping("/findAll")
 @ResponseBody
  public  PageInfo   findAll(@RequestBody  Page page){
        String url=ip+"/Sample/find";
        PageInfo data= RestTemplateUtil.executePost(url,page);
    return   data;
  }


    @RequestMapping("/findById")
    @ResponseBody
    public Sample findById(Integer id){
        String url=ip+"/Sample/externalFindById";
        Sample data= RestTemplateUtil.findByIdPost(url,id);
        return   data;
    }



    @RequestMapping("/sampleUrl")
    @ResponseBody
    public String sampleUrl(){
       String data= sampleIp;
        return  data;
    }



    @RequestMapping("/downloadShareFile")
    public String downLoad(String filename, HttpServletResponse response) throws UnsupportedEncodingException {
        File file = new File(filename);
        String name = filename;
        if (filename.lastIndexOf("/") >= 0) {
            String str1 = filename.substring(0, filename.lastIndexOf("/"));
            name = filename.substring(str1.length() + 1, filename.length());
        }
        if (file.exists()) { //判断文件父目录是否存在
            //response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(name, "UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;
            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("----------file download---" + filename);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }






}
