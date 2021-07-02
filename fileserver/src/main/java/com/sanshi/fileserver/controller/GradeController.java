package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.Cclass;
import com.sanshi.fileserver.bean.Grade;
import com.sanshi.fileserver.bean.StuGroup;
import com.sanshi.fileserver.service.CclassService;
import com.sanshi.fileserver.service.GradeService;
import com.sanshi.fileserver.service.StuGroupService;
import com.sanshi.fileserver.vo.PageGet;
import com.sanshi.fileserver.vo.Result;
import com.sanshi.fileserver.vo.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Grade")
public class GradeController {
    private GradeService gradeService;
    private CclassService cclassService;
    private StuGroupService stuGroupService;

    public GradeController(GradeService gradeService, CclassService cclassService, StuGroupService stuGroupService) {
        this.gradeService = gradeService;
        this.cclassService = cclassService;
        this.stuGroupService = stuGroupService;
    }

    @RequestMapping(path = "/GetYear")
    @ResponseBody
    public Map GetYears(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map json = new HashMap();
        if (session != null && session.getAttribute("user") != null) {
            SessionUser sessionUser = new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            Integer ident = (sessionUser.getLogintype() == 0 ? 0 : sessionUser.getTeacher().getTeaIdentity());
            Integer id = (sessionUser.getLogintype() == 0 ? sessionUser.getStudent().getStuId() : sessionUser.getTeacher().getTeaId());
            json.put("resoult", true);
            if (ident == 0) {
                json.put("year", gradeService.findOneById(cclassService.findOneById(stuGroupService.findOneById(sessionUser.getStudent().getStuGroup()).getCclassId()).getGradeId()));
            } else {
                json.put("years", gradeService.findAllYear(ident, id));
            }

        } else
            json.put("resoult", false);

        return json;
    }

    @RequestMapping(path = "/GetGrade")
    @ResponseBody
    public Map GetGrade(Integer yearNumber, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map json = new HashMap();
        if (session != null && session.getAttribute("user") != null) {
            SessionUser sessionUser = new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            Integer ident = (sessionUser.getLogintype() == 0 ? 0 : sessionUser.getTeacher().getTeaIdentity());
            Integer id = (sessionUser.getLogintype() == 0 ? sessionUser.getStudent().getStuId() : sessionUser.getTeacher().getTeaId());
            json.put("resoult", true);
            if (ident == 0) {
                json.put("grade", gradeService.findOneById(cclassService.findOneById(stuGroupService.findOneById(sessionUser.getStudent().getStuGroup()).getCclassId()).getGradeId()));
                // json.put("year",gradeService.findOneById(cclassService.GetGradeIdById(stuGroupService.findById(sessionUser.getStudent().getStuGroup()).getCclassId())).getYear());
            } else {
                json.put("grades", gradeService.findAll(ident, id, yearNumber));
            }
        } else
            json.put("resoult", false);

        return json;
    }

    @RequestMapping(path = "/GetGradeByyearNumber")
    @ResponseBody
    public List<Map> GetGradeByyearNumber(@RequestParam Integer yearNumber, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("user") != null) {
            SessionUser sessionUser = new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            Integer ident = (sessionUser.getLogintype() == 0 ? 0 : sessionUser.getTeacher().getTeaIdentity());
            Integer id = (sessionUser.getLogintype() == 0 ? sessionUser.getStudent().getStuId() : sessionUser.getTeacher().getTeaId());
            List<Grade> Gradelist = gradeService.findAll(ident, id, yearNumber);
            List<Map> listdata = new ArrayList<Map>();
            Map json;
            for (int i = 0; i < Gradelist.size(); i++) {
                json = new HashMap();
                json.put("key", Gradelist.get(i).getId());
                json.put("val", Gradelist.get(i).getName());
                listdata.add(json);
            }
            return listdata;
        } else
            return null;
    }

    @RequestMapping(path = "/GetGradesByyearNumber")
    @ResponseBody
    public Map GetGradesByyearNumber(PageGet val, HttpServletRequest request) {
        return gradeService.GetGradesByyearNumber(val);
    }

    @RequestMapping(path = "/save")
    @ResponseBody
    public Grade SaveClass(Grade val, HttpServletRequest request) {
        return gradeService.save(val);
    }


    @RequestMapping(path = "/findDelete")
    @ResponseBody
    public Result findDelete(Integer val, HttpServletRequest request) {
        List<Cclass>  cclassList=  cclassService.findByGradeId(val);
        if (cclassList.size()>0){
            return  new Result(false,"该学院下有班级和学生确定要删除吗？");
        }
        return  new Result(true,"可以删除");

    }

    @RequestMapping(path = "/deleteById")
    @ResponseBody
    public int deleteById(Integer val) {
    return   gradeService.deleteById(val);
    }

}
