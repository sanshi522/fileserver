package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.TestPaperBindChoice;
import com.sanshi.fileserver.service.TestPaperBindChoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/TestPaperBindChoice")
public class TestPaperBindChoiceController {
    private TestPaperBindChoiceService  TestPaperBindChoiceService;

    public TestPaperBindChoiceController(com.sanshi.fileserver.service.TestPaperBindChoiceService testPaperBindChoiceService) {
        TestPaperBindChoiceService = testPaperBindChoiceService;
    }

    @PostMapping("/save")



    @ResponseBody
    public int  save(List<TestPaperBindChoice> testPaperBindChoices){TestPaperBindChoiceService.save(testPaperBindChoices); return 1;}


    @PostMapping("/delete")
    @ResponseBody
    public Integer  deleteById(Integer id){
        TestPaperBindChoice  testPaperBindChoice=new  TestPaperBindChoice();
        testPaperBindChoice.setId(id);
        TestPaperBindChoiceService.deleteById(testPaperBindChoice);
        return 1;
    };


}
