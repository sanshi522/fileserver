package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.FileSample;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/file")
public class FileSampleController {
    private FileSampleService fileSampleService;

    public FileSampleController(FileSampleService fileSampleService) {
        this.fileSampleService = fileSampleService;
    }

    @GetMapping("/exists")
    @ResponseBody
    public FileExists fileExists(String md5, Long size) {
        return fileSampleService.fileExists(md5,size);
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
        if(file == null || !file.getSize().equals(size)) {
            Optional.ofNullable(file).ifPresent(e -> fileSampleService.deleteById(e.getId()));
            fileSampleService.insertFileSample(new FileSample(index, parent, name, UploadUtil.saveFile(patch, size), md5, size,null));
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
        if(fileInfo == null || CollectionUtils.isEmpty(patchs) || !total.equals(size)) {
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
    public  Map getAllShareFile(@RequestBody ScreenShareFile screenShareFile,HttpServletRequest request){
        return fileSampleService.ScreenALL(screenShareFile,request);
    }
    @PostMapping("/getAllMyShareFile")
    @ResponseBody
    public  Map getAllMyShareFile(@RequestBody ScreenShareFile screenShareFile,HttpServletRequest request){
        return fileSampleService.ScreenMyALL(screenShareFile,request);
    }
}
