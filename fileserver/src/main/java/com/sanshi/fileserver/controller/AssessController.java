package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.service.*;
import com.sanshi.fileserver.vo.*;
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
     * @param
     * @param （0：未进行；1：进行中）
     * @param
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
    public   int  save(@RequestBody AssessUserVo assessUservo){

        return  assessService.save(assessUservo);
    };

    //通过id获取全称
    @RequestMapping(path = "/fullname")
    @ResponseBody
    public  String   fullname(Integer testObject ,Integer testObjectId){


        return  assessService.fullname(testObject,testObjectId);
    }

    @RequestMapping(path = "/findById")
    @ResponseBody
    public  AssessUerGVo  findbyId(Integer  assessId){
        return  assessService.findbyId(assessId);
    };


    @RequestMapping(path = "/delete")
    @ResponseBody
    public  Result  delete(Integer  assessId){
        return  assessService.delete(assessId);
    };

  //学生获取考核
  @RequestMapping(path = "/StudentAssess")
  @ResponseBody
    public  Map  StudentAssess(@RequestBody StudentAssessVo studentAssessVo){
        return  assessService.StudentAssess(studentAssessVo);
    }

    //学生参加考核获取题目
    @RequestMapping(path = "/studentChoice")
    @ResponseBody
    public  StudentAssessVo  studentChoice(Integer assessId){

        return  assessService.studentChoice(assessId);
    }





}
