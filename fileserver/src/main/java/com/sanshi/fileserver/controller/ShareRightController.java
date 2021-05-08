package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.ShareRight;
import com.sanshi.fileserver.service.ShareRightService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path = "/ShareRight")
public class ShareRightController {
    private ShareRightService shareRightService;

    public ShareRightController(ShareRightService shareRightService) {
        this.shareRightService = shareRightService;
    }

    @ResponseBody
    @RequestMapping(path = "/AddIsHaveUpTime")
    public Map AddIsHaveUpTime(@RequestBody ShareRight shareRight) {
        shareRightService.AddIsHaveUpTime(shareRight);
        Map json = new HashMap();
        json.put("resoult", 1);
        return json;
    }
}
