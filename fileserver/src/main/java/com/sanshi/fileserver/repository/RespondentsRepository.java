package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Respondents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 答卷dao
 */
@Repository
public interface RespondentsRepository extends JpaRepository<Respondents,Integer>, JpaSpecificationExecutor {
    Respondents findOneById(Integer id);
    /**
     * 通过考核id获取答卷
     * @param id
     * @return
     */
    List<Respondents> findAllByAssessId(Integer id);

    /**
     * 通过考核id、答题人id获取答卷
     * @param id
     * @param stuId
     * @return
     */
    List<Respondents> findAllByAssessIdAndStuId(Integer id,Integer stuId);

    /**
     * 通过考核id、批阅状态获取答卷
     * @param id
     * @param correct
     * @return
     */
    List<Respondents> findAllByAssessIdAndCorrect(Integer id,Integer correct);

    /**
     * 通过答题人id、批阅状态获取答卷
     * @param id
     * @param correct
     * @return
     */
    List<Respondents> findAllBystuIdAndCorrect(Integer id,Integer correct);
    /**
     * 通过答题人id获取答卷
     * @param id
     * @return
     */
    List<Respondents> findAllBystuId(Integer id);

    /**
     * 通过批阅状态
     * @param correct
     * @return
     */
    List<Respondents> findAllByCorrect(Integer correct);
    /**
     *添加修改答卷信息
     * @param re
     * @return
     */
    Respondents save(Respondents re);

    /**
     * 通过id删除答卷
     * @param id
     */
    void deleteById(Integer id);
    /**
     * 通过考核id删除答卷
     * @param id
     */
    void deleteByAssessId(Integer assessId);
}