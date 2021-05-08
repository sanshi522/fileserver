package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.StuGroup;
import com.sanshi.fileserver.bean.Student;
import com.sanshi.fileserver.service.StuGroupService;
import com.sanshi.fileserver.service.StudentService;
import com.sanshi.fileserver.vo.PageGet;
import com.sanshi.fileserver.vo.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Student")
public class StudentController {
    private StudentService studentService;
    private StuGroupService stuGroupService;

    public StudentController(StudentService studentService, StuGroupService stuGroupService) {
        this.studentService = studentService;
        this.stuGroupService = stuGroupService;
    }

    @RequestMapping(path = "/GetStudent")
    @ResponseBody
    public Map GetStudent(Integer StuGroup, HttpServletRequest request) {
        Map json = new HashMap();
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("user") != null) {
            SessionUser sessionUser = new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            Integer ident = (sessionUser.getLogintype() == 0 ? 0 : sessionUser.getTeacher().getTeaIdentity());
            Integer id = (sessionUser.getLogintype() == 0 ? sessionUser.getStudent().getStuId() : sessionUser.getTeacher().getTeaId());
            json.put("resoult", true);
            if (ident == 0)
                json.put("students", studentService.findAllByStuGroup(sessionUser.getStudent().getStuGroup()));
            else {
                json.put("students", studentService.findAllByStuGroup(StuGroup));
            }
        } else
            json.put("resoult", false);
        return json;
    }

    @RequestMapping(path = "/GetStudentByClassId")
    @ResponseBody
    public Map GetStudentByClassId(PageGet val) {
        System.out.println(val.toString());
        return studentService.findAllByClassId(val);
//        List<Student> Studentlist = studentService.findAllByClassId(val);
//        List<Map> listdata=new ArrayList<Map>();
//        Map json;
//        for (int i=0;i<Studentlist.size();i++){
//            json = new HashMap();
//            json.put("stuId",Studentlist.get(i).getStuId());
//            json.put("stuNumber",Studentlist.get(i).getStuNumber());
//            json.put("stuName",Studentlist.get(i).getStuName());
//            json.put("stuGender",Studentlist.get(i).getStuGender());
//            json.put("stuPass",Studentlist.get(i).getStuPass());
//            json.put("stuHead",Studentlist.get(i).getStuHead());
//            json.put("stuGroup",Studentlist.get(i).getStuGroup());
//            json.put("stuGroupName",stuGroupService.findOneById(Studentlist.get(i).getStuGroup()).getName());
//            json.put("stuState",Studentlist.get(i).getStuState());
//            json.put("stuRemake",Studentlist.get(i).getStuRemake());
//            listdata.add(json);
//        }
//        return listdata;
    }

    @RequestMapping(path = "/save")
    @ResponseBody
    public Student AddStudent(Student val, HttpServletRequest request) {
        return studentService.save(val);
    }

    @RequestMapping(path = "/deleteById")
    @ResponseBody
    public Integer deleteById(Integer val, HttpServletRequest request) {
        return studentService.deleteById(val);
    }

}
