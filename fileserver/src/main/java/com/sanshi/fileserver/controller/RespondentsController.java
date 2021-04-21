package com.sanshi.fileserver.controller;

import com.sanshi.fileserver.bean.Respondents;
import com.sanshi.fileserver.service.RespondentsService;
import com.sanshi.fileserver.vo.PageGet;
import com.sanshi.fileserver.vo.RespondentsMsg;
import com.sanshi.fileserver.vo.RespondentsPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/Respondents")
public class RespondentsController {
    private RespondentsService  respondentsService;

    public RespondentsController(RespondentsService respondentsService) {
        this.respondentsService = respondentsService;
    }

    @ResponseBody
    @RequestMapping("/save")
    public  Respondents   save(@RequestBody  Respondents  respondents){

        return  respondentsService.save(respondents);
    }


    @ResponseBody
    @RequestMapping("/findById")
    public Respondents findById(Integer id) {

   return  respondentsService.findById(id);
    }



    @ResponseBody
    @RequestMapping("/selectSubmit")
    public int selectSubmit(Integer assessId) {
       Respondents  respondents=   respondentsService.selectSubmit(assessId);
       if(respondents==null){
           return  1;
       }else{
           return respondents.getSubmit();
       }

    }

    @ResponseBody
    @RequestMapping("/selectScore")
    public Map selectScore(@RequestBody PageGet pageGet){

        return  respondentsService.selectScore(pageGet);

    }


    @ResponseBody
    @RequestMapping("/selectRespondentsMsg")
    public RespondentsMsg  selectRespondentsMsg(Integer id){

        return   respondentsService.selectRespondentsMsg(id);

    }

    @ResponseBody
    @RequestMapping("/selectRespondents")

    public  Map  selectRespondents(RespondentsPage respondentsPage){


return  null;
    }






}
