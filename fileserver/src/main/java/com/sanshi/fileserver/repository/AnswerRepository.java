package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 答题dao
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer,Integer>, JpaSpecificationExecutor {
    /**
     * 通过试题id和答卷id获取答题
     * @param choideid
     * @param respondentsId
     * @return
     */
    Answer findOneByChoiceIdAndRespondentsId(Integer choideid,Integer respondentsId);

    /**
     * 添加修改答题
     * @param answer
     * @return
     */
    Answer save(Answer answer);

    /**
     *删除答题
     * @param id
     */
    void deleteById(Integer id);
}
