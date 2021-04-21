package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.Respondents;
import com.sanshi.fileserver.vo.PageGet;
import com.sanshi.fileserver.vo.RespondentsMsg;
import com.sanshi.fileserver.vo.RespondentsPage;

import java.util.List;
import java.util.Map;

/**
 * 【答卷数据库】
 * 1.获取答卷
 * 2.添加修改答卷
 * 3.删除答卷
 */
public interface RespondentsService {
    /**
     * 获取答卷
     * @param respondents
     * @return
     */
    List<Respondents> findAll(Respondents respondents);

    /**
     * 添加修改答卷
     * @param respondents
     * @return
     */
    Respondents save(Respondents respondents);

    /**
     * 删除
     */
    void delete(Respondents respondents);

    /**
     * 获取一个考核下面的所有答卷
     */


    /**
     *
     */
    Respondents  findById(Integer id);

    Respondents  selectSubmit(Integer assessId);

    /**
     * 获取学生已完成的考核信息
     * @param pageGet
     * @return
     */
    public Map selectScore(PageGet pageGet);


    /**
     *
     * @param id
     * @return
     */
    RespondentsMsg selectRespondentsMsg(Integer id);

     Map   selectRespondents(RespondentsPage respondentsPage);



}
