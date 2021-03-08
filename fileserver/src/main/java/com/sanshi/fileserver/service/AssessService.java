package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.Assess;
import com.sanshi.fileserver.vo.AssessVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public List<AssessVo> findAll(Assess assess, Integer test, HttpServletRequest request);
    /**
     * 获取进行中考核
     * @param assess
     * @return
     */
    List<Assess> findAllValid(Assess assess, Integer logintype,Integer userId);

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
}
