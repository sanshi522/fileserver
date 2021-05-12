package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.Answer;
import com.sanshi.fileserver.vo.AnswerVo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 【答题service】
 */
public interface AnswerService {
    /**
     * 查询
     *
     * @param answer
     * @return
     */
    List<Answer> findall(Answer answer);

    /**
     * 添加修改
     *
     * @param
     * @return
     */
    int save(List<Answer> answerList);

    /**
     * 删除
     *
     * @param answer
     */
    void delete(Answer answer);

    List<AnswerVo> approval(Integer respondentId);

    /**
     * 老师评卷给分
     *
     * @param answerList
     * @return
     */
    int teachRead(@RequestBody List<Answer> answerList);

    /**
     * 老师查看学生答卷
     *
     * @param respondentId
     * @return
     */
    int skip(Integer respondentId);

    /**
     * 学生查看答卷详情
     *
     * @param assessId
     * @return
     */
    List<AnswerVo> particulars(Integer assessId);

    /**
     * 根据ID查询答题信息
     *
     * @param id
     * @return
     */
    Answer findById(Integer id);

}
