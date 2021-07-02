package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.StuGroup;
import com.sanshi.fileserver.bean.Subject;
import com.sanshi.fileserver.bean.TestPaper;
import com.sanshi.fileserver.service.SubjectService;
import com.sanshi.fileserver.service.TestPaperService;
import com.sanshi.fileserver.vo.PageGet;
import com.sanshi.fileserver.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/subject")
public class SubjectController {
    private SubjectService subjectService;
    private TestPaperService testPaperService;

    public SubjectController(SubjectService subjectService, TestPaperService testPaperService) {
        this.subjectService = subjectService;
        this.testPaperService = testPaperService;
    }

    @RequestMapping(path = "/findAll")
    @ResponseBody
    public List<Subject> findAll() {
        return subjectService.findAll();
    }

    @RequestMapping(path = "/getAll")
    @ResponseBody
    public Map getAll(PageGet val, HttpServletRequest request) {
        return subjectService.getAll(val);
    }


    @RequestMapping(path = "/save")
    @ResponseBody
    public Subject save(Subject subject) {
        return subjectService.save(subject);
    }


    @RequestMapping(path = "/deleteById")
    @ResponseBody
    public Integer deleteById(Integer val, HttpServletRequest request) {
        subjectService.deleteById(val);
        return 1;
    }

    @RequestMapping(path = "/findDelete")
    @ResponseBody
    public Result findDelete(Integer val, HttpServletRequest request) {
      List<TestPaper> testPaperList    =   testPaperService.findAllBySubjectId(val);
        if (testPaperList.size()>0){
            return  new Result(false,"该学科存在试卷和考核信息确定要删除吗？");
        }
        return  new Result(true,"可以删除");

    }
}
