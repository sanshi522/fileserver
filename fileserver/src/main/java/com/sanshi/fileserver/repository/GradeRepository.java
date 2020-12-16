package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade,Integer>, JpaSpecificationExecutor {
    /**
     * 根据id获取年级对象
     * @param id
     * @return
     */
    Grade findOneById(Integer id);

    /**
     * 获取年级id集合
     * @return
     */
    List<Grade> findall();

}
