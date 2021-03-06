package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.KnowledgePoint;
import com.sanshi.fileserver.vo.PageGet;

import java.util.List;
import java.util.Map;

/**
 * 【知识点Service】
 * 1.获取
 * 2.添加修改
 * 3.删除
 */
public interface KnowledgePointService {
    /**
     * 获取
     *
     * @param ids
     * @return
     */
    List<KnowledgePoint> findAllByIds(List<Integer> ids);

    List<KnowledgePoint> findAllBySubIdAndNameLike(Integer subId, String name);

    KnowledgePoint findOneById(Integer id);
    /**
     * 获取
     * @param knowledgePoint
     * @return
     */
//    List<KnowledgePoint> findAll(KnowledgePoint knowledgePoint);

    /**
     * 添加修改
     *
     * @param knowledgePoint
     * @return
     */
    KnowledgePoint save(KnowledgePoint knowledgePoint);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(Integer id);

    Map getAll(PageGet val);

    List<KnowledgePoint> selectBySubId(Integer id);

    KnowledgePoint findOneBySubIdAndName(Integer subId, String name);
  /*
  查询知识点
   */
    public List<KnowledgePoint> selectByNam(int [] arr);
}


