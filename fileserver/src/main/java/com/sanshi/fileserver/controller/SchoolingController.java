package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.TeacherBindCclass;
import com.sanshi.fileserver.service.SchoolingService;
import com.sanshi.fileserver.vo.PageGet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/schooling")
public class SchoolingController {
    private SchoolingService schoolingService;

    public SchoolingController(SchoolingService schoolingService) {
        this.schoolingService = schoolingService;
    }

    @RequestMapping(path = "/finAlltByClassId")
    @ResponseBody
    public Map finAlltByClassId(PageGet val) {
        return schoolingService.finAlltByClassId(val);
    }

    @RequestMapping(path = "/save")
    @ResponseBody
    public TeacherBindCclass save(TeacherBindCclass teacherBindCclass) {
        return schoolingService.save(teacherBindCclass);
    }

    @RequestMapping(path = "/deleteById")
    @ResponseBody
    public Integer deleteById(Integer id) {
        schoolingService.deleteById(id);
        return 1;
    }
}
