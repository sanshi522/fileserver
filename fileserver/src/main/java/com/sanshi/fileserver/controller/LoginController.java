package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.Student;
import com.sanshi.fileserver.bean.Teacher;
import com.sanshi.fileserver.vo.SessionUser;
import com.sanshi.fileserver.repository.StudentRepository;
import com.sanshi.fileserver.service.StudentService;
import com.sanshi.fileserver.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(StudentRepository.class);
    private StudentService studentService;
    private TeacherService teacherService;

    public LoginController(StudentService studentService, TeacherService teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @RequestMapping(path = "/login")
    @ResponseBody
    public Map login(@RequestParam String slumber, @RequestParam String pass, @RequestParam Integer identity, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map json = new HashMap();
        if (identity == 0) { //学生登录
            switch (studentService.Login(slumber, pass, identity, request)) {
                case -1:
                    log.warn("未找到当前用户");
                    json.put("code", -1);
                    json.put("msg", "未找到用户!");
                    break;
                case 0:
                    json.put("code", 0);
                    json.put("msg", "密码错误!");
                    break;
                case 1:
                    json.put("code", 1);
                    break;
            }
        } else {//教师或者管理员登录
            switch (teacherService.Login(slumber, pass, identity, request)) {
                case -1:
                    log.warn("未找到当前用户");
                    json.put("code", -1);
                    json.put("msg", "未找到用户!");
                    break;
                case 0:
                    json.put("code", 0);
                    json.put("msg", "密码错误!");
                    break;
                case 1:
                    json.put("code", 1);
                    break;
            }
        }
        return json;
    }

    @RequestMapping(path = "/login/Islogin")
    @ResponseBody
    public Map Islogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map json = new HashMap();
        if (session != null && session.getAttribute("user") != null) {
            SessionUser sessionUser = new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            json.put("resoult", true);
            json.put("logintype", sessionUser.getLogintype());
            if (sessionUser.getLogintype() == 0) {
                Student student = sessionUser.getStudent();
                student.setStuPass("禁止非法获取");
                json.put("user", student);
            } else {
                Teacher teacher = sessionUser.getTeacher();
                teacher.setTeaPass("禁止非法获取");
                json.put("user", teacher);
            }
        } else {
            json.put("resoult", false);
        }
        return json;
    }

    @GetMapping(path = "/exit")
    public String Exit(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("user") != null) {
            session.removeAttribute("user");
        }
        return "login";
    }
}

