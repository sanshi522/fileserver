package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.Answer;

import java.util.List;

/**
 * 【答题service】
 */
public interface AnswerService {
    /**
     * 查询
     * @param answer
     * @return
     */
    List<Answer> findall(Answer answer);

    /**
     * 添加修改
     * @param answer
     * @return
     */
    Answer save(Answer answer);

    /**
     * 删除
     * @param answer
     */
    void delete(Answer answer);
}
