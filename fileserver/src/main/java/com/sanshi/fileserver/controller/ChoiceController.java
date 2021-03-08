package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.service.ChoiceService;
import com.sanshi.fileserver.vo.ScreenChoice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/Choice")
public class ChoiceController {
    private ChoiceService choiceService;

    public ChoiceController(ChoiceService choiceService) {
        this.choiceService = choiceService;
    }

    @PostMapping("/findAll")
    @ResponseBody
    public Map findAll(@RequestBody ScreenChoice screenChoice){
        return choiceService.findAll(screenChoice);
    }
}
