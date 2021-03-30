package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.Subject;
import com.sanshi.fileserver.service.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/subject")
public class SubjectController {
    private SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @RequestMapping(path = "/findAll")
    @ResponseBody
    public List<Subject> findAll(){
        return subjectService.findAll();
    }
}
