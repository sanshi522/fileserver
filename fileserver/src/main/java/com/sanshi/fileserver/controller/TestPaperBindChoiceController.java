package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.TestPaperBindChoice;
import com.sanshi.fileserver.service.TestPaperBindChoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/TestPaperBindChoice")
public class TestPaperBindChoiceController {
    private TestPaperBindChoiceService  TestPaperBindChoiceService;

    @PostMapping("/save")
    @ResponseBody
    public TestPaperBindChoice  save(TestPaperBindChoice testPaperBindChoice){ return TestPaperBindChoiceService.save(testPaperBindChoice);};


    @PostMapping("/delete")
    @ResponseBody
    public void  deleteById(TestPaperBindChoice testPaperBindChoice){ TestPaperBindChoiceService.deleteById(testPaperBindChoice); };



}
