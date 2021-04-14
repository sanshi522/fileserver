package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.Assess;
import com.sanshi.fileserver.vo.AssessMsg;
import com.sanshi.fileserver.vo.AssessVo;
import com.sanshi.fileserver.vo.ScreenAssess;
import com.sanshi.fileserver.vo.TestPaperMsg;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 【考核service】
 */
public interface AssessService {

    /**
     * 获取考核
     * @param assess
     * @param test
     * @param request
     * @return
     */
    public Map findAll(ScreenAssess screenAssess);
    /**
     * 获取进行中考核
     * @param assess
     * @return
     */
    //List<Assess> findAllValid(Assess assess, Integer logintype,Integer userId);

    /**
     * 获取未进行考核
     * @param assess
     * @param request
     * @return
     */
    List<Assess> findAllInvalid(Assess assess, Integer logintype,Integer userId);
    /**
     * 添加修改
     * @param assess
     * @return
     */
    Assess save(Assess assess);

    /**
     * 删除
     * @param assess
     */
    void delete(Assess assess);


     AssessMsg findMsg(Integer testPaperId);
     String  fullname(Integer testObject ,Integer testObjectId );

}
