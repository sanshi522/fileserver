package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.vo.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@Controller
public class HelloController {
    @GetMapping("/particulars")
    public ModelAndView particulars(Integer id) {
        if (id==null) id=0;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("particulars");
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    @GetMapping("/approval")
    public ModelAndView approval(Integer id) {
        if (id==null) id=0;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("approval");
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    @GetMapping("/studentScore")
    public String studentScore(Integer id) {
        return "studentScore";
    }

    @GetMapping("/assess")
    public String assess() {
        return "assess";
    }

    @GetMapping("/addTestPaper")
    public ModelAndView addTestPaper(Integer id) {
        if (id==null) id=0;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addTestPaper");
        modelAndView.addObject("id", id);
        return modelAndView;
    }
    @GetMapping("/assessment")
    public ModelAndView assessment(Integer id) {
        if (id==null) id=0;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("assessment");
        modelAndView.addObject("id", id);
        return modelAndView;
    }


    @GetMapping("/answer")
    public ModelAndView answer(Integer id) {
        if (id==null) id=0;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("answer");
        modelAndView.addObject("id", id);
        return modelAndView;
    }
    @GetMapping("/studentAssess")
    public String studentAssess() {
        return "studentAssess";
    }

    @GetMapping("/schooling")
    public String schooling() {
        return "schooling";
    }

    @GetMapping("/subject")
    public String subject() {
        return "sub";
    }

    @GetMapping("/choice")
    public String choice() {
        return "choice";
    }

    @GetMapping("/testPaper")
    public String testPaper() {
        return "testPaper";
    }

    @GetMapping("/sample")
    public String sample() {
        return "sample";
    }

    @GetMapping("/knowledgePoint")
    public String knowledgePoint() {
        return "knowledgePoint";
    }

    @GetMapping("/student")
    public String student() {
        return "student";
    }

    @GetMapping("/group")
    public String group() {
        return "group";
    }

    @GetMapping("/grade")
    public String grade() {
        return "grade";
    }

    @GetMapping("/class")
    public String cclass() {
        return "class";
    }

    @GetMapping("/teacher")
    public String teacher() {
        return "teacher";
    }

    @GetMapping("/table")
    public String table() {
        return "table";
    }

    @GetMapping("/join")
    public String sayHello() {
        return "login";
    }

    @GetMapping("/file")
    public String sharedfile() {
        return "sharedfile";
    }

    @GetMapping("/share")
    public String share() {
        return "sharehome";
    }

    @GetMapping("/myshare")
    public String myshare() {
        return "myshare";
    }

    @GetMapping("/addshare")
    public String addshare() {
        return "addshare";
    }

    @GetMapping("/uploadview")
    public String uploadview() {
        return "uploadadmin";
    }

    @GetMapping("/home")
    public String home(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("user") != null) {
            SessionUser sessionUser = new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            if (sessionUser.getLogintype() == 0) {
                return "stuhome";
            } else {
                return "teahome";
            }
        } else {
            return "error";
        }
    }

    /**
     * 域名的根目录，已登录返回对应的主页，否则去登录页
     *
     * @return
     */
    @GetMapping(path = "/")
    public String welcomePage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("user") != null) {
            SessionUser sessionUser = new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            if (sessionUser.getLogintype() == 0) {
                int a = 0;

            } else {

            }
            return "index";
        } else {
            return "login";
        }
    }

    @RequestMapping("/download")
    public String downLoad(HttpServletResponse response) throws UnsupportedEncodingException {
        String filename = "navicatformysql.zip";
        String filePath = "D:/软件安装包/数据库管理工具";
        File file = new File(filePath + "/" + filename);
        if (file.exists()) { //判断文件父目录是否存在
            //response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(filename, "UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("----------file download---" + filename);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
//    @GetMapping("/download")
//    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {
//        String fileName = "navicatformysql.zip";// 文件名
//        if (fileName != null) {
//            //设置文件路径
//            File file = new File("D://软件安装包//数据库管理工具//navicatformysql.zip");
//            if (file.exists()) {
//                response.setContentType("application/force-download");// 设置强制下载不打开
//                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
//                byte[] buffer = new byte[1024];
//                FileInputStream fis = null;
//                BufferedInputStream bis = null;
//                try {
//                    fis = new FileInputStream(file);
//                    bis = new BufferedInputStream(fis);
//                    OutputStream os = response.getOutputStream();
//                    int i = bis.read(buffer);
//                    while (i != -1) {
//                        os.write(buffer, 0, i);
//                        i = bis.read(buffer);
//                    }
//                    return "下载成功";
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (bis != null) {
//                        try {
//                            bis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (fis != null) {
//                        try {
//                            fis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//        return "下载失败";
//    }
}
