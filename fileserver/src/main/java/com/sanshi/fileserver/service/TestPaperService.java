package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.TestPaper;

import java.util.List;

/**
 * 【试卷service】
 * 1.获取试卷集合
 * 2.添加修改试卷
 * 3.删除试卷
 */
public interface TestPaperService {
    /**
     * 获取试卷集合
     * @param testPaper
     * @return
     */
    public List<TestPaper> findAll(TestPaper testPaper);

    /**
     * 添加修改试卷
     * @param testPaper
     * @return
     */
    public TestPaper save(TestPaper testPaper);

    /**
     * 删除试卷
     * @return
     */
    public void deleteById(TestPaper testPaper);
}
