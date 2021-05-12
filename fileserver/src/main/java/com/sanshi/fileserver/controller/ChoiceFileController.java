package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.ChoiceFile;
import com.sanshi.fileserver.bean.FileSample;
import com.sanshi.fileserver.bean.Sample;
import com.sanshi.fileserver.service.ChoiceFileService;
import com.sanshi.fileserver.service.FileSampleService;
import com.sanshi.fileserver.utils.RestTemplateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ChoiceFile")
public class ChoiceFileController {

    private ChoiceFileService  choiceFileService;
    private FileSampleService fileSampleService;

    public ChoiceFileController(ChoiceFileService choiceFileService, FileSampleService fileSampleService) {
        this.choiceFileService = choiceFileService;
        this.fileSampleService = fileSampleService;
    }

    @RequestMapping("/findByChoiceId")
    @ResponseBody
    public List<ChoiceFile> findByChoiceId(Integer choiceId){

     return    choiceFileService.findByChoiceId(choiceId);
    }
    @RequestMapping("/findFileName")
    @ResponseBody
    public List<String>findFileName (Integer choiceId){

   List<ChoiceFile>  choiceFileList=   choiceFileService.findByChoiceId(choiceId);
   List<String>  list=new ArrayList<>();
        for (ChoiceFile   file:choiceFileList) {
            if(file.getType()==0){
                FileSample   fileSample=fileSampleService.findById(file.getFileId());
                list.add(fileSample.getPath().replaceAll("\\\\","/"));
            }else {
                String url="/Sample/externalFindById";
                Sample data= RestTemplateUtil.findByIdPost(url,file.getFileId());
                String fileName=data.getFileName().substring(0,data.getFileName().lastIndexOf("."));
                String suffix= data.getFileName().substring(data.getFileName().lastIndexOf("."),data.getFileName().length());
                for(int i=1;i<=data.getFileNumbe();i++){
                    String name=data.getFilePath()+"/"+fileName+"_"+i+suffix;
                    //String name=RestTemplateUtil.sampleFileName()+"/"+data.getFilePath()+"/"+fileName+"_"+i+suffix;  配置远程需要做的
                    list.add(name);
                }
            }

        }
        System.out.print(list);

        return  list;
    }




}
