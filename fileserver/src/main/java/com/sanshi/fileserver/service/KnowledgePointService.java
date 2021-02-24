package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.KnowledgePoint;

import java.util.List;

/**
 * 【知识点Service】
 * 1.获取
 * 2.添加修改
 * 3.删除
 */
public interface KnowledgePointService {
    /**
     * 获取
     * @param ids
     * @return
     */
    List<KnowledgePoint> findAllByIds(List<Integer> ids);

    /**
     * 获取
     * @param knowledgePoint
     * @return
     */
    List<KnowledgePoint> findAll(KnowledgePoint knowledgePoint);

    /**
     * 添加修改
     * @param knowledgePoint
     * @return
     */
    KnowledgePoint save(KnowledgePoint knowledgePoint);

    /**
     * 删除
     * @param knowledgePoint
     */
    void delete(KnowledgePoint knowledgePoint);
}
