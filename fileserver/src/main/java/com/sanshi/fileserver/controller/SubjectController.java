package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.Subject;
import com.sanshi.fileserver.service.SubjectService;
import com.sanshi.fileserver.vo.PageGet;
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

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
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
}
