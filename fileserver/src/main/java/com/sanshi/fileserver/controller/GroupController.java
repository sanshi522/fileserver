package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.service.StuGroupService;
import com.sanshi.fileserver.vo.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
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
}
