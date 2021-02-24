package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 试题dao
 */
@Repository
public interface ChoiceRepository extends JpaRepository<Choice,Integer>, JpaSpecificationExecutor {
    /**
     * 添加修改试题
     * @param c
     * @return
     */
    Choice save(Choice c);

    /**
     *通过id获取试题信息
     * @param id
     * @return
     */
    Choice findOneById(Integer id);

    /**
     * 通过id删除试题
     * @param id
     */
    void deleteById(Integer id);
}
