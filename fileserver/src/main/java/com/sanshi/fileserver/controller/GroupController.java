package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.StuGroup;
import com.sanshi.fileserver.bean.Student;
import com.sanshi.fileserver.service.StuGroupService;
import com.sanshi.fileserver.vo.PageGet;
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
@RequestMapping("/Group")
public class GroupController {
    private StuGroupService stuGroupService;

    public GroupController(StuGroupService stuGroupService) {
        this.stuGroupService = stuGroupService;
    }

    @RequestMapping(path = "/GetGroup")
    @ResponseBody
    public Map GetGroup(@RequestParam Integer CclassId, HttpServletRequest request){
        HttpSession session = request.getSession();
        Map json = new HashMap();
        if (session != null && session.getAttribute("user") != null) {
            SessionUser sessionUser = new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            Integer ident = (sessionUser.getLogintype() == 0 ? 0 : sessionUser.getTeacher().getTeaIdentity());
            Integer id = (sessionUser.getLogintype() == 0 ? sessionUser.getStudent().getStuId() : sessionUser.getTeacher().getTeaId());
            json.put("resoult", true);
            if (ident==0){
                json.put("Group",stuGroupService.findOneById(sessionUser.getStudent().getStuGroup()));
            }else{
                json.put("Groups",stuGroupService.findGroupsByCclassId(CclassId));
            }
        }else
            json.put("resoult", false);
        return json;
    }
    @RequestMapping(path = "/GetGroupsByClassId")
    @ResponseBody
    public Map GetGroupByClassId(PageGet val){
        return stuGroupService.findGroupByCclassId(val);
//        List<StuGroup> Grouplist = stuGroupService.findGroupsByCclassId(val);
//        List<Map> listdata=new ArrayList<Map>();
//        Map json;
//        for (int i=0;i<Grouplist.size();i++){
//            json = new HashMap();
//            json.put("key",Grouplist.get(i).getId());
//            json.put("val",Grouplist.get(i).getName());
//            listdata.add(json);
//        }
//        return listdata;
    }
    @RequestMapping(path = "/GetGroupByClassId")
    @ResponseBody
    public List<Map> GetGroupsByClassId(Integer val){
        List<StuGroup> Grouplist = stuGroupService.findGroupsByCclassId(val);
        List<Map> listdata=new ArrayList<Map>();
        Map json;
        for (int i=0;i<Grouplist.size();i++){
            json = new HashMap();
            json.put("key",Grouplist.get(i).getId());
            json.put("val",Grouplist.get(i).getName());
            listdata.add(json);
        }
        return listdata;
    }
    @RequestMapping(path = "/GetNameById")
    @ResponseBody
    public Map GetGroupNameById(Integer val){
        Map json= new HashMap();
        json.put("name",stuGroupService.findOneById(val).getName());
        return  json;
    }
    @RequestMapping(path = "/save")
    @ResponseBody
    public StuGroup save(StuGroup val, HttpServletRequest request){
        return stuGroupService.save(val);
    }
    @RequestMapping(path = "/deleteById")
    @ResponseBody
    public Integer deleteById(Integer val, HttpServletRequest request){
        return stuGroupService.deleteById(val);
    }
}
