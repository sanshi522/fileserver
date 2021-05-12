package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.KnowledgePoint;
import com.sanshi.fileserver.bean.Subject;
import com.sanshi.fileserver.service.KnowledgePointService;
import com.sanshi.fileserver.vo.PageGet;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/knowledgePoint")
public class KnowledgePointController {
    private KnowledgePointService knowledgePointService;

    public KnowledgePointController(KnowledgePointService knowledgePointService) {
        this.knowledgePointService = knowledgePointService;
    }

    @RequestMapping(path = "/findAllByNameLike")
    @ResponseBody
    public List<KnowledgePoint> findAllByNameLike(@RequestParam Integer subid, @RequestParam String name) {
        return knowledgePointService.findAllBySubIdAndNameLike(subid, name);
    }

    @RequestMapping(path = "/getAll")
    @ResponseBody
    public Map getAll(PageGet val) {
        return knowledgePointService.getAll(val);
    }

    @RequestMapping(path = "/save")
    @ResponseBody
    public KnowledgePoint save(KnowledgePoint knowledgePoint) {
        return knowledgePointService.save(knowledgePoint);
    }

    ;

    @RequestMapping(path = "/deleteById")
    @ResponseBody
    public Integer deleteById(Integer val, HttpServletRequest request) {
        knowledgePointService.deleteById(val);
        return 1;
    }

    @RequestMapping(path = "/selectBySubId")
    @ResponseBody
    public List<KnowledgePoint> selectBySubId(Integer id) {
        return knowledgePointService.selectBySubId(id);

    }


}
