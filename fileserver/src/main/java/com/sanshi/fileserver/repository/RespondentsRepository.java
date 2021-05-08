package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Respondents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
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
     * @param
     */
    void deleteByAssessId(Integer assessId);

    @Query(value="select count(s.id) from Respondents s where s.assessId = ?1  and  s.correct=1")
      int findCountnotred(Integer id);

    @Query(value="select count(s.id)  from Respondents s where s.assessId = ?1  and  s.correct=2")
    int findCountred(Integer id);

    //通过答题人id、批阅状态获取答卷d
    Respondents  findByStuIdAndAssessId(Integer stuId,Integer assessId);

    //查询是否完成的试卷
    Page<Respondents> findAllByStuIdAndCorrectOrderByCreateTimeDesc(Integer stuid, Integer correct, Pageable pa);

    /**
     * 查询所有答卷
     */
    Page<Respondents>findAllByAssessId(Integer assessId,Pageable pa);

    /**
     * 根据提交状态查询的答卷
     */

    Page<Respondents>findAllByAssessIdAndSubmit(Integer assessId,Integer submit,Pageable pa);

    /**
     * 查询已提交未审批的
     * @param assessId
     * @param Submit
     * @param correct
     * @param pa
     * @return
     */
    Page<Respondents>findAllByAssessIdAndSubmitAndCorrect(Integer assessId,Integer Submit,Integer correct,Pageable pa);
    /**
     * 根据完成状态查询
     */
    Page<Respondents>findAllByAssessIdAndCorrect(Integer assessId,Integer correct,Pageable pa);

    /**
     * 根据试卷查询所有的学生Id集合
     * @param assessId
     * @return
     */
    @Query(value="select s.stuId from Respondents s where s.assessId = ?1  ")
    List<Integer> findStuIdsAndAssessId(Integer assessId);

    Respondents   findOneByAssessIdAndStuId(Integer id,Integer stuId);

    /**
     * 查询学生已完成的试卷
     * @param id
     * @param stuId
     * @return
     */
    List<Respondents> findAllByAssessIdInAndStuIdAndCorrect(List<Integer> id,Integer stuId,Integer correct);


}
