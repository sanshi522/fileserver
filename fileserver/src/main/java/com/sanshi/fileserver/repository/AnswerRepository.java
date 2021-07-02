package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Answer;
import com.sanshi.fileserver.bean.Assess;
import com.sanshi.fileserver.bean.Respondents;
import com.sanshi.fileserver.vo.RespondentsMsg;
import com.sanshi.fileserver.vo.SessionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 答题dao
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer>, JpaSpecificationExecutor {
    /**
     * 通过试题id和答卷id获取答题
     *
     * @param choideid
     * @param respondentsId
     * @return
     */
    Answer findOneByChoiceIdAndRespondentsId(Integer choideid, Integer respondentsId);

    /**
     * 添加修改答题
     *
     * @param answer
     * @return
     */
    Answer save(Answer answer);

    /**
     * 删除答题
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 查询成绩
     */

    @Query(value = "select SUM(a.score) from Answer a where a.respondentsId=?1 ")
    Double selectScore(Integer respondentsId);

    Answer findOneById(Integer id);


    int  deleteByRespondentsId(Integer respondentId);
    List<Answer> findAllByRespondentsId(Integer id);


}



