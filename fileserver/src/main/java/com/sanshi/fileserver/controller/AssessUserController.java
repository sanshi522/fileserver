package com.sanshi.fileserver.controller;


import com.sanshi.fileserver.bean.AssessUser;
import com.sanshi.fileserver.service.AssessUserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("AssessUser")
public class AssessUserController {

    private AssessUserService assessUserService;

    public AssessUserController(AssessUserService assessUserService) {
        this.assessUserService = assessUserService;
    }

    @RequestMapping("save")
    @ResponseBody
    private int save(List<AssessUser> list) {
        return assessUserService.save(list);
    }


}
