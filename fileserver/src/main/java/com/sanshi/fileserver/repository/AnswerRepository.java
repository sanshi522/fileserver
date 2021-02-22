package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 答题数据库
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer,Integer>, JpaSpecificationExecutor {
}
