package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    List<Choice> findAllBySubIdAndTypeAndTopicLike(Integer subid,Integer type,String name);

    List<Choice> findAllBySubIdAndTopicLike(Integer subid,String name);

    List<Choice> findAllByTypeAndTopicLike(Integer type,String name);

    List<Choice> findAllByTopicLike(String name);

    List<Choice> findAllBySubIdAndType(Integer subid,Integer type);

    List<Choice> findAllBySubId(Integer subid);

    List<Choice> findAllByType(Integer type);

    List<Choice> findAll();
    /**
     * 通过id删除试题
     * @param id
     */
    void deleteById(Integer id);
}
