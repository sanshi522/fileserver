package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.ChoiceFile;
import com.sanshi.fileserver.bean.FileSample;
import com.sanshi.fileserver.service.ChoiceFileService;
import com.sanshi.fileserver.service.ChoiceService;
import com.sanshi.fileserver.service.FileSampleService;
import com.sanshi.fileserver.utils.UploadUtil;
import com.sanshi.fileserver.vo.FileExists;
import com.sanshi.fileserver.vo.Result;
import com.sanshi.fileserver.vo.ScreenShareFile;
import com.sanshi.fileserver.vo.SessionUser;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/file")
public class FileSampleController {
    private FileSampleService fileSampleService;
    private ChoiceService  choiceService;
    private ChoiceFileService  choiceFileService;

    public FileSampleController(FileSampleService fileSampleService, ChoiceService choiceService, ChoiceFileService choiceFileService) {
        this.fileSampleService = fileSampleService;
        this.choiceService = choiceService;
        this.choiceFileService = choiceFileService;
    }

    @GetMapping("/exists")
    @ResponseBody
    public FileExists fileExists(String md5, Long size) {
        return fileSampleService.fileExists(md5, size);
    }

    @PostMapping("/new")
    @ResponseBody
    public FileSample fileExists(@RequestBody FileSample file) {
        return fileSampleService.insertFileSample(file);
    }

    @PostMapping("/patch/upload")
    @ResponseBody
    public Result filePatchExists(String name, Integer index, Integer parent, String md5, Long size, MultipartFile patch, HttpServletRequest request) throws IOException {
        FileSample file = fileSampleService.findByParentAndMd5(parent, md5);
        if (file == null || !file.getSize().equals(size)) {
            Optional.ofNullable(file).ifPresent(e -> fileSampleService.deleteById(e.getId()));
            fileSampleService.insertFileSample(new FileSample(index, parent, name, UploadUtil.saveFile(patch, size), md5, size, null, null));
            return Result.OK();
        }
        return file.getSize().equals(size) ? Result.OK() : Result.FAIL();
    }

    @Transactional
    @PostMapping("/patch/merge")
    @ResponseBody
    public Result filePatchMerge(Integer parent, Long size) throws IOException {
        FileSample fileInfo = fileSampleService.findById(parent);
        List<FileSample> patchs = fileSampleService.findByParentOrderByPatchIndexAsc(parent);
        Long total = patchs.stream().mapToLong(FileSample::getSize).sum();
        if (fileInfo == null || CollectionUtils.isEmpty(patchs) || !total.equals(size)) {
            fileSampleService.deleteByParent(parent);
            //log.warn("total: {}, require size: {}, and delete file to retry !", total, size);
            return Result.FAIL();
        }
        String fileType = UploadUtil.parseFileType(fileInfo.getName());
        String path = UploadUtil.mergeFile(fileType, patchs.stream().map(FileSample::getPath).collect(Collectors.toList()));
        fileSampleService.updateByIdSetPathAndSize(parent, path, total);
        fileSampleService.deleteByParent(parent);
        return Result.OK();
    }

    @PostMapping("/getAllShareFile")
    @ResponseBody
    public Map getAllShareFile(@RequestBody ScreenShareFile screenShareFile, HttpServletRequest request) {
        return fileSampleService.ScreenALL(screenShareFile, request);
    }

    @PostMapping("/getAllMyShareFile")
    @ResponseBody
    public Map getAllMyShareFile(@RequestBody ScreenShareFile screenShareFile, HttpServletRequest request) {
        return fileSampleService.ScreenMyALL(screenShareFile, request);
    }

    @RequestMapping("/downloadShareFile")
    public String downLoad(Integer fileId, HttpServletResponse response) throws UnsupportedEncodingException {
        FileSample fileSample = fileSampleService.findById(fileId);
        // String filename="navicatformysql.zip";
        String filename = fileSample.getName();
        File file = new File(fileSample.getPath());
        //String filePath = "D:/软件安装包/数据库管理工具" ;
        //File file = new File(filePath + "/" + filename);
        if (file.exists()) { //判断文件父目录是否存在
            //response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(filename, "UTF-8"));
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

    @RequestMapping("/downloadShareFile2")
    public String downLoad2(String filename, HttpServletResponse response) throws UnsupportedEncodingException {
        File file = new File(filename);
        String name = filename;
        if (filename.lastIndexOf("_") >= 0) {
            String str1 = filename.substring(0, filename.indexOf("_"));
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

    @RequestMapping("/deleteById")
    @ResponseBody
    public Result  deleteById(Integer id){
        FileSample  sample=   fileSampleService.findById(id);
        List<ChoiceFile>  list= choiceFileService.findByFileId(sample.getId());

        if(list.size()>0){
            return   new Result(false,"有试题绑定该文件请先解除绑定");
        }
            String  fileName=sample.getPath();
            File file = new File(fileName);
            if(!file.exists()){
            }else{
                if(file.isFile()){
                    if(file.isFile() && file.exists()){
                        file.delete();
                    }else{
                    }
                }else{
                }
            }
        fileSampleService.deleteById(id);
        return   new Result(true,"删除成功");
    }


    @PostMapping("/choiceShareFile")
    @ResponseBody
    public Map  choiceShareFile(@RequestBody ScreenShareFile screenShareFile, HttpServletRequest request){
        List<Integer> list=fileSampleService.ScreenALL2(screenShareFile,request);
        List<Integer> list1=fileSampleService.ScreenMyALL2(screenShareFile,request);
        for (Integer  l:list1 ) {
            list.add(l);
        }
        return   fileSampleService.choiceShareFile(screenShareFile,list);
    }






}
