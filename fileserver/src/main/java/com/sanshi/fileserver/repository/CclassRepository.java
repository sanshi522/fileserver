package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Cclass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CclassRepository extends JpaRepository<Cclass,Integer>, JpaSpecificationExecutor {
    /**
     * 根据id获班级信息
     * @param id
     * @return
     */
    Cclass findOneById(Integer id);

    /**
     * 根据年级id获取班级集合
     * @param id
     * @return
     */
    List<Cclass> findByGradeId(Integer id);

}
