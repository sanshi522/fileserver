package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.StuGroup;
import com.sanshi.fileserver.bean.Teacher;
import com.sanshi.fileserver.service.StuGroupService;
import com.sanshi.fileserver.service.TeacherService;
import com.sanshi.fileserver.vo.PageGet;
import com.sanshi.fileserver.vo.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @RequestMapping(path = "/save")
    @ResponseBody
    public Teacher save(Teacher teacher){
        return teacherService.save(teacher);
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
    @RequestMapping(path = "/findTeachers")
    @ResponseBody
    public Map findTeachers(PageGet val,HttpServletRequest request){
        val.setIssistId(2);
        return teacherService.finTeachers(val,request);
    }

    @RequestMapping(path = "/GetteaIdentity")
    @ResponseBody
    public List<Map> GetteaIdentity(Integer val,HttpServletRequest request){
        List<Map> listdata=new ArrayList<Map>();
        Map json;
        json = new HashMap();
        json.put("key",1);
        json.put("val","老师");
        listdata.add(json);
        json = new HashMap();
        json.put("key",2);
        json.put("val","老师兼管理员");
        listdata.add(json);
        return listdata;
    }
    @RequestMapping(path = "/GetIdentName")
    @ResponseBody
    public Map GetIdentName(Integer val,HttpServletRequest request) {
        Map json= new HashMap();
        if (val==1)
            json.put("name","老师");
        else if(val==2)
            json.put("name","老师兼管理员");
        else if(val==3)
            json.put("name","管理员");
        return  json;
    }
    @RequestMapping(path = "/deleteById")
    @ResponseBody
    public Integer deleteById(Integer val, HttpServletRequest request){
        teacherService.deleteByTeaId(val);
        return 1;
    }
    @RequestMapping(path = "/GetNameById")
    @ResponseBody
    public String GetNameById(Integer val){
        return teacherService.findOneByTeaId(val).getTeaName();
    }

    @RequestMapping(path = "/findAllNoInClass")
    @ResponseBody
    public List<Map> findAllNoInClass(Integer val){
        List<Teacher> Tealist = teacherService.findAllNoInClass(val);
        List<Map> listdata=new ArrayList<Map>();
        Map json;
        for (int i=0;i<Tealist.size();i++){
            json = new HashMap();
            json.put("key",Tealist.get(i).getTeaId());
            json.put("val",Tealist.get(i).getTeaName());
            listdata.add(json);
        }
        return listdata;
    }
}
