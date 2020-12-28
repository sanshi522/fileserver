package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.service.StuGroupService;
import com.sanshi.fileserver.service.TeacherService;
import com.sanshi.fileserver.vo.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/Teacher")
public class TeacherController {
    private TeacherService teacherService;
    private StuGroupService stuGroupService;

    public TeacherController(TeacherService teacherService, StuGroupService stuGroupService) {
        this.teacherService = teacherService;
        this.stuGroupService = stuGroupService;
    }

    @RequestMapping(path = "/GetTeacher")
    @ResponseBody
    public Map GetTeacher(Integer classId,HttpServletRequest request){
        Map json = new HashMap();
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("user") != null) {
            SessionUser sessionUser = new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            Integer ident = (sessionUser.getLogintype() == 0 ? 0 : sessionUser.getTeacher().getTeaIdentity());
            Integer id = (sessionUser.getLogintype() == 0 ? sessionUser.getStudent().getStuId() : sessionUser.getTeacher().getTeaId());
            json.put("resoult", true);
            if (ident==0)
                json.put("teachers",teacherService.findTeacherByClassId(stuGroupService.findOneById(sessionUser.getStudent().getStuGroup()).getCclassId()));
            else
                json.put("teachers",teacherService.findTeacherByClassId(classId));
        }else
            json.put("resoult", false);
        return json;
    }
    @RequestMapping(path = "/GetAdmin")
    @ResponseBody
    public Map findAdmin(){
        Map json = new HashMap();
        json.put("resoult", true);
        json.put("teachers",teacherService.findAdmin());
        return json;
    }
}
