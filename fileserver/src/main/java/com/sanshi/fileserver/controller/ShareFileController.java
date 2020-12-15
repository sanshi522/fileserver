package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.ShareFile;
import com.sanshi.fileserver.service.ShareFileService;
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
@RequestMapping(path = "/ShareFile")
public class ShareFileController {
    ShareFileService shareFileService;
    @RequestMapping(path = "/getid")
    @ResponseBody
    public Map login(@RequestParam ShareFile shareFile, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map json = new HashMap();
        if(session!=null&&session.getAttribute("user") != null){
            SessionUser sessionUser=new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            shareFile.setOwnerIdent(sessionUser.getIdent());
            shareFile.setOwnerId(sessionUser.getUserId());
            Integer id=shareFileService.getIdIsNoAdd(shareFile);
            json.put("resoult", id);
            json.put("ident", sessionUser.getIdent());
        }else{
            json.put("resoult",-1);//返回登陆页面
        }
        return  json;
    }
}
