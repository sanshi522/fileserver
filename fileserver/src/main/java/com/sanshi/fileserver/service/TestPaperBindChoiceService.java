package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.TestPaperBindChoice;

import java.util.List;

/**
 * 【试卷试题绑定service】
 * 1.获取绑定
 * 2.添加修改
 * 3.通过id删除
 * 4.通过试卷id删除
 */
public interface TestPaperBindChoiceService {
    /**
     * 通过试卷id获取绑定集合
     * @param testPaperBindChoice
     * @return
     */
    List<TestPaperBindChoice> findAll(TestPaperBindChoice testPaperBindChoice);

    /**
     * 添加修改
     * @param testPaperBindChoice
     * @return
     */
    TestPaperBindChoice save(TestPaperBindChoice testPaperBindChoice);

    /**
     * 获取试卷总分
     * @param id 试卷id
     * @return
     */
    Double findScoreSum(Integer id);
    /**
     * 删除
     * @param Id
     * @return
     */
    void deleteById(TestPaperBindChoice TestPaperBindChoice);

}
