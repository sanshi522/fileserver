package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.Answer;
import com.sanshi.fileserver.service.AnswerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping("/Answer")
public class AnswerController {

    private AnswerService   answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }



    @RequestMapping("/save")
    @ResponseBody
    public   int  save(@RequestBody List<Answer> answerList){

        return  answerService.save(answerList)  ;

    }


}
