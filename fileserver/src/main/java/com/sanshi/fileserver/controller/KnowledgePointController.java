package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.KnowledgePoint;
import com.sanshi.fileserver.service.KnowledgePointService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/KnowledgePoint")
public class KnowledgePointController {
    private KnowledgePointService knowledgePointService;

    public KnowledgePointController(KnowledgePointService knowledgePointService) {
        this.knowledgePointService = knowledgePointService;
    }

    @RequestMapping(path = "/findAllByNameLike")
    @ResponseBody
    public List<KnowledgePoint> findAllByNameLike(@RequestParam Integer subid,@RequestParam String name){
        return knowledgePointService.findAllBySubIdAndNameLike(subid,name);
    }
}
