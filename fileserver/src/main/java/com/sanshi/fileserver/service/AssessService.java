package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.Assess;
import com.sanshi.fileserver.vo.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 【考核service】
 */
public interface AssessService {

    /**
     * 获取考核
     * @param
     * @param
     * @param
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
     * @param
     * @return
     */
    List<Assess> findAllInvalid(Assess assess, Integer logintype,Integer userId);
    /**
     * 添加修改
     * @param
     * @return
     */
    int save(AssessUserVo assessUserVo);

    /**
     * 删除
     * @param
     */
    Result delete(Integer id);


     AssessMsg findMsg(Integer testPaperId);
     String  fullname(Integer testObject ,Integer testObjectId );

    /**
     * 查询
     */
    AssessUerGVo  findbyId(Integer  assessId);
    /**
     * 查询试卷
     */
    Assess  findByOneId(Integer  assessId);

    /**
     * 学生查询自己的试卷
     */

    Map  StudentAssess(StudentAssessVo studentAssessVo);

    StudentAssessVo  studentChoice(Integer assessId);

    AssessMsg findMsg2(Integer id);

}
