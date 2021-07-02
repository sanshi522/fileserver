package com.sanshi.fileserver.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.sanshi.fileserver.bean.Assess;
import com.sanshi.fileserver.bean.Cclass;
import com.sanshi.fileserver.bean.Respondents;
import com.sanshi.fileserver.bean.Student;
import com.sanshi.fileserver.service.*;
import com.sanshi.fileserver.utils.StudentExcel;
import com.sanshi.fileserver.vo.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.*;

/**
 * 获取考核
 */
@Controller
@RequestMapping("/Assess")
public class AssessController {
    private AssessService assessService;
    private RespondentsService  respondentsService;
    private  StudentService studentService;
    private  StuGroupService  stuGroupService;
    private  CclassService  cclassService;
    private  GradeService  gradeService;
    private  AnswerService answerService;

    public AssessController(AssessService assessService, RespondentsService respondentsService, StudentService studentService, StuGroupService stuGroupService, CclassService cclassService, GradeService gradeService, AnswerService answerService) {
        this.assessService = assessService;
        this.respondentsService = respondentsService;
        this.studentService = studentService;
        this.stuGroupService = stuGroupService;
        this.cclassService = cclassService;
        this.gradeService = gradeService;
        this.answerService = answerService;
    }

    /**
     * 获取考核
     *
     * @param
     * @param （0：未进行；1：进行中）
     * @param
     * @return
     */
    @RequestMapping(path = "/findAll")
    @ResponseBody
    public Map TeacherfindAll(@RequestBody ScreenAssess assessVo) {
        return assessService.findAll(assessVo);
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping(path = "/findMsg")
    @ResponseBody
    public AssessMsg findMsg(Integer id) {
        return assessService.findMsg(id);
    }


    /**
     * @param id
     * @return
     */
    @RequestMapping(path = "/findMsg2")
    @ResponseBody
    public AssessMsg findMsg2(Integer id) {
        return assessService.findMsg2(id);
    }

    @RequestMapping(path = "/save")
    @ResponseBody
    public int save(@RequestBody AssessUserVo assessUservo) {

        return assessService.save(assessUservo);
    }

    ;

    //通过id获取全称
    @RequestMapping(path = "/fullname")
    @ResponseBody
    public String fullname(Integer testObject, Integer testObjectId) {


        return assessService.fullname(testObject, testObjectId);
    }

    @RequestMapping(path = "/findById")
    @ResponseBody
    public AssessUerGVo findbyId(Integer assessId) {
        return assessService.findbyId(assessId);
    }

    ;


    @RequestMapping(path = "/delete")
    @ResponseBody
    public Result delete(Integer assessId) {
        return assessService.delete(assessId);
    }

    ;

    //学生获取考核
    @RequestMapping(path = "/StudentAssess")
    @ResponseBody
    public Map StudentAssess(@RequestBody StudentAssessVo studentAssessVo) {
        return assessService.StudentAssess(studentAssessVo);
    }

    //学生参加考核获取题目
    @RequestMapping(path = "/studentChoice")
    @ResponseBody
    public StudentAssessVo studentChoice(Integer assessId) {

        return assessService.studentChoice(assessId);
    }

    //查询考核
    @RequestMapping(path = "/findByOneId")
    @ResponseBody
    public Assess findByOneId(Integer assessId) {
        return assessService.findByOneId(assessId);
    }


    //导出成绩
    @RequestMapping(path = "/exportScore")
    public void exportScore (Integer assessId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Assess  assess=   assessService.findByOneId(assessId);
      List<Integer>   list=respondentsService.selectStudent(assess.getId());
      List<StudentScore> studentScoreList=new ArrayList<>();
        for (Integer  id:list){
            StudentScore  studentScore=new StudentScore();
          Student  student= studentService.findOneById(id);
            studentScore.setStuName(student.getStuName());
            studentScore.setStuNumber(student.getStuNumber());
            studentScore.setAssessName(assess.getName());
          Cclass  cclass= cclassService.findOneById(stuGroupService.findOneById(student.getStuGroup()).getCclassId());
            studentScore.setClassName(cclass.getName());
            studentScore.setGradeName(gradeService.findOneById(cclass.getGradeId()).getName());
            //获取成绩
           Respondents  respondents= respondentsService.selectAssessIdAndStuId(assess.getId(),student.getStuId());
           if(respondents!=null){

               Double  d=  answerService.selectScore(respondents.getId());
               if(d==null){
                   studentScore.setState("未提交");
                   studentScore.setScore(0);
               }else {
                   studentScore.setState("已参加");
                   studentScore.setScore(d);
               }

           }else {
               studentScore.setState("缺考");
               studentScore.setScore(0);
           }
            studentScoreList.add(studentScore);
        }

        Collections.sort(studentScoreList, new Comparator<StudentScore>() {
            @Override
            public int compare(StudentScore o1, StudentScore o2) {
                //升序
                return o1.getGradeName().compareTo(o2.getGradeName());
            }
        });
        ExportParams exportParams = new ExportParams("学生考核成绩表", "导出");
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, StudentScore.class,studentScoreList);
        ExcelUtils.downLoadExcel(assess.getName()+"成绩信息表", workbook, request, response);
    }


}
