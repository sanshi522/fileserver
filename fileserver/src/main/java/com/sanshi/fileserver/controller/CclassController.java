package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.service.CclassService;
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
@RequestMapping("/Grade")
public class CclassController {
    private CclassService cclassService;
    private StuGroupService stuGroupService;

    public CclassController(CclassService cclassService, StuGroupService stuGroupService) {
        this.cclassService = cclassService;
        this.stuGroupService = stuGroupService;
    }
    @RequestMapping(path = "/GetClass")
    @ResponseBody
    public Map GetClass(@RequestParam Integer GradeId, HttpServletRequest request){
        HttpSession session = request.getSession();
        Map json = new HashMap();
        if (session != null && session.getAttribute("user") != null) {
            SessionUser sessionUser = new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            Integer ident = (sessionUser.getLogintype() == 0 ? 0 : sessionUser.getTeacher().getTeaIdentity());
            Integer id = (sessionUser.getLogintype() == 0 ? sessionUser.getStudent().getStuId() : sessionUser.getTeacher().getTeaId());
            json.put("resoult", true);
            if (ident==0){
                json.put("class",cclassService.findOneById(stuGroupService.findOneById(sessionUser.getStudent().getStuGroup()).getCclassId()));
            }else{
                json.put("clases",cclassService.findCclasesByGradeId(ident,id,GradeId));
            }
        }else
            json.put("resoult", false);
        return json;
    }
}
