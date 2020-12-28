package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.vo.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "sharedfile";
    }
    @GetMapping("/share")
    public String share( ){return "sharehome";}
    @GetMapping("/myshare")
    public String myshare( ){return "myshare";}
    @GetMapping("/addshare")
    public String addshare( ){return "addshare";}
    @GetMapping("/uploadview")
    public String uploadview( ){return "uploadadmin";}
    @GetMapping("/home")
    public String home(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session!=null&&session.getAttribute("user") != null){
            SessionUser sessionUser=new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            if(sessionUser.getLogintype()==0){
                return "stuhome";
            }else{
                return "teahome";
            }
        }else{
            return "error";
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
