package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.vo.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HelloController {
    @GetMapping("/join")
    public String sayHello(){
        return "login";
    }
    @GetMapping("/file")
    public String sharedfile(){
        return "frame/sharedfile";
    }
    @GetMapping("/share")
    public String share( ){return "frame/share";}
    @GetMapping("/myshare")
    public String myshare( ){return "frame/myshare";}
    @GetMapping("/addshare")
    public String addshare( ){return "frame/addshare";}
    @GetMapping("/uploadview")
    public String uploadview( ){return "frame/upload";}
    @GetMapping("/home")
    public String home(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session!=null&&session.getAttribute("user") != null){
            SessionUser sessionUser=new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            if(sessionUser.getLogintype()==0){
                return "frame/stuhome";
            }else{
                return "frame/teahome";
            }
        }else{
            return "error/404";
        }
    }
    /**
     * 域名的根目录，已登录返回对应的主页，否则去登录页
     * @return
     */
    @GetMapping(path="/")
    public String welcomePage(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session!=null&&session.getAttribute("user") != null){
            SessionUser sessionUser=new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            if(sessionUser.getLogintype()==0){
                int a=0;

            }else{

            }
            return "index";
        }else{
            return "login";
        }
    }
}
