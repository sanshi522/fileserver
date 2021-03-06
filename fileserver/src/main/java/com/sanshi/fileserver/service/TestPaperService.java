package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.TestPaper;
import com.sanshi.fileserver.vo.*;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 【试卷service】
 * 1.获取试卷集合
 * 2.添加修改试卷
 * 3.删除试卷
 */
public interface TestPaperService {
    /**
     * 进行考试
     *
     * @param assessId
     * @return
     */
    TestPaperVo Exam(Integer assessId, HttpServletRequest request);

    /**
     * 读取试卷
     *
     * @param testPaperId
     * @return
     */
    ReadTestPaper read(Integer testPaperId);

    TestPaper findOneById(Integer id);

    /**
     * 获取试卷集合
     *
     * @param val
     * @return
     */
    public Map findAll(PageGet val);

    /**
     * 添加修改试卷
     *
     * @param testPaper
     * @return
     */
    public TestPaper save(TestPaper testPaper);

    /**
     * 删除试卷
     *
     * @return
     */
    public Result deleteById(Integer testPaperId);

    public TestPaperMsg findMsg(Integer testPaperId);

    /**
     * 自动生成试卷
     */
    public Result generateTestPaper(TestPaperUtils testPaperUtils);

    /**
     * 根据学科查询试卷
     * @param id
     * @return
     */
    public  List<TestPaper> findAllBySubjectId(Integer id);

    /**
     * 根据老师查询试卷
     */
    public List<TestPaper> findAllByTeacherId(Integer id);


}
