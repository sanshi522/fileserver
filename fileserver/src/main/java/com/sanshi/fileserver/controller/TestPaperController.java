package com.sanshi.fileserver.controller;


import com.sanshi.fileserver.bean.TestPaper;
import com.sanshi.fileserver.service.TestPaperService;
import com.sanshi.fileserver.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/testPaper")
public class TestPaperController {
    private TestPaperService testPaperService;

    private HttpSession session;

    public TestPaperController(TestPaperService testPaperService, HttpSession session) {
        this.testPaperService = testPaperService;
        this.session = session;
    }

    /**
     * 学生考试
     *
     * @param assessId
     * @param request
     * @return
     */
    @RequestMapping(path = "/exam")
    @ResponseBody
    public TestPaperVo Exam(Integer assessId, HttpServletRequest request) {
        return testPaperService.Exam(assessId, request);
    }

    /**
     * 获取试卷
     *
     * @param val
     * @return
     */
    @RequestMapping(path = "/findAll")
    @ResponseBody
    public Map findAll(PageGet val) {
        return testPaperService.findAll(val);
    }

    /**
     * @param testPaperId
     * @return
     */
    @RequestMapping(path = "/read")
    @ResponseBody
    public ReadTestPaper read(Integer testPaperId) {
        return testPaperService.read(testPaperId);
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping(path = "/findMsg")
    @ResponseBody
    public TestPaperMsg findMsg(Integer id) {
        return testPaperService.findMsg(id);
    }

    /**
     * @param testPaper
     * @return
     */
    @RequestMapping(path = "/save")
    @ResponseBody
    public TestPaper save(@RequestBody TestPaper testPaper) {
        if(testPaper.getId()!=null && testPaper.getId()!=0){
          TestPaper  testPaper1=    testPaperService.findOneById(testPaper.getId());
            testPaper.setCreateTime(testPaper1.getCreateTime());
            testPaper.setCreationId(testPaper1.getCreationId());
        }else {
            SessionUser sessionUser = (SessionUser) session.getAttribute("user");
            testPaper.setCreationId(sessionUser.getTeacher().getTeaId());
        }
        return testPaperService.save(testPaper);
    }


    @RequestMapping(path = "/delete")
    @ResponseBody
    public Result delete(Integer testPaperId) {

        return testPaperService.deleteById(testPaperId);
    }

    @RequestMapping(path = "/generateTestPaper")
    @ResponseBody
    public Result generateTestPaper(@RequestBody TestPaperUtils testPaperUtils) {

        return testPaperService.generateTestPaper(testPaperUtils);
    }


}
