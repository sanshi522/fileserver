package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.Choice;
import com.sanshi.fileserver.bean.Subject;
import com.sanshi.fileserver.vo.ScreenChoice;

import java.util.List;
import java.util.Map;

/**
 * 【试题service】
 */
public interface ChoiceService {
    /**
     * 获取单个试题
     * @param id
     * @return
     */
    Choice findOneById(Integer id);

    /**
     * save试题
     * @param choice
     * @return
     */
    Choice save(Choice choice);
    /**
     * 获取
     * @param choice
     * @return
     */
    Map findAll(ScreenChoice choice);

    /**
     * 删除
     * @param id
     */
    Map deleteById(Integer id);

    /**
     * 根据试卷Id查询试题
     */
    List<Choice> selectChoiceByTestPaperId(Integer id);

    /**
     * 根据学科查询
     * @param name
     * @return
     */
    Subject findOneByName(String name);

    /**
     * 批量导入
     */
    int  saves(List<Choice> choiceList);

    /**
     * 根据试题名字查询某个试题
     */
     List<Choice> findAllByTopic(String topic);

}
