package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.Assess;
import com.sanshi.fileserver.service.*;
import com.sanshi.fileserver.vo.AssessVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public List<AssessVo> findAll(Assess assess,Integer test,HttpServletRequest request){
        return assessService.findAll(assess,test,request);
    }


}
