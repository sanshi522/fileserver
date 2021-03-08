package com.sanshi.fileserver.controller;


import com.sanshi.fileserver.bean.TestPaper;
import com.sanshi.fileserver.service.TestPaperService;
import com.sanshi.fileserver.vo.ReadTestPaper;
import com.sanshi.fileserver.vo.TestPaperVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/TestPaper")
public class TestPaperController {
    private TestPaperService testPaperService;

    public TestPaperController(TestPaperService testPaperService) {
        this.testPaperService = testPaperService;
    }

    /**
     * 学生考试
     * @param assessId
     * @param request
     * @return
     */
    @RequestMapping(path = "/exam")
    @ResponseBody
    public TestPaperVo Exam(Integer assessId, HttpServletRequest request){
        return  testPaperService.Exam(assessId,request);
    }

    /**
     * 获取试卷
     * @param testPaper
     * @return
     */
    @RequestMapping(path = "/findAll")
    @ResponseBody
    public List<TestPaper> findAll(TestPaper testPaper){
        return testPaperService.findAll(testPaper);
    }

    /**
     *
     * @param testPaperId
     * @return
     */
    @RequestMapping(path = "/read")
    @ResponseBody
    public ReadTestPaper read(Integer testPaperId){
        return testPaperService.read(testPaperId);
    }
}
