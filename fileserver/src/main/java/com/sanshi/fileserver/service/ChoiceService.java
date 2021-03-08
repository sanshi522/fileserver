package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.Choice;
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
     * 获取
     * @param choice
     * @return
     */
    Map findAll(ScreenChoice choice);

    /**
     * 删除
     * @param choice
     */
    void delete(Choice choice);
}
