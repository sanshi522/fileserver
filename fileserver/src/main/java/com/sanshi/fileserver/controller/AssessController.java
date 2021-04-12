package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.Assess;
import com.sanshi.fileserver.service.*;
import com.sanshi.fileserver.vo.AssessMsg;
import com.sanshi.fileserver.vo.AssessVo;
import com.sanshi.fileserver.vo.ScreenAssess;
import com.sanshi.fileserver.vo.TestPaperMsg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 获取考核
 */
@Controller
@RequestMapping("/Assess")
public class AssessController {
    private AssessService assessService;

    public AssessController(AssessService assessService) {
        this.assessService = assessService;
    }


    /**
     * 获取考核
     * @param assess
     * @param test（0：未进行；1：进行中）
     * @param request
     * @return
     */
    @RequestMapping(path = "/findAll")
    @ResponseBody
    public Map TeacherfindAll(@RequestBody ScreenAssess assessVo){
        return  assessService.findAll(assessVo);
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/findMsg")
    @ResponseBody
    public AssessMsg findMsg(Integer id){
        return assessService.findMsg(id);
    }




    @RequestMapping(path = "/save")
    @ResponseBody
    public   Assess  save(Assess assess){
       return   assessService.save(assess);
    };








}
