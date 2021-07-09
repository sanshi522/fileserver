package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.ChoiceFile;
import com.sanshi.fileserver.bean.FileSample;
import com.sanshi.fileserver.bean.Sample;
import com.sanshi.fileserver.service.ChoiceFileService;
import com.sanshi.fileserver.service.FileSampleService;
import com.sanshi.fileserver.utils.FileUtil;
import com.sanshi.fileserver.utils.RestTemplateUtil;
import com.sanshi.fileserver.vo.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ChoiceFile")
@CrossOrigin
public class ChoiceFileController {
    @Value("${sampleurl.ip}")
    private   String ip;

    private ChoiceFileService  choiceFileService;
    private FileSampleService fileSampleService;

    public ChoiceFileController(ChoiceFileService choiceFileService, FileSampleService fileSampleService) {
        this.choiceFileService = choiceFileService;
        this.fileSampleService = fileSampleService;
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
                String url=ip+"/Sample/externalFindById";
                RestTemplateUtil restTemplateUtil=new RestTemplateUtil();
                Sample data= restTemplateUtil.findByIdPost(url,file.getFileId());
                String fileName=data.getFileName().substring(0,data.getFileName().lastIndexOf("."));
                String suffix= data.getFileName().substring(data.getFileName().lastIndexOf("."),data.getFileName().length());
                for(int i=1;i<=data.getFileNumber();i++){
                    String name=data.getFilePath()+"/"+fileName+"_"+i+suffix;
                    //String name=RestTemplateUtil.sampleFileName()+"/"+data.getFilePath()+"/"+fileName+"_"+i+suffix;  配置远程需要做的
                    list.add(name);
                }
            }

        }
        System.out.print(list);

        return  list;
    }


    @RequestMapping("/findFileName2")
    @ResponseBody
    public List<FileUtil>findFileName2(Integer choiceId){
        List<ChoiceFile>  choiceFileList=   choiceFileService.findByChoiceId(choiceId);
        List<FileUtil>  list=new ArrayList<>();
        for (ChoiceFile   file:choiceFileList) {
            if(file.getType()==0){
                FileSample   fileSample=fileSampleService.findById(file.getFileId());
                if(fileSample==null){
                    continue;
                }
                list.add( new FileUtil(fileSample.getPath().replaceAll("\\\\","/"),0,fileSample.getName()));
            }else {
                String url=ip+"/Sample/externalFindById";
                Sample data= RestTemplateUtil.findByIdPost(url,file.getFileId());
                if (data==null){
                    continue;
                }
                String fileName=data.getName().substring(0,data.getName().lastIndexOf("."));
                String suffix= data.getName().substring(data.getName().lastIndexOf("."),data.getName().length());
                for(int i=0;i<data.getFileNumber();i++){
                    String name=data.getFilePath()+"/"+fileName+"_"+i+suffix;
                    list.add(new FileUtil( name,1,data.getFileName()+"_"+i+suffix));
                }
            }
        }
        System.out.print(list);

        return  list;
    }


    @RequestMapping("/findDelete")
    @ResponseBody
    @CrossOrigin
    public Result findDelete(HttpServletResponse response, Integer id){
     List<ChoiceFile>  choiceFileList=    choiceFileService.findByTypeAndFileId(1,id);
    if (choiceFileList.size()>0){
        return new Result(false,"该样本文件已绑定试题不可删除");
    }
        return  new  Result(true,"可以删除");
    }



    @CrossOrigin
    @RequestMapping("/findDelete2")
    @ResponseBody
    public Result  externalFindById(HttpEntity<Integer> request){
        Integer  id=request.getBody();
        List<ChoiceFile>  choiceFileList=    choiceFileService.findByTypeAndFileId(1,id);
        if (choiceFileList.size()>0){
            return new Result(false,"该样本文件已绑定试题不可删除");
        }
        return  new  Result(true,"可以删除");

    }


}
