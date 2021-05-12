package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.Choice;
import com.sanshi.fileserver.service.ChoiceService;
import com.sanshi.fileserver.utils.ChoiceUtil;
import com.sanshi.fileserver.vo.ScreenChoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/choice")
public class ChoiceController {
    private ChoiceService choiceService;

    public ChoiceController(ChoiceService choiceService) {
        this.choiceService = choiceService;
    }

    @PostMapping("/findAll")
    @ResponseBody
    public Map findAll(@RequestBody ScreenChoice screenChoice) {
        return choiceService.findAll(screenChoice);
    }

    @PostMapping("/save")
    @ResponseBody
    public Choice save(@RequestBody ChoiceUtil choiceUtil) {
        return choiceService.save(choiceUtil);
    }

    @PostMapping("/findOneById")
    @ResponseBody
    public Choice findOneById(Integer id) {
        return choiceService.findOneById(id);
    }

    @PostMapping("/deleteById")
    @ResponseBody
    public Map deleteById(Integer id) {
        return choiceService.deleteById(id);
    }

    @PostMapping("/selectChoiceByTestPaperId")
    @ResponseBody
    public List<Choice> selectChoiceByTestPaperId(Integer id) {
        return choiceService.selectChoiceByTestPaperId(id);
    }



}
