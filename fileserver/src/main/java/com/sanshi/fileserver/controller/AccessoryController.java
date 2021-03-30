package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.Assess;
import com.sanshi.fileserver.service.AccessoryService;
import com.sanshi.fileserver.vo.AssessVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/accessory")
public class AccessoryController {
    AccessoryService accessoryService;
    @RequestMapping(path = "/save")
    @ResponseBody
    public Map save(Integer library, Integer fileid){
        return accessoryService.save(library,fileid);
    }
}
