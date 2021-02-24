package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.Respondents;

import java.util.List;

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
}
