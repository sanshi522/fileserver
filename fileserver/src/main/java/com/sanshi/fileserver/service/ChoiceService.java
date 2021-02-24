package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.Choice;

import java.util.List;

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
     * 获取
     * @param choice
     * @return
     */
    List<Choice> findAll(Choice choice);

    /**
     * 删除
     * @param choice
     */
    void delete(Choice choice);
}
