package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.Answer;
import com.sanshi.fileserver.service.AnswerService;
import com.sanshi.fileserver.vo.AnswerVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/Answer")
public class AnswerController {

    private AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @RequestMapping("/save")
    @ResponseBody
    public int save(@RequestBody List<Answer> answerList   ) {
        return answerService.save(answerList);
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest req) {

        if(file.isEmpty()){
            return "";
        }else {
            //日期目录
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
            String path = "E:/fileUpload/";
            String format = sdf.format(new Date());
            File folder = new File(path + format);
            if (!folder.isDirectory()) {
                folder.mkdirs();
            }
            String oldName = file.getOriginalFilename();

            String newName = UUID.randomUUID().toString() +"_"+oldName.substring(0, oldName.indexOf("."))+ oldName.substring(oldName.lastIndexOf("."), oldName.length());
            try {
                // 文件保存
                file.transferTo(new File(folder, newName));
                // 返回上传文件的访问路径
                String filePath = path + format + newName;
                return filePath;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "上传失败";
        }
    }

    @RequestMapping("/approval")
    @ResponseBody
    public List<AnswerVo> approval(Integer respondentId) {
        return answerService.approval(respondentId);
    }


    @RequestMapping("/teachRead")
    @ResponseBody
    public int teachRead(@RequestBody List<Answer> answerList) {
        return answerService.teachRead(answerList);

    }

    @RequestMapping("/skip")
    @ResponseBody
    public int skip(Integer respondentId) {
        return answerService.skip(respondentId);
    }


    @ResponseBody
    @RequestMapping("/particulars")
    public List<AnswerVo> particulars(Integer assessId) {
        return answerService.particulars(assessId);
    }

    @ResponseBody
    @RequestMapping("/findById")
    public Answer findById(Integer id) {
        return answerService.findById(id);
    }


}
